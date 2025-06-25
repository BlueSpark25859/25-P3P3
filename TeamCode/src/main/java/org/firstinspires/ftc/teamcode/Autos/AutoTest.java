package org.firstinspires.ftc.teamcode.Autos;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.*;

@Autonomous
public class AutoTest extends LinearOpMode {
    public void runOpMode(){
        //Initialize the hardware variables
        DcMotor leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Inverting one of the sides for a linear drive
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        // Wait for the game to start (driver presses START)
        waitForStart();

        //Give initial power to motors
        leftDrive.setPower(0.2);
        rightDrive.setPower(0.2);

        sleep(1000);
        requestOpModeStop();
    }
}
