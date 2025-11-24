package frc.robot.subsystems.Score;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeManager extends SubsystemBase {

    private final IntakeSubsystem intake;
    private boolean isRunning = false;

    public IntakeManager() {
      intake = new IntakeSubsystem();
    }

    public void intakeIn(double power) {
        isRunning = true;
        intake.setPower(power);
    }
    
    public void intakeOut(double power) {
        isRunning = true;
        intake.setPower(power);
    }
    
    public void stop() {
        intake.stop();
        isRunning = false;
    }
    
    public boolean isRunning() {
        return isRunning;
    }
    

}
