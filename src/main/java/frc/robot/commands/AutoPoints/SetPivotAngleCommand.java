package frc.robot.commands.AutoPoints;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Score.PivotManager;

public class SetPivotAngleCommand extends Command {
  
  public enum Target {
    ZERO,
    SAVED_TARGET
}

private final PivotManager pivot;
private final Target target;

public SetPivotAngleCommand(PivotManager pivot, Target target) {
    this.pivot = pivot;
    this.target = target;

    addRequirements(pivot); 
}

@Override
public void initialize() {
    switch (target) {
        case ZERO:
            pivot.moveToZeroPosition();
            break;

        case SAVED_TARGET:
            pivot.moveToTargetPosition();
            break;
    }
}

@Override
public boolean isFinished() {
    return pivot.getCurrentState() == PivotManager.ControlState.DISABLED;
}

@Override
public void end(boolean interrupted) {
    pivot.stop();
}
}
