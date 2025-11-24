package frc.robot.subsystems.Score;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ExtenderManager extends SubsystemBase {

    public enum ExtenderState {
        DISABLED,
        MANUAL,
        AUTOMATIC
    }

    private final ExtenderSubsystem extender;
    private final PIDController pid;

    private ExtenderState currentState = ExtenderState.DISABLED;
    private boolean autoActive = false;

    private double minPos = 0.0;
    private double maxPos = 0.0;
    private double targetPos = 0.0;

    public ExtenderManager() {
        extender = new ExtenderSubsystem();

        pid = new PIDController(Constants.EXTENDER_KP, Constants.EXTENDER_KI, Constants.EXTENDER_KD);
        pid.setTolerance(Constants.EXTENDER_TOLERANCE);

        minPos = Preferences.getDouble(Constants.EXTENDER_MIN_KEY, 0.0);
        maxPos = Preferences.getDouble(Constants.EXTENDER_MAX_KEY, 0.0);
    }

    public void setManual() {
        currentState = ExtenderState.MANUAL;
        autoActive = false;
    }

    public void setManualPower(double power) {
        if (currentState != ExtenderState.MANUAL) {
            currentState = ExtenderState.MANUAL;
            autoActive = false;
        }

        power = Math.max(
            Math.min(power, Constants.EXTENDER_MAX_OUTPUT),
            -Constants.EXTENDER_MAX_OUTPUT
        );

        extender.setPower(power);
    }

    public void stopManual() {
        if (currentState == ExtenderState.MANUAL) {
            extender.stop();
            currentState = ExtenderState.DISABLED;
            autoActive = false;
        } else {
            extender.stop();
        }
    }

    public void calibrateMin() {
        minPos = extender.getPosition();
        Preferences.setDouble(Constants.EXTENDER_MIN_KEY, minPos);
        SmartDashboard.putString("Extender/Status", "MIN calibrado");
    }

    public void calibrateMax() {
        maxPos = extender.getPosition();
        Preferences.setDouble(Constants.EXTENDER_MAX_KEY, maxPos);
        SmartDashboard.putString("Extender/Status", "MAX calibrado");
    }

    public void goToMin() {
        currentState = ExtenderState.AUTOMATIC;
        autoActive = true;
        targetPos = minPos;
        SmartDashboard.putString("Extender/Status", "Indo para MIN");
    }

    public void goToMax() {
        currentState = ExtenderState.AUTOMATIC;
        autoActive = true;
        targetPos = maxPos;
        SmartDashboard.putString("Extender/Status", "Indo para MAX");
    }

    private void runAutomatic() {
        if (!autoActive) return;

        double current = extender.getPosition();
        double output = pid.calculate(current, targetPos);

        output = Math.max(Math.min(output, Constants.EXTENDER_MAX_OUTPUT),
                          -Constants.EXTENDER_MAX_OUTPUT);

        extender.setPower(output);

        SmartDashboard.putNumber("Extender/Current", current);
        SmartDashboard.putNumber("Extender/Target", targetPos);

        if (pid.atSetpoint()) {
            extender.stop();
            autoActive = false;
            currentState = ExtenderState.DISABLED;
            SmartDashboard.putString("Extender/Status", "Posição atingida");
        }
    }

    public void periodic() {
        extender.periodic();

        switch (currentState) {
            case AUTOMATIC:
                runAutomatic();
                break;
            case MANUAL:
            case DISABLED:
            default:
                break;
        }
    }

    public double getPosition() {
        return extender.getPosition();
    }
    
    public ExtenderState getCurrentState() {
        return currentState;
    }
    
}