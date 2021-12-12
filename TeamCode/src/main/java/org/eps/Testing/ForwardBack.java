package org.eps.Testing;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eps.Hardware;

//SKELETON
//By Everest & Xinyuan

@Autonomous(name="ForwardBack", group="9884_2122")
public class ForwardBack extends LinearOpMode {

    //declare opmode members
    Hardware robot = new Hardware();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();

        robot.encoderDrive(0.5, 2000, 2000, 2000, 2000);
        robot.encoderDrive(-0.5, 2000, 2000, 2000, 2000);

        //stuff u want to do goes here
    }

}
