package org.firstinspires.ftc.teamcode.PIDTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class PID extends LinearOpMode {
    //Motor initialization
    public DcMotor arm = hardwareMap.get(DcMotor.class,"arm");
    public void armToPosition(DcMotor arm, int target, double kp, double ki, double kd) { //kconstants are the inputs (tuned values)
        ElapsedTime timer = new ElapsedTime();
        int MOE = 3; //Margin Of Error
        double previousTime = 0, previousError = 0; //to determine the rate of error and change
        double p = 0, i = 0, d = 0; //output PID constants
        double max_i = 0.2, min_i = -0.2;
        double power; //total sum of the constants for the motor

        //runs while the robot is active and checks if the arm is within the margin of error and the target position
        while (Math.abs(target - arm.getCurrentPosition()) > 3) {
            double currentTime = timer.milliseconds();
            double error = target - arm.getCurrentPosition();

            //Proportional error (directly proportional to the error)
            p = kp * error; //the smaller the error, the less power the motor has

            //Integral error (not recommended for FTC)
            i += ki * (error * (currentTime - i));

            if (i > max_i) {
                i = max_i;
            } else if (i < min_i) {
                i = min_i;
            }

            //Derivative error (directly proportional to the rate of change of the error)
            d = kd * (error - previousError)/(currentTime - previousTime);

            //Adding calculated powers (p,i,d)
            power = p + i + d;
            arm.setPower(power);

            //Save values
            previousError = error;
            previousTime = currentTime;

        }
    }
}
