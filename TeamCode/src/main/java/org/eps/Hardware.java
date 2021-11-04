package org.eps;

//This is the Hardware.java file for Robotics 2020-21 Ultimate Goal.
//The robot's name is Sketchy Boi.

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Hardware {
    public DcMotor FrontLeftMotor = null;
    public DcMotor FrontRightMotor = null;
    public DcMotor BackLeftMotor = null;
    public DcMotor BackRightMotor = null;
    public DcMotor [] allMotors;
    double [] rotationArray;

    //Local opMode members.
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    //Constructor
    public Hardware() {
        //Nothing :)
    }

    public void init(HardwareMap ahwMap) {
        //Save references to hardware map
        hwMap = ahwMap;

        //define and init motors
        FrontLeftMotor = hwMap.dcMotor.get("LF");
        FrontRightMotor = hwMap.dcMotor.get("RF");
        BackLeftMotor = hwMap.dcMotor.get("LB");
        BackRightMotor = hwMap.dcMotor.get("COREHEX"); //CHANGE TO RB

        allMotors = new DcMotor[] {
                FrontLeftMotor, FrontRightMotor, BackLeftMotor, BackRightMotor
        };

        rotationArray = new double[] {-1.0, 1.0, -1.0, 1.0};

//        BackLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        BackRightMotor.setDirection(DcMotor.Direction.REVERSE);
//        FrontLeftMotor.setDirection(DcMotor.Direction.FORWARD);
//        FrontRightMotor.setDirection(DcMotor.Direction.REVERSE);
        BackLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        BackRightMotor.setDirection(DcMotor.Direction.FORWARD);
        FrontLeftMotor.setDirection(DcMotor.Direction.REVERSE);
        FrontRightMotor.setDirection(DcMotor.Direction.FORWARD);

        for (DcMotor m : allMotors) {
            m.setPower(0.0);
//            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    void threadSleep(long milliseconds) {
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
     - spinTurn: a project of Ben's that is still in progress. When finished, it will
    allow the robot to turn on a pivot instead of in place.
            - garageLift and garagePlace: turns the CR servos on the garage mechanism in
    the directions needed to lift or release a block for a given number of milliseconds.
     */

    public void allDrive(double power, int milliseconds){
        FrontLeftMotor.setPower(power);
        BackLeftMotor.setPower(power);
        FrontRightMotor.setPower(power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        BackLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackRightMotor.setPower(0);
    }



    public void turn(double power, int milliseconds){
        //Front motors
        FrontLeftMotor.setPower(-power);
        FrontRightMotor.setPower(power);
        //Back motors
        BackLeftMotor.setPower(-power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        BackLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        BackRightMotor.setPower(0);
    }

    public void spinTurn(double power, int milliseconds){
        FrontLeftMotor.setPower(-power);
        FrontRightMotor.setPower(power);
        //Back motors
        BackLeftMotor.setPower(power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0);
        FrontRightMotor.setPower(0);
        //Back motors
        BackLeftMotor.setPower(0);
        BackRightMotor.setPower(0);
    }

    public void strafe (double power, int milliseconds) {
        FrontLeftMotor.setPower(power);
        FrontRightMotor.setPower(-1*power);
        BackLeftMotor.setPower(-1*power);
        BackRightMotor.setPower(power);

        threadSleep(milliseconds);

        FrontLeftMotor.setPower(0.0);
        FrontRightMotor.setPower(0.0);
        BackLeftMotor.setPower(0.0);
        BackRightMotor.setPower(0.0);

    }

    public void pickUpCargo (){
        //to be filled in later
    }

    public void spinCarousel (double power){
        //to be filled in later
    }

}
