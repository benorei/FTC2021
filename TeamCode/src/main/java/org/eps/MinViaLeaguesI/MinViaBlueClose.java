package org.eps.MinViaLeaguesI;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eps.Hardware;

@Autonomous(name="MinVia (Blue, Close)", group="9884")
public class MinViaBlueClose extends LinearOpMode {

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

        robot.strafe(-0.4, 500);

        robot.threadSleep(1000);

        robot.allDrive(0.4, 1000);

        robot.spinCarouselTimed(true, 1000);

        robot.threadSleep(1000);

        robot.allDrive(-0.3, 4000);

    }

}
