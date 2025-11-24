package frc.robot.commands.AutoPoints;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Score.IntakeManager;

public class RunIntakeCommand extends Command {

    private final IntakeManager intake;
    private final double power;
    private final double duration;

    private final Timer timer = new Timer();

    public RunIntakeCommand(IntakeManager intake, double power, double durationSeconds) {
        this.intake = intake;
        this.power = power;
        this.duration = durationSeconds;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        timer.reset();
        timer.start();
        intake.intakeIn(power);  
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(duration);
    }

    @Override
    public void end(boolean interrupted) {
        intake.stop();   
        timer.stop();
    }
}
