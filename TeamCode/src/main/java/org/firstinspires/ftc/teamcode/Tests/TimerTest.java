package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Timer test by Raúl")
public class TimerTest extends LinearOpMode {
    public void runOpMode(){
        ElapsedTime timer = new ElapsedTime();
        boolean bringIn = false;

        waitForStart();

        while (opModeIsActive()){
            if(gamepad1.a){
                //grab the cone
                timer.reset();
                bringIn = true;
            }

            if(timer.milliseconds() >= 150 && bringIn) {
                //bring the cone with the robot
                bringIn = false;
            }
            }
        }
    }
