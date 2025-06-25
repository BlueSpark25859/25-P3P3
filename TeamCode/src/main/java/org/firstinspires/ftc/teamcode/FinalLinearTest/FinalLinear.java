package org.firstinspires.ftc.teamcode.FinalLinearTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;

@TeleOp(name = " Final Linear Test Drive by Raúl")
public class FinalLinear extends LinearOpMode {

    private ElapsedTime runtime = new ElapsedTime();

    public void runOpMode(){
        //Initialization of telemetry
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Motor variables
        DcMotor extender =hardwareMap.get(DcMotor.class,"extender");
        extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        int initialPos = 0;
        int midPos = 1500;
        int finalPos = 3180;
        double extenderPower = 0.8;

        //Motor with through bore encoder
        //DcMotor testMotor = hardwareMap.get(DcMotor.class,"testMotor");
        //testMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //testMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Servo variables
        Servo servoOne = hardwareMap.get(Servo.class,"servo_one");
        CRServo servoTwo = hardwareMap.get(CRServo.class,"servo_two");

        //Initialize the hardware variables
        DcMotor leftDrive = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightDrive = hardwareMap.get(DcMotor.class, "right_drive");

        //Inverting one of the sides for a linear drive
        leftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        //Break or coast for motors
        leftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //A little delay to let the button change mode respond correctly
        Deadline gamepadRateLimit = new Deadline(500, TimeUnit.MILLISECONDS);
        double lowPower = 0.2;
        double highPower = 0.8;
        double currentPower = lowPower;

        // Wait for the game to start (driver presses START)
        waitForStart();
        runtime.reset();

        //While Driver Station is active
        while(opModeIsActive()){
            double drive = gamepad1.left_stick_y; //left stick is for forward and backward
            double turn = gamepad1.right_stick_x; //right stick is for turning to left or right

            //When pressing X, velocity mode changes
            if(gamepadRateLimit.hasExpired() && gamepad1.a){
                if(currentPower == lowPower){
                    currentPower = highPower;
                }else {
                    currentPower = lowPower;
                }
                gamepadRateLimit.reset();
            }

            //Testing the servo smoothly with dpad
            if(gamepad1.dpad_up){
                servoOne.setPosition(servoOne.getPosition() + 0.002);
            }else if(gamepad1.dpad_down){
                servoOne.setPosition(servoOne.getPosition() - 0.002);
            }

            //Testing a continuous servo
            boolean isPressed = gamepad1.right_trigger > 0.5;
            boolean isPressed2 = gamepad1.left_trigger > 0.5;
            if(isPressed) {
                servoTwo.setPower(0.5);
            }else if(isPressed2){
                servoTwo.setPower(-0.5);
            }

            //Establishing the extender behavior
            if(gamepad1.circle) {
                extender.setTargetPosition(initialPos); //always set first target position
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(extenderPower);
            }
            if(gamepad1.triangle) {
                extender.setTargetPosition(midPos);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(extenderPower);
            }

            if(gamepad1.square) {
                extender.setTargetPosition(finalPos);
                extender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                extender.setPower(extenderPower);
            }

            if(gamepad1.left_stick_button){
                extender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                extender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }

            //Testing the through bore encoder
            /*
            int encoderPosition = testMotor.getCurrentPosition();

            if(gamepad1.dpad_left){
                testMotor.setPower(0.1);
            }else if (gamepad1.dpad_right){
                testMotor.setPower(0);
            }*/

            //Drivetrain configuration for arcade drive
            double leftPower = Range.clip(drive + turn, -currentPower, currentPower); //limits the range of the motor
            double rightPower = Range.clip(drive - turn, -currentPower, currentPower);

            //Send calculated power to wheels
            leftDrive.setPower(leftPower);
            rightDrive.setPower(rightPower);

            // Show the elapsed game time and telemetry data
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
            telemetry.addData("Extender Position: ", extender.getCurrentPosition());
            telemetry.addData("Servo Position: ",servoOne.getPosition());
            //telemetry.addData("Encoder ticks: ",encoderPosition);
            telemetry.update();
        }
    }
}
