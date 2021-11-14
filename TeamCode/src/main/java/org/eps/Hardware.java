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

    public DcMotor [] allMotors;
    double [] rotationArray;
    double ProblematicMotorReductionFactor = 2;

    //Local opMode members.
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    //Constructor
    public Hardware() {
        //Nothing :)
    }

    double carouselPower = 0.1;

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


        allMotors = new DcMotor[]{
                FrontLeftMotor, FrontRightMotor, BackLeftMotor, BackRightMotor, CarouselMotor
        };

        rotationArray = new double[] {-1.0, 1.0, -1.0, 1.0};

//        BackLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        BackRightMotor.setDirection(DcMotor.Direction.REVERSE);
//        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
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

    public void allDrive(double power, int milliseconds){
//        FrontLeftMotor.setPower(power);
//        BackLeftMotor.setPower(power);
//        FrontRightMotorFix(power);
//        BackRightMotor.setPower(power);
        powerAll(power, power, power, power);

        threadSleep(milliseconds);

//        FrontLeftMotor.setPower(0);
//        BackLeftMotor.setPower(0);
//        FrontRightMotor.setPower(0);
//        BackRightMotor.setPower(0);
        powerAllOne(0);
    }



    public void turn(double power, int milliseconds){
        //Front motors
//        FrontLeftMotor.setPower(-power);
//        FrontRightMotorFix(power);
//        //Back motors
//        BackLeftMotor.setPower(-power);
//        BackRightMotor.setPower(power);
        powerAll(-power, power, -power, power);

        threadSleep(milliseconds);

//        FrontLeftMotor.setPower(0);
//        BackLeftMotor.setPower(0);
//        FrontRightMotor.setPower(0);
//        BackRightMotor.setPower(0);
        powerAllOne(0);
    }

    public void strafe (double power, int milliseconds) {
//        FrontLeftMotor.setPower(power);
//        FrontRightMotorFix(-1*power);
//        BackLeftMotor.setPower(-1*power);
//        BackRightMotor.setPower(power);
        powerAll(power, -1*power, -1*power, power);

        threadSleep(milliseconds);

//        FrontLeftMotor.setPower(0.0);
//        FrontRightMotor.setPower(0.0);
//        BackLeftMotor.setPower(0.0);
//        BackRightMotor.setPower(0.0);
        powerAll(0, 0, 0, 0);

    }

    public void powerAll(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        FrontLeftMotor.setPower(frontLeftPower);
        FrontRightMotor.setPower(frontRightPower);
        BackLeftMotor.setPower(backLeftPower);
        BackRightMotor.setPower(backRightPower);
    }

    public void powerAllOne(double power){
        powerAll(power, power, power, power);
    }

    public void rampAll(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        //note: this takes 200ms to get to the inserted power.
        powerAll((frontLeftPower + FrontLeftMotor.getPower()) / 2.5, //goes to power 1/4 in between.
                (frontRightPower + FrontRightMotor.getPower()) / 2.5,
                (backLeftPower + BackLeftMotor.getPower()) / 2.5,
                (backRightPower + BackRightMotor.getPower()) / 2.5);
        threadSleep(100);
        powerAll((frontLeftPower + FrontLeftMotor.getPower()) / 2, //goes to power halfway in between.
                (frontRightPower + FrontRightMotor.getPower()) / 2,
                (backLeftPower + BackLeftMotor.getPower()) / 2,
                (backRightPower + BackRightMotor.getPower()) / 2);
        threadSleep(100);
        powerAll(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }


    public void pickUpCargo (){
        //to be filled in later
    }

    public void spinCarousel (boolean forBlueSide){
        if (forBlueSide) {
            CarouselMotor.setPower(carouselPower);
        } else {
            CarouselMotor.setPower(-carouselPower);
        }

    }

    public void stopCarousel(){
        CarouselMotor.setPower(0);
    }

    public void spinCarouselTimed(boolean forBlueSide, int millis){
        spinCarousel(forBlueSide);
        threadSleep(millis);
        stopCarousel();
    }

    public void setClawPosition(double leftPosition, double rightPosition){
        ClawLeftServo.setPosition(leftPosition);
        ClawRightServo.setPosition(rightPosition);
    }

//    public void FrontRightMotorFix(double power){
//        FrontRightMotor.setPower(power * ProblematicMotorReductionFactor);
//    }
}
