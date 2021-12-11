package org.eps;

//This is the Hardware.java file for Robotics 2020-21 Ultimate Goal.
//The robot's name is Sketchy Boi.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {
    public DcMotor FrontLeftMotor = null;
    public DcMotor FrontRightMotor = null; //robot.FrontRightMotorFix(power)
    public DcMotor BackLeftMotor = null;
    public DcMotor BackRightMotor = null;
    public DcMotor CarouselMotor = null;

    public Servo ClawLeftServo = null;
    public Servo ClawRightServo = null;

    public DcMotor ArmMotor = null;

    public DcMotor[] allMotors;
    double[] rotationArray;
    double ProblematicMotorReductionFactor = 2;

    //Local opMode members.
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    //Constructor
    public Hardware() {
        //Nothing :)
    }

    double CAROUSELPOWER = 0.1;
    double ARMSPEED = 0.2;


    public void init(HardwareMap ahwMap) {
        //Save references to hardware map
        hwMap = ahwMap;

        //define and init motors
        FrontLeftMotor = hwMap.dcMotor.get("LF");
        FrontRightMotor = hwMap.dcMotor.get("RF");
        BackLeftMotor = hwMap.dcMotor.get("LB");
        BackRightMotor = hwMap.dcMotor.get("RB"); //CHANGE TO RB
        CarouselMotor = hwMap.dcMotor.get("CAROUSEL");

        ClawLeftServo = hwMap.servo.get("GLEFT");
        ClawRightServo = hwMap.servo.get("GRIGHT");

        ArmMotor = hwMap.dcMotor.get("ARM");


        allMotors = new DcMotor[]{
                FrontLeftMotor, FrontRightMotor, BackLeftMotor, BackRightMotor, CarouselMotor
        };

        rotationArray = new double[]{-1.0, 1.0, -1.0, 1.0};

        BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontRightMotor.setDirection(DcMotor.Direction.FORWARD);

        for (DcMotor m : allMotors) {
            m.setPower(0.0);
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); //this is good for auto, but is it good for driver control?
        }

    }

    public double[] getDrivePowersFromAngle(double angle) {
        double[] unscaledPowers = new double[4];
        unscaledPowers[0] = Math.sin(angle + Math.PI / 4);
        unscaledPowers[1] = Math.cos(angle + Math.PI / 4);
        unscaledPowers[2] = unscaledPowers[1];
        unscaledPowers[3] = unscaledPowers[0];
        return unscaledPowers;
    }

    public void threadSleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            //do nothing
        }
    }
    /*
    VARIOUS FUNCTIONS EVEREST WROTE FOR AUTONOMOUS ROUTINES LAST YEAR BUT IS TOO LAZY TO REWRITE THEM
    (OR FIX THE FORMATTING FOR THAT MATTER):
            - allDrive: sets all motors to a given power, condensing code needed to drive.
     - turn: turns in place at a given power for a given number of
    milliseconds. There is no way to input degrees.
     */

    public void allDrive(double power, int milliseconds) {
        powerAll(power, power, power, power);
        threadSleep(milliseconds);
        powerAllOne(0);
    }

    public void turn(double power, int milliseconds) {
        powerAll(-power, power, -power, power);
        threadSleep(milliseconds);
        powerAllOne(0);
    }

    public void strafe(double power, int milliseconds) {
        powerAll(power, -1 * power, -1 * power, power);
        threadSleep(milliseconds);
        powerAll(0, 0, 0, 0);

    }

    public void powerAll(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        FrontLeftMotor.setPower(frontLeftPower);
        FrontRightMotor.setPower(frontRightPower);
        BackLeftMotor.setPower(backLeftPower);
        BackRightMotor.setPower(backRightPower);
    }

    public void powerAllOne(double power) {
        powerAll(power, power, power, power);
    }


    public void moveArm(int delta) {
        int newArmTarget = ArmMotor.getCurrentPosition() + delta;
        ArmMotor.setTargetPosition(newArmTarget);

        ArmMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ArmMotor.setPower(Math.abs(ARMSPEED));
    }

    public void spinCarousel(boolean forBlueSide) {
        if (forBlueSide) {
            CarouselMotor.setPower(CAROUSELPOWER);
        } else {
            CarouselMotor.setPower(-CAROUSELPOWER);
        }

    }

    public void stopCarousel() {
        CarouselMotor.setPower(0);
    }

    public void spinCarouselTimed(boolean forBlueSide, int millis) {
        spinCarousel(forBlueSide);
        threadSleep(millis);
        stopCarousel();
    }

    public void setClawPosition(double leftPosition, double rightPosition) {
        ClawLeftServo.setPosition(leftPosition);
        ClawRightServo.setPosition(rightPosition);
    }

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 2.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    public void encoderDrive( double speed, double lfInches, double rfInches, double lbInches, double rbInches ) {
        int newLFTarget;
        int newRFTarget;
        int newLBTarget;
        int newRBTarget;

        // Determine new target position, and pass to motor controller
        newLFTarget = FrontLeftMotor.getCurrentPosition() + (int)(lfInches * COUNTS_PER_INCH);
        newRFTarget = FrontRightMotor.getCurrentPosition() + (int)(rfInches * COUNTS_PER_INCH);
        newLBTarget = BackLeftMotor.getCurrentPosition() + (int)(lbInches * COUNTS_PER_INCH);
        newRBTarget = BackRightMotor.getCurrentPosition() + (int)(rbInches * COUNTS_PER_INCH);
        FrontLeftMotor.setTargetPosition(newLFTarget);
        FrontRightMotor.setTargetPosition(newRFTarget);
        BackLeftMotor.setTargetPosition(newLBTarget);
        BackRightMotor.setTargetPosition(newRBTarget);

        // Turn On RUN_TO_POSITION
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BackRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        FrontLeftMotor.setPower(Math.abs(speed));
        FrontRightMotor.setPower(Math.abs(speed));
        BackLeftMotor.setPower(Math.abs(speed));
        BackRightMotor.setPower(Math.abs(speed));

        // keep looping while we are still active, and there is time left, and both motors are running.
        // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
        // its target position, the motion will stop.  This is "safer" in the event that the robot will
        // always end the motion as soon as possible.
        // However, if you require that BOTH motors have finished their moves before the robot continues
        // onto the next step, use (isBusy() || isBusy()) in the loop test.
        while (FrontLeftMotor.isBusy() && FrontRightMotor.isBusy() && BackLeftMotor.isBusy() && BackRightMotor.isBusy()) {
            //Wait until motors are done
        }

        // Stop all motion;
        FrontLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackLeftMotor.setPower(0);
        BackRightMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        FrontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FrontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BackRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //  sleep(250);   // optional pause after each move
    }
}