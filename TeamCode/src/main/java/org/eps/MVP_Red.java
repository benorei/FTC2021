package org.eps;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//SKELETON
//By Everest & Xinyuan

@Autonomous(name="MVP_Red", group="9884_2122")
public class MVP_Red extends LinearOpMode {

    //declare opmode members
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        //CHANGE VALUES LATER
        //robot starts with side on wall
        robot.strafe(0.25 , 200 );

        robot.allDrive( 0.25, 1000 );

        robot.threadSleep(500);

        robot.spinCarousel(0.25);

        robot.allDrive( -.5, 3000);

        //stuff u want to do goes here
    }

}
