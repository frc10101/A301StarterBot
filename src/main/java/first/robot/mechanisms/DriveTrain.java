package first.robot.mechanisms;

import org.wpilib.command3.Mechanism;

import com.revrobotics.spark.A301;


public class DriveTrain extends Mechanism{
    private A301 m_FrontLeft;
    private A301 m_FrontRight;
    private A301 m_BackLeft;
    private A301 m_BackRight;

    /**
     * @param MFL_ID M_FrontLeft CAN ID
     * @param MFR_ID M_FrontRight CAN ID
     * @param MBL_ID M_BackLeft CAN ID
     * @param MBR_ID M_BackRight CAN ID
     */
    public DriveTrain(int MFL_ID, int MFR_ID, int MBL_ID, int MBR_ID) {
        m_FrontLeft = new A301(MFL_ID);
        m_BackRight = new A301(MBR_ID);
        m_FrontRight = new A301(MFR_ID);
        m_BackLeft = new A301(MBL_ID);
    }
}
