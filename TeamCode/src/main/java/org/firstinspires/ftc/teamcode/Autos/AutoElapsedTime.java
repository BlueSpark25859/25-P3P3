package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous
public class AutoElapsedTime extends LinearOpMode {
    public void runOpMode(){
        //Initialize the hardware variables
        DcMotor leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Inverting one of the sides for a linear drive
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        //Variable for measuring time
        ElapsedTime runtime = new ElapsedTime();

        // Wait for the game to start (driver presses START)
        waitForStart();

        //Moves for 1 sec at 20% power
        leftDrive.setPower(0.2);
        rightDrive.setPower(0.2);
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < 1.0)){
            idle();
        }

        //Pause for 1 sec
        leftDrive.setPower(0);
        rightDrive.setPower(0);
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < 0.5)){
            idle();
        }

        //Goes reverse for 1 sec at 50% power
        leftDrive.setPower(-0.5);
        rightDrive.setPower(-0.5);
        runtime.reset();

        while(opModeIsActive() && (runtime.seconds() < 1.0)){
            idle();
        }

        requestOpModeStop();
    }
}
