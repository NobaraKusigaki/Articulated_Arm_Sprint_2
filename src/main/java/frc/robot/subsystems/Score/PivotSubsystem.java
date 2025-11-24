package frc.robot.subsystems.Score;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class PivotSubsystem  extends SubsystemBase {
    private final SparkMax armMotor = new SparkMax(Constants.ARM_MOTOR_ID, MotorType.kBrushless);
    private final DutyCycleEncoder absEncoder = new DutyCycleEncoder(Constants.ABS_ENCODER_ID);

    private double absEncoderOffsetDeg = 0.0;

    public PivotSubsystem() {
        SparkMaxConfig armConfig = new SparkMaxConfig();
        armConfig.idleMode(IdleMode.kBrake);
        armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public double getAbsoluteAngleDeg() {
        double raw = absEncoder.get();
        double angle = (raw * 360.0) + absEncoderOffsetDeg;
        return ((angle % 360.0) + 360.0) % 360.0;
    }

    public void setEncoderOffset(double offset) {
        absEncoderOffsetDeg = offset;
    }

    public double getEncoderOffset() {
        return absEncoderOffsetDeg;
    }

    public void setArmPower(double power) {
        armMotor.set(power);
    }

    public void stopArm() {
        armMotor.set(0);
    }

    public void periodic() {
        SmartDashboard.putNumber("Arm/AngleDeg", getAbsoluteAngleDeg());
        SmartDashboard.putNumber("Arm/OffsetDeg", absEncoderOffsetDeg);
    }
}
