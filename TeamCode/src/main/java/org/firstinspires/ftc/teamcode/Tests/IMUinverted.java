package org.firstinspires.ftc.teamcode.Tests;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "IMU inverted by Raúl", group = "Tests")
public class IMUinverted extends LinearOpMode {

    public IMU imu;

    public void runOpMode(){
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        imu = hardwareMap.get(IMU.class, "imu");

        // ✅ Orientación corregida
        imu.initialize(
                new IMU.Parameters(
                        new RevHubOrientationOnRobot(
                                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
                        )
                )
        );

        waitForStart();

        while(opModeIsActive()){
            double yaw = obtenerAngulo();
            telemetry.addData("Yaw (Heading)", yaw);
            telemetry.update();
        }
    }

    public double obtenerAngulo(){
        return -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
}
