package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "Encoder test by Raúl")
public class EncoderTest extends LinearOpMode {
    public void runOpMode(){
        //Investigate the ticks per revolution for each motor (in this case it's 1000)
        DcMotorEx arm = hardwareMap.get(DcMotorEx.class,"arm");
        DcMotor sweeper = hardwareMap.get(DcMotor.class,"sweeper");
        arm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int degrees = 250; //target position

        waitForStart();

        while(opModeIsActive()){
            arm.setTargetPosition(degrees); //always set first target position
            arm.setTargetPositionTolerance(3); //establishes a tolerance of 3 in this case (247<-250->253)
            arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            arm.setPower(0.3);

            sweeper.setPower(0.5);
        }
    }
}
