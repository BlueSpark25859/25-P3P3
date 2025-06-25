package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Telemetry test by Raúl")
public class TelemetryTest extends LinearOpMode {
    public void runOpMode(){
        DcMotor motorOne = hardwareMap.dcMotor.get("motorOne");

        telemetry.addData("Status","Initialized"); //to know when everything has been initialized correctly
        telemetry.update();

        waitForStart();

        while(opModeIsActive()){
            motorOne.setPower(0.6);
            telemetry.addLine("Hello, this is telemetry"); //for adding lines of text
            telemetry.addData("Motor Position: ",motorOne.getCurrentPosition()); //getting a dc motor position
            telemetry.update();
        }
    }
}
