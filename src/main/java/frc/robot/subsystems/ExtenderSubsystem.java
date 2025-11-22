package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;

public class ExtenderSubsystem {

    private final SparkMax extenderMotor =
        new SparkMax(Constants.EXTENDER_MOTOR_ID, MotorType.kBrushless);

    public ExtenderSubsystem() {
        SparkMaxConfig extend_Config = new SparkMaxConfig();
        extend_Config.idleMode(IdleMode.kBrake);
        extend_Config.inverted(false);

        extenderMotor.configure(
            extend_Config,
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
        );
    }

    public void setPower(double power) {
        extenderMotor.set(power);
    }

    public void stop() {
        extenderMotor.set(0);
    }

    public double getPosition() {
        return extenderMotor.getEncoder().getPosition();
    }

    public void periodic() {
        SmartDashboard.putNumber("Extender/Position", getPosition());
    }
}