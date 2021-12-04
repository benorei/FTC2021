package org.eps;

//Terminator, Destroyer of All, Bane of Android Studio

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.eps.HardwareWithEncoders;

@Autonomous(name="Simple Auto", group="9884")
public class SimpleAuto extends LinearOpMode {

    //declare opmode members
    HardwareWithEncoders robot = new org.eps.HardwareWithEncoders();

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        //telemetry
        telemetry.addData("Status", "started");
        telemetry.update();

        waitForStart();
        //stuff u want to do goes here
    }

}
