package frc.robot.commands.AutoPoints;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Score.ExtenderManager;
import frc.robot.subsystems.Score.IntakeManager;

public class SetExtensionCommand extends Command {

    public enum Mode {
        TO_MIN,
        TO_MAX,
        RETRACT_WHEN_INTAKE_DONE
    }

    private final ExtenderManager extender;
    private final IntakeManager intake;
    private final Mode mode;

    public SetExtensionCommand(ExtenderManager extender, Mode mode) {
        this(extender, null, mode);
    }

    public SetExtensionCommand(ExtenderManager extender, IntakeManager intake, Mode mode) {
        this.extender = extender;
        this.intake = intake;
        this.mode = mode;

        addRequirements(extender);
    }

    @Override
    public void initialize() {

        switch (mode) {
            case TO_MIN:
                extender.goToMin();
                break;

            case TO_MAX:
                extender.goToMax();
                break;

            case RETRACT_WHEN_INTAKE_DONE:
                extender.goToMin();
                break;
        }
    }

    @Override
    public boolean isFinished() {

        if (mode != Mode.RETRACT_WHEN_INTAKE_DONE) {
            return extender.getCurrentState() == ExtenderManager.ExtenderState.DISABLED;
        }

        boolean intakeStopped = intake != null && !intake.isRunning();
        boolean extenderAtMin = extender.getCurrentState() == ExtenderManager.ExtenderState.DISABLED;

        return intakeStopped && extenderAtMin;
    }

    @Override
    public void end(boolean interrupted) {
        extender.stopManual();
    }
}
