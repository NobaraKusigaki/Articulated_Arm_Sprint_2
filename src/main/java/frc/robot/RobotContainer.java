package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PS5Controller;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.TagFollower;
import frc.robot.subsystems.Locomotion.DriveSubsystem;
import frc.robot.subsystems.Score.ExtenderManager;
import frc.robot.subsystems.Score.PivotManager;

public class RobotContainer {

    private final DriveSubsystem SubSys = new DriveSubsystem();

    private final Joystick robotController = new Joystick(Constants.robotController_ID);
    private final PS5Controller systemController = new PS5Controller(Constants.CONTROLLER_SYTEM_ID);

    private final PivotManager pivotManager = new PivotManager();
    private final ExtenderManager extenderManager = new ExtenderManager();

    public RobotContainer() {
        configureBindings();
        DriveCommand DefaultDrive = new DriveCommand(SubSys, robotController);
        SubSys.setDefaultCommand(DefaultDrive);
    }

    private void configureBindings() {

        // ======================= PIVOT =======================
        
        // ZERO
        new Trigger(() -> systemController.getL1Button())
            .onTrue(new InstantCommand(() -> pivotManager.moveToZeroPosition()));

        // TARGET
        new Trigger(() -> systemController.getR1Button())
            .onTrue(new InstantCommand(() -> pivotManager.moveToTargetPosition()));

        // ===================== EXTENDER ======================
        
        new Trigger(() -> systemController.getSquareButton())
            .onTrue(new InstantCommand(() -> extenderManager.calibrateMin()));

        new Trigger(() -> systemController.getTriangleButton())
            .onTrue(new InstantCommand(() -> extenderManager.calibrateMax()));

        // ===== AUTOMÁTICO =====
        // Círculo ir para MAX
        new Trigger(() -> systemController.getCircleButton())
            .onTrue(new InstantCommand(() -> extenderManager.goToMax()));

        // X ir para MIN
        new Trigger(() -> systemController.getCrossButton())
            .onTrue(new InstantCommand(() -> extenderManager.goToMin()));

        
        new Trigger(() -> Math.abs(systemController.getR2Axis() - systemController.getL2Axis()) > 0.05)
            .whileTrue(new RunCommand(() -> {
                extenderManager.setManual();
                double power = systemController.getR2Axis() - systemController.getL2Axis();
                extenderManager.setManualPower(power); 
            }))
            .onFalse(new InstantCommand(() -> extenderManager.stopManual()));
    }

    public void periodic() {
        pivotManager.periodic();
        extenderManager.periodic();  
    }

    public PivotManager getPivotManager() {
        return pivotManager;
    }

    public ExtenderManager getExtenderManager() {
        return extenderManager;
    }

    public Command getAutonomousCommand() {
    SequentialCommandGroup auto = new SequentialCommandGroup(
      new TagFollower(SubSys, 0.275)
    );
    
    return auto;
    }
}

