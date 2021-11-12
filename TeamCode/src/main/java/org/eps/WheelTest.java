package org.eps;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="WheelTest", group="9884")
public class WheelTest extends LinearOpMode {

    //declare opmode members
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();
        robot.FrontRightMotor.setPower(1);
        robot.threadSleep(5000);
        robot.FrontRightMotor.setPower(-1);
        robot.threadSleep(5000);

        robot.FrontLeftMotor.setPower(1);
        robot.threadSleep(5000);
        robot.FrontLeftMotor.setPower(-1);
        robot.threadSleep(5000);

        robot.BackRightMotor.setPower(1);
        robot.threadSleep(5000);
        robot.BackRightMotor.setPower(-1);
        robot.threadSleep(5000);

        robot.BackLeftMotor.setPower(1);
        robot.threadSleep(5000);
        robot.BackLeftMotor.setPower(-1);
        robot.threadSleep(5000);

        robot.FrontRightMotor.setPower(1);
        robot.FrontLeftMotor.setPower(1);
        robot.BackRightMotor.setPower(1);
        robot.BackLeftMotor.setPower(1);
        robot.threadSleep(5000);

        robot.FrontRightMotor.setPower(-1);
        robot.FrontLeftMotor.setPower(-1);
        robot.BackRightMotor.setPower(-1);
        robot.BackLeftMotor.setPower(-1);
        robot.threadSleep(5000);
        //stuff u want to do goes here
    }

}
