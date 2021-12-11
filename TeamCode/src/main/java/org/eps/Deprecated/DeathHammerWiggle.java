package org.eps.Deprecated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.eps.Hardware;

@TeleOp (name="death hammer", group="9884_2122")
public class DeathHammerWiggle extends LinearOpMode {

    Hardware robot = new Hardware();

    double ARMSPEED = 0.1;
    int TIMEOUTSECS = 5;

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        telemetry.addData("Say", "Ready");

        ElapsedTime runtime = new ElapsedTime();

        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

        telemetry.addData("Path0", "Starting at %7d :%7d",
                robot.FrontLeftMotor.getCurrentPosition(),
                robot.FrontRightMotor.getCurrentPosition());
        telemetry.update();

        int newArmTarget;

        waitForStart();

        while(opModeIsActive()) {
            int armDelta = 0;

            if(gamepad1.dpad_up && gamepad1.b) {
                armDelta += 10;
            }
            if(gamepad1.dpad_down && gamepad1.b) {
                armDelta -= 10;
            }
            if(gamepad1.dpad_up) {
                armDelta += 100;
            }
            if(gamepad1.dpad_down) {
                armDelta -= 100;
            }

            if(armDelta != 0) {
                newArmTarget = robot.ArmMotor.getCurrentPosition() + armDelta;
                robot.ArmMotor.setTargetPosition(newArmTarget);

                robot.ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                runtime.reset();
                robot.ArmMotor.setPower(Math.abs(ARMSPEED));

                telemetry.addData("Path1", "Running to %7d", newArmTarget);
                telemetry.addData("Path2", "Running at %7d", robot.ArmMotor.getCurrentPosition());
                telemetry.update();

            }

            if(gamepad1.right_trigger > 0.5){
                robot.ArmMotor.setPower(0);
                robot.ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            double clawLeftAdjusted = 00.39 - (gamepad1.left_trigger * 0.4);
            double clawRightAdjusted = 0.45 +  (gamepad1.left_trigger * 0.4);
            robot.ClawLeftServo.setPosition(clawLeftAdjusted);
            robot.ClawRightServo.setPosition(clawRightAdjusted);

            sleep(40);
        }
    }

}
