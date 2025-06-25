package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;
@TeleOp(name = "Tank Drive by Raúl")
public class Tank extends LinearOpMode {
    @Override
    public void runOpMode() {
        //Declaración de los 4 motores del chasis
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "left_drive");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "right_drive");
        //DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        //DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        //Inversión de dirección de motores para modo tanque
        //leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        //Pausa para que al presionar un botón, se espere medio segundo para una respuesta
        Deadline gamepadRateLimit = new Deadline(500,TimeUnit.MILLISECONDS);
        double lowPower = 0.2;
        double highPower = 0.8;
        double currentPower = lowPower;

        //Espera a que se presione "Iniciar" en la Driver Station
        waitForStart();

        //Este while será TRUE hasta que se presione "Stop" en la Driver Station
        while (opModeIsActive()) {
            double left = gamepad1.left_stick_y; //inicializa izq y der dependiendo de los sticks
            double right = gamepad1.right_stick_y;

            double power = 0.8 - (0.6*gamepad1.right_trigger); //limita el poder para los motores

            //Al presionar X se cambia el modo de velocidad
            if(gamepadRateLimit.hasExpired() && gamepad1.a){
                if(currentPower == lowPower){
                    currentPower = highPower;
                }else {
                    currentPower = lowPower;
                }
                gamepadRateLimit.reset();
            }

            leftFront.setPower(left*currentPower); //stick izq potencia motores izq
            //leftBack.setPower(left);

            rightFront.setPower(right*currentPower); //stick der potencia motores derechos
            //rightBack.setPower(right);
        }
    }
}
