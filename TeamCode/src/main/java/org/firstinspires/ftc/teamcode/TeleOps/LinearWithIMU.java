package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Linear IMU by Raúl")
public class LinearWithIMU extends LinearOpMode {
    //Global variables
    private ElapsedTime runtime = new ElapsedTime();
    public IMU imu;

    public void runOpMode(){
        //Initialization of telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Initialize the hardware variables
        DcMotor leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Inverting one of the sides for a linear drive
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        //A little delay to let the button change mode respond correctly
        Deadline gamepadRateLimit = new Deadline(500,TimeUnit.MILLISECONDS);
        double lowPower = 0.2;
        double highPower = 0.8;
        double currentPower = lowPower;

        //Pausa para reseteo de IMU
        Deadline imuLimit = new Deadline(500,TimeUnit.MILLISECONDS);

        //Creating the IMU object
        imu = hardwareMap.get(IMU.class,"imu");
        imu.initialize(
                new IMU.Parameters(new RevHubOrientationOnRobot(RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD))
        );

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        //This while will be TRUE until "Stop" is pushed in the Driver Station
        while (opModeIsActive()) {
            double drive = gamepad1.left_stick_y; //left stick is for forward and backward
            double turn = gamepad1.right_stick_x; //right stick is for turning to left or right

            //Establish the robot heading
            //double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double adjustedLx = -drive * Math.sin(obtenerAngulo()) + turn * Math.cos(obtenerAngulo());
            double adjustedLy = drive * Math.cos(obtenerAngulo()) + turn * Math.sin(obtenerAngulo());

            //When pressing X, velocity mode changes
            if(gamepadRateLimit.hasExpired() && gamepad1.a){
                if(currentPower == lowPower){
                    currentPower = highPower;
                }else {
                    currentPower = lowPower;
                }
                gamepadRateLimit.reset();
            }

            //IMU reset
            if(gamepadRateLimit.hasExpired() && gamepad1.b){
                imu.resetYaw();
                imuLimit.reset();
            }

            double leftPower = Range.clip(adjustedLy + adjustedLx, -currentPower, currentPower); //limits the range of the motor
            double rightPower = Range.clip(adjustedLy - adjustedLx, -currentPower, currentPower);

            //Send calculated power to wheels
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            // Show the elapsed game time and wheel power.
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Heading: ", obtenerAngulo());
            telemetry.update();
        }
    }

    // Creación de funciones adicionales //

    public double obtenerAngulo(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }
}

