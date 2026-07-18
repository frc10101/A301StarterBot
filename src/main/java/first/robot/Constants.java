package first.robot;

import org.wpilib.hardware.hal.CANBusMap;

public class Constants {
  public class DriveTrainConstants {
    // Can IDs for drive motors
    public static final int FrontLeftID = CANBusMap.CAN_D0;
    public static final int FrontRightID = CANBusMap.CAN_D1;
    public static final int BackLeftID = CANBusMap.CAN_D2;
    public static final int BackRightID = CANBusMap.CAN_D3;

    public enum motors {
      FrontLeft,
      FrontRight,
      BackLeft,
      BackRight,
    };
  }

  public class LauncherConstants {
    public static final int LauncherID = CANBusMap.CAN_D4;
    public static final int LeftFeederID = CANBusMap.CAN_D5;
    public static final int RightFeederID = CANBusMap.CAN_D6;
    public static final double feederThrottle = 0.3;
    public static final double LAUNCH_VELOCITY = 400;
  }
}
