package org.eps;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="AutoRed", group="9884")
public class AutoRed extends LinearOpMode {


    //by xinyuan d
    //declare opmode members
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();
        //stuff u want to do goes here

        robot.strafe(0.5,1000);
        robot.threadSleep(1000);
        robot.allDrive(0.5, 3000);
        robot.threadSleep(1000);
        robot.spinCarouselTimed(false,7000);
        robot.allDrive(-0.5,8000);

    }

}
