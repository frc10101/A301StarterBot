// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package first.robot.opmode;

import org.wpilib.command3.Scheduler;
import org.wpilib.driverstation.Gamepad;
import org.wpilib.opmode.PeriodicOpMode;
import org.wpilib.opmode.Teleop;
import first.robot.Robot;
import first.robot.mechanisms.DriveTrain;

@Teleop
public class MyTeleop extends PeriodicOpMode {
  private final Robot robot;

  /** The Robot instance is passed into the opmode via the constructor. */
  public MyTeleop(Robot robot) {
    this.robot = robot;
  }

  //Define joysticks
  private static Gamepad gamepad = new Gamepad(0);
  //define subsystems
  private static DriveTrain drive = new DriveTrain();

  @Override
  public void disabledPeriodic() {
    /* Called periodically (on every DS packet) while the robot is disabled. */
  }

  @Override
  public void start() {
    /* Called once when the robot is enabled. */
    drive.setDefaultCommand(drive.mecanumDrive(gamepad.getLeftY(), gamepad.getLeftX(), gamepad.getRightX()));
  }

  @Override
  public void periodic() {
    /* Called periodically (set time interval) while the robot is enabled. */
    Scheduler.getDefault().run(); 
  }

  @Override
  public void end() {
    /* Called when the robot is disabled (after previously being enabled). */
    //maybe need to change this if we don't want everything on the robot to stop 
    //when it is disabled for some reason
    Scheduler.getDefault().cancelAll();
  }

  @Override
  public void close() {
    /* Called when the opmode is de-selected / no additional methods will be called. */
  }
}
