package first.robot.mechanisms;

import java.util.function.DoubleSupplier;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.wpilib.command3.Command;
import org.wpilib.command3.Coroutine;
import org.wpilib.command3.Mechanism;
import first.robot.Constants.DriveTrainConstants;
import first.robot.Constants.DriveTrainConstants.motors;
import com.revrobotics.spark.A301;


public class DriveTrain extends Mechanism{
    private A301 m_FrontLeft = new A301(DriveTrainConstants.FrontLeftID);
    private A301 m_FrontRight = new A301(DriveTrainConstants.FrontRightID);
    private A301 m_BackLeft = new A301(DriveTrainConstants.BackLeftID);
    private A301 m_BackRight = new A301(DriveTrainConstants.BackRightID);

    //TO_DO need to create config for motors set directions and maybe current settings

    public DriveTrain() {
        m_BackLeft.setAbsoluteEncoderPosition(0);
        m_BackRight.setAbsoluteEncoderPosition(0);
        m_FrontLeft.setAbsoluteEncoderPosition(0);
        m_FrontRight.setAbsoluteEncoderPosition(0);
    }

    public Command setIndividualThrottle(motors motor, double power){
        return run(Coroutine -> {
        switch (motor) {
            case FrontLeft:
                m_FrontLeft.setThrottle(power); 
            case FrontRight:
                m_FrontRight.setThrottle(power);
            case BackLeft:
                m_BackLeft.setThrottle(power);
            case BackRight:
                m_BackRight.setThrottle(power);
        }
    }).named("setIndividualMotorPower " + motor + ':' + power);
    }

    public Command setAllMotorThrottles(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        return run(Coroutine -> {

            m_FrontLeft.setThrottle(frontLeftPower);
            m_FrontRight.setThrottle(frontRightPower);
            m_BackLeft.setThrottle(backLeftPower);
            m_BackRight.setThrottle(backRightPower);
        }).named("set all motor throttles");
    }

    public Command stop(){
        return run(Coroutine -> {
        m_FrontLeft.setThrottle(0);
        m_FrontRight.setThrottle(0);
        m_BackLeft.setThrottle(0);
        m_BackRight.setThrottle(0);
        }).named("Stop all Drive Motors");
    }

    public Command mecanumDrive(DoubleSupplier forward, DoubleSupplier strafe, DoubleSupplier yaw){
        return run(Coroutine -> {
            while(true){
            double deadzone = 0.00;
            double f = -forward.getAsDouble();
            double s = strafe.getAsDouble();
            double y = -yaw.getAsDouble();
            
            if (Math.abs(f) < deadzone) f = 0;
            if (Math.abs(s) < deadzone) s = 0;
            if (Math.abs(y) < deadzone) y = 0;

            m_FrontLeft.setThrottle(
                (f + s) + y
            );

            m_FrontRight.setThrottle(
                (f - s) - y
            );

            m_BackLeft.setThrottle(
                (f - s) + y
            );

            m_BackRight.setThrottle(
                (f + s) - y
            );
            Coroutine.yield();
        }
        }).named("Mecanum Drive");
    }
}
