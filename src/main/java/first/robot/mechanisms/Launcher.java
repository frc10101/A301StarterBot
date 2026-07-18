package first.robot.mechanisms;

import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import org.wpilib.system.Timer;

import com.revrobotics.spark.A301;

import first.robot.Constants.LauncherConstants;

public class Launcher extends Mechanism {
  private A301 m_laucnher = new A301(LauncherConstants.LauncherID);
  private A301 m_leftFeeder = new A301(LauncherConstants.LeftFeederID);
  private A301 m_rightFeeder = new A301(LauncherConstants.RightFeederID);

  private double TARGET_VELOCITY;
  private double LEFT_TARGET_POSITION; // in revolutions?
  private double RIGHT_TARGET_POSITION; // in revolutions?
                                        //
  private Timer timer;

  public Launcher() {
    m_laucnher.setAbsoluteEncoderPosition(0);
    m_leftFeeder.setAbsoluteEncoderPosition(0);
    m_rightFeeder.setAbsoluteEncoderPosition(0);

    m_rightFeeder.setInverted(true);

    TARGET_VELOCITY = 0;
    LEFT_TARGET_POSITION = 0;
    RIGHT_TARGET_POSITION = 0;

    timer = new Timer();
  }

  public void setVelocity(double speed) {
    TARGET_VELOCITY = speed;
  }

  public Command rpmController() {
    return run(Coroutine -> {
      while (true) {
        m_laucnher.setVelocity(TARGET_VELOCITY);
        Coroutine.yield();
      }
    }).named("Launcher RPM Controller | RPM: " + TARGET_VELOCITY);
  }

  public Command advanceBall() {
    return run(Coroutine -> {

      LEFT_TARGET_POSITION += 25; // mgiht be in rotations?
      RIGHT_TARGET_POSITION += 25; // might be in rotations?

      while (m_leftFeeder.getRelativeEncoderPosition().get() < LEFT_TARGET_POSITION
          && m_rightFeeder.getRelativeEncoderPosition().get() < RIGHT_TARGET_POSITION) {
        m_leftFeeder.setThrottle(LauncherConstants.feederThrottle);
        m_rightFeeder.setThrottle(LauncherConstants.feederThrottle);
        Coroutine.yield();
      }

      m_rightFeeder.setThrottle(0);
      m_leftFeeder.setThrottle(0);
      Coroutine.yield();
    }).named("Advance Ball");
  }

  public Command launch() {
    return run(Coroutine -> {
      boolean launched = false;
      while (!launched) {
        if (m_laucnher.getEncoderVelocity().get() > LauncherConstants.LAUNCH_VELOCITY - 10) {
          Coroutine.await(advanceBall());
          break;
        } else {
          setVelocity(LauncherConstants.LAUNCH_VELOCITY);
          Coroutine.yield();
        }
        // timer is in seconds
        if (timer.hasElapsed(10)) {
          // find way to print error of timeout
          break;
        }
      }
    }).named("Launching Ball");
  }
}
