package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Extender Test by Raúl")

public class extenderTest extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        //Telemetry initialization
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Motor variable
        DcMotor extender = hardwareMap.get(DcMotor.class,"extender");
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int initialPos = 0;
        int midPos = 2000;
        int finalPos = 4500;
        double motorPower = 0.7;

        waitForStart();
        runtime.reset();

        while(opModeIsActive()){
            //Original Position
            if(gamepad1.cross){
                extender.setTargetPosition(initialPos);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(motorPower);
            }

            //Middle Position
            if(gamepad1.circle){
                extender.setTargetPosition(midPos);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(motorPower);
            }

            //Final Position
            if(gamepad1.square){
                extender.setTargetPosition(finalPos);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(motorPower);
            }

            // Show telemetry results
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Arm Position: ", extender.getCurrentPosition());
            telemetry.update();
        }
    }
}
