package org.eps.Testing;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.robot.Robot;

import org.eps.Hardware;
import org.firstinspires.ftc.robotcore.external.Telemetry;

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

        robot.encoderDrive(0.5, 2000, 2000, 2000, 2000, telemetry);
//        robot.localEncoderDrive(-0.5, 2000, 2000, 2000, 2000, telemetry);

        //stuff u want to do goes here
    }

}
