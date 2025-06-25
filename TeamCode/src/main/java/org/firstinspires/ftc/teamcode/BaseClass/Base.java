package org.firstinspires.ftc.teamcode.BaseClass;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public abstract class Base extends LinearOpMode {
    public DcMotor motorOne;
    public Servo wrist;
    public Servo claw;
    public DistanceSensor distance_sensor;

    //Main method for hardware creation
    public void initHardware(){

        //First test of 1 motor and servo
        motorOne = hardwareMap.get(DcMotor.class,"motor_one");
        motorOne.setDirection(DcMotorSimple.Direction.REVERSE);

        //Test for a wrist servo
        wrist = hardwareMap.get(Servo.class,"wrist");

        //Test for a claw servo
        claw = hardwareMap.get(Servo.class,"claw");

        //Second test with a distance sensor
        distance_sensor = hardwareMap.get(DistanceSensor.class,"distance_sensor");
    }

    //Next section for using and creating the hardware methods
    public double getDistance(){

        return distance_sensor.getDistance(DistanceUnit.CM);
    }
}
