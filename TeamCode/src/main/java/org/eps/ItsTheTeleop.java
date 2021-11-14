package org.eps;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//Controls:
//  Left stick    ->    Drive
//  Right stick   ->    Turn
//  Right trigger ->    Shoot ring
//  Left bumper   ->    Control arm
//  Right bumper  ->    Control claw

@TeleOp(name="It's The Teleop", group="9884_2122")

public class ItsTheTeleop extends LinearOpMode {
    //Declare opmode members.

    Hardware robot = new Hardware();

    //Code to run once at init
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Say", "ready");

        ElapsedTime runtime= new ElapsedTime();
        waitForStart();

        double driveFactor = 1;
        int carouselCountdown = 0;
        int clawCountdown = 0;

        while(opModeIsActive()) {
            double lX = -gamepad1.left_stick_x;
            double lY = -gamepad1.left_stick_y;
            double rX = -gamepad1.right_stick_x;
            double rY = gamepad1.right_stick_y;
            //Mr. Mein's Math
            double dsAngle = Math.atan2(lX, lY);
            double dsWeight = Math.sqrt(lX * lX + lY * lY);
            double rotPower = rX;
            double rotWeight = Math.abs(rX);



            //make sure values are not greater than 1
            if (dsWeight + rotWeight > 1.0) {
                dsWeight /= dsWeight + rotWeight;
                rotPower /= dsWeight + rotWeight;
            }
            // finally, do a little math and put them into the motors


            robot.FrontLeftMotor.setPower(driveFactor*(Math.cos(dsAngle + Math.PI / 4) * dsWeight - rotPower * rotWeight));
            robot.BackRightMotor.setPower(driveFactor*(Math.cos(dsAngle + Math.PI / 4) * dsWeight + rotPower * rotWeight));
            robot.FrontRightMotor.setPower(driveFactor*(Math.cos(dsAngle - Math.PI / 4) * dsWeight + rotPower * rotWeight));
            robot.BackLeftMotor.setPower(driveFactor*(Math.cos(dsAngle - Math.PI / 4) * dsWeight - rotPower * rotWeight));

            if(rX == 0 && rY == 0 && lX == 0 && lY == 0){
                robot.FrontLeftMotor.setPower(0);
                robot.BackRightMotor.setPower(0);
                robot.FrontRightMotor.setPower(0);
                robot.BackLeftMotor.setPower(0);
            }

            if(gamepad1.right_bumper) {
                robot.spinCarousel(false);
                carouselCountdown = 3;
            } else if (gamepad1.left_bumper) {
                robot.spinCarousel(true);
                carouselCountdown = 3;
            } else if (carouselCountdown == 0){
                robot.stopCarousel();
            } else {
                carouselCountdown -= 1; // count down from 3 so it doesn't ever stop when it's not supposed to.
                //only 120ms delay
            }

            //THE CLAW
//            if(gamepad1.right_trigger > 0 || gamepad1.left_trigger > 0.2){
//                robot.setClawPosition(gamepad1.left_trigger, -gamepad1.right_trigger);
////                clawCountdown = 2;
//            } else {
////                clawCountdown -= 1;
//            }
//
//            if(gamepad1.right_trigger == 0 && gamepad1.left_trigger <= 0.2){
//                robot.setClawPosition(0.2, 0);
//            }

            if(gamepad1.left_trigger > 0){
                robot.ClawLeftServo.setPosition(gamepad1.left_trigger + 0.2);
                robot.ClawRightServo.setPosition(gamepad1.left_trigger + 0.2);
            }

            telemetry.addData("Claw (left)", gamepad1.left_trigger);
            telemetry.addData("Claw (right)", gamepad1.right_trigger);
            telemetry.addData("Claw Countdown", clawCountdown);
            telemetry.addData("Carousel Countdown", carouselCountdown);
            telemetry.addData("dsAngle", dsAngle);
            telemetry.addData("dsWeight", dsWeight);
            telemetry.addData("rotPower", rotPower);
            telemetry.addData("rotWeight", rotWeight);
            telemetry.update();

            sleep(40);

        }


    }

}