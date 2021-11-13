package org.eps;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="WheelTest", group="9884")
public class WheelTest extends LinearOpMode {

    double factor = .4;
    //declare opmode members
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "WithOpModeActive");
        telemetry.update();

        waitForStart();
//        while(opModeIsActive()) {
            robot.FrontRightMotor.setPower(factor);
            robot.threadSleep(5000);
            robot.FrontRightMotor.setPower(-factor);
            robot.threadSleep(5000);
            robot.FrontRightMotor.setPower(0);

            robot.FrontLeftMotor.setPower(factor);
            robot.threadSleep(5000);
            robot.FrontLeftMotor.setPower(-factor);
            robot.threadSleep(5000);
            robot.FrontLeftMotor.setPower(0);

            robot.BackRightMotor.setPower(factor);
            robot.threadSleep(5000);
            robot.BackRightMotor.setPower(-factor);
            robot.threadSleep(5000);
            robot.BackRightMotor.setPower(0);

            robot.BackLeftMotor.setPower(factor);
            robot.threadSleep(5000);
            robot.BackLeftMotor.setPower(-factor);
            robot.threadSleep(5000);
            robot.BackLeftMotor.setPower(0);

            robot.powerAllOne(factor);
            robot.threadSleep(5000);

            robot.powerAllOne(-factor);
            robot.threadSleep(5000);

            robot.powerAllOne(0);
//            stop();
//            break;
            //stuff u want to do goes here
//        }
    }

}
