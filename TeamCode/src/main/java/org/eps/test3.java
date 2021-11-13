package org.eps;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//SKELETON
//By Everest & Xinyuan

@Autonomous(name="Test3", group="9884_2122")
public class test3 extends LinearOpMode {

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
        robot.allDrive(0.5, 5000);
        robot.allDrive(-0.5, 5000);

        //stuff u want to do goes here
    }

}
