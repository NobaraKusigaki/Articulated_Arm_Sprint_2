package frc.robot.subsystems.Score;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class PivotManager {

    public enum ControlState { 
        DISABLED, MANUAL, AUTOMATIC }

    private final PivotSubsystem pivot;
    private final PIDController armPID;
    private final ArmFeedforward armFF;

    private ControlState currentState = ControlState.DISABLED;
    private double targetAngleDeg = 0.0;
    private boolean autoActive = false;

    public PivotManager() {
        this.pivot = new PivotSubsystem();

        armPID = new PIDController(Constants.ARM_KP, Constants.ARM_KI, Constants.ARM_KD);
        armPID.setTolerance(Constants.ARM_TOLERANCE_DEG);

        armFF = new ArmFeedforward(Constants.ARM_KS, Constants.ARM_KG, Constants.ARM_KV, Constants.ARM_KA);

       
        pivot.setEncoderOffset(Preferences.getDouble(Constants.PREF_KEY_ENCODER_OFFSET, 0.0));
        targetAngleDeg = Preferences.getDouble(Constants.PREF_KEY_TARGET_ANGLE, 0.0);
    }

    public void setManual() {
        currentState = ControlState.MANUAL;
    }

    public void setManualOutput(double power) {
        if (currentState == ControlState.MANUAL)
            pivot.setArmPower(power);
    }

    public void moveToZeroPosition() {
        currentState = ControlState.AUTOMATIC;
        autoActive = true;
        targetAngleDeg = 0.0;
        SmartDashboard.putString("Arm/Status", "Movendo para ZERO");
    }

    public void moveToTargetPosition() {
        currentState = ControlState.AUTOMATIC;
        autoActive = true;
        targetAngleDeg = Preferences.getDouble(Constants.PREF_KEY_TARGET_ANGLE, targetAngleDeg);
        SmartDashboard.putString("Arm/Status", "Movendo para TARGET");
    }

    public void periodic() {
        pivot.periodic();

        switch (currentState) {
            case AUTOMATIC:
                moveAutomatic();
                break;
            case MANUAL:
                break;
            case DISABLED:
            default:
                break;
        }

        if (!autoActive && currentState == ControlState.AUTOMATIC) {
            currentState = ControlState.DISABLED;
        }
    }

    private double lastOutput = 0.0;

    private void moveAutomatic() {
        if (!autoActive) return;
    
        double currentAngle = pivot.getAbsoluteAngleDeg();
        double setpoint = targetAngleDeg;
    
        double error = ((setpoint - currentAngle + 540.0) % 360.0) - 180.0;
    
        double pidOutput = armPID.calculate(error, 0.0);
    
        double ffOutput = armFF.calculate(Math.toRadians(currentAngle), 0.0);
    
        double totalOutput = pidOutput + ffOutput;
    
        totalOutput = Math.max(Math.min(totalOutput, Constants.ARM_MAX_OUTPUT), -Constants.ARM_MAX_OUTPUT);
  
        if (Math.abs(error) < 5.0) {
            totalOutput *= 0.3;
        }
    
        double rampRate = 0.02;
        totalOutput = lastOutput + Math.max(Math.min(totalOutput - lastOutput, rampRate), -rampRate);
        lastOutput = totalOutput;
    
        pivot.setArmPower(totalOutput);
    
        SmartDashboard.putNumber("Arm/CurrentAngle", currentAngle);
        SmartDashboard.putNumber("Arm/TargetAngle", setpoint);
        SmartDashboard.putNumber("Arm/ErrorDeg", error);
        SmartDashboard.putNumber("Arm/PIDOutput", pidOutput);
        SmartDashboard.putNumber("Arm/FFOutput", ffOutput);
        SmartDashboard.putNumber("Arm/TotalOutput", totalOutput);
    
        if (Math.abs(error) < Constants.ARM_TOLERANCE_DEG) {
            pivot.stopArm();
            autoActive = false;
            currentState = ControlState.DISABLED;
            SmartDashboard.putString("Arm/Status", "Posição atingida");
        }
    }
   
    public void stop() {
        pivot.stopArm();
        autoActive = false;
        currentState = ControlState.DISABLED;
    }

    public ControlState getCurrentState() {
        return currentState;
    }

    public PivotSubsystem getSubsystem() {
        return pivot;
    }

    public void calibrateZero() {
        double rawValue = pivot.getAbsoluteAngleDeg() - pivot.getEncoderOffset();
        double newOffset = -rawValue;

        pivot.setEncoderOffset(newOffset);
        Preferences.setDouble(Constants.PREF_KEY_ENCODER_OFFSET, newOffset);

        SmartDashboard.putString("Arm/Calibration", "Novo ZERO definido!");
        SmartDashboard.putNumber("Arm/New Offset (deg)", newOffset);
    }

    public void calibrateTargetAngle() {
        double currentAngle = pivot.getAbsoluteAngleDeg();
        targetAngleDeg = currentAngle;
        Preferences.setDouble(Constants.PREF_KEY_TARGET_ANGLE, targetAngleDeg);

        SmartDashboard.putString("Arm/Calibration", "Ângulo alvo salvo!");
        SmartDashboard.putNumber("Arm/Target Angle (deg)", targetAngleDeg);
    }
}