package org.firstinspires.ftc.teamcode.PIDTest;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Base PID test by Raúl")
public class BasePID extends PID{

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        while(opModeIsActive()){
            //Using the arm
            armToPosition(arm,2,0.001,0,0);
        }
    }
}
