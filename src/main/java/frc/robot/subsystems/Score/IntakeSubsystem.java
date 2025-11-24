// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Score;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class IntakeSubsystem extends SubsystemBase {
  private final SparkMax intake_Motor = new SparkMax(Constants.INTAKE_MOTOR_ID, MotorType.kBrushless);

  public IntakeSubsystem() {
    SparkMaxConfig intakeConfig = new SparkMaxConfig();
    intakeConfig.idleMode(IdleMode.kBrake);
    intakeConfig.inverted(false);

    intake_Motor.configure(
    intakeConfig, 
    ResetMode.kResetSafeParameters, 
    PersistMode.kPersistParameters
    );

  }

   public void setPower(double power) {
        intake_Motor.set(power);
    }

    public void stop() {
        intake_Motor.set(0);
    }

  @Override
  public void periodic() {

    
  }
}
