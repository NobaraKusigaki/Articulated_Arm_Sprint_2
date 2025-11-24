package frc.robot;

public final class Constants {
    // ====== DRIVE MOTOR IDS ======
    public static final int RMot1_ID = 2;
    public static final int RMot2_ID = 1;
    public static final int LMot1_ID = 3;
    public static final int LMot2_ID = 4;

    public static final double Deadzone = 0.04;

    // ====== CONTROLLER IDS ======
    public static final int robotController_ID = 0;

    public static final int ButA_ID = 1;
    public static final int ButB_ID = 2;
    public static final int ButX_ID = 4;

    public static final int X1_ID = 0;
    public static final int X2_ID = 4;
    public static final int Y1_ID = 1;
    public static final int Y2_ID = 5;

    public static final int Rtrig_ID = 3;
    public static final int Ltrig_ID = 2;

    // ====== ARM MOTOR IDS ======
    public static final int ARM_MOTOR_ID = 5 ;
    public static final int EXTENDER_MOTOR_ID = 6;
    public static final int INTAKE_MOTOR_ID = 7;
    
    public static final int ABS_ENCODER_ID = 4;

    // ====== PID CONSTANTS ======
    public static final double ARM_KP = 0.015;
    public static final double ARM_KI = 0.0;
    public static final double ARM_KD = 0.001;

    public static final double EXTENDER_KP = 0.02;
    public static final double EXTENDER_KI = 0.0;
    public static final double EXTENDER_KD = 0.0;

    // ====== FEEDFORWARD ======
    public static final double ARM_KS = 0.05;   
    public static final double ARM_KG = 0.05;   
    public static final double ARM_KV = 0.0;    
    public static final double ARM_KA = 0.0;    

    // ====== PREFERENCES KEYS ======
    public static final String PREF_KEY_ENCODER_OFFSET = "ArmEncoderOffsetDeg";
    public static final String PREF_KEY_TARGET_ANGLE = "ArmTargetAngleDeg";

    public static final String EXTENDER_MIN_KEY = "Extender/MinPos";
    public static final String EXTENDER_MAX_KEY = "Extender/MaxPos";

    // ====== LIMITES E SAFETY ======
    public static final double ARM_MAX_OUTPUT = 1.0;
    public static final double ARM_TOLERANCE_DEG = 1.0;

    public static final double EXTENDER_MAX_OUTPUT = 1.0;
    public static final double EXTENDER_TOLERANCE = 0.3;
    
    public static final int CONTROLLER_SYTEM_ID = 1;
    
}