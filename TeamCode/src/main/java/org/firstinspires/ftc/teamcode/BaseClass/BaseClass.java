package org.firstinspires.ftc.teamcode.BaseClass;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Base Class Test by Raúl")
public class BaseClass extends Base {
    public void runOpMode(){

        //Calling the first method for testing motors
        initHardware();

        //Calling the distance sensor method for testing
        double x = getDistance();
        telemetry.addData("Distance: ",x);
        telemetry.update();

        while(opModeIsActive()){
            //Testing a claw to close depending on the distance
            if(getDistance() < 10){
                claw.setPosition(1);
            }
        }
    }
}
