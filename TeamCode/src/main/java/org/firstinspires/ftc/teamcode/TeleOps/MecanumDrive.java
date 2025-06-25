package org.firstinspires.ftc.teamcode.TeleOps;

import com.qualcomm.robotcore.hardware.*;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.internal.system.Deadline;
import java.util.concurrent.TimeUnit;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.IMU;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Mecanum by Raúl")
public class MecanumDrive extends LinearOpMode{
    @Override
    public void runOpMode(){
        //Declaración de los 4 motores del chasis
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "leftFront");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "rightFront");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "leftBack");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "rightBack");

        //Inversión de dirección de motores para modo tanque
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);

        //Encoders para más precisión
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Pausa para que al presionar un botón, se espere medio segundo para una respuesta
        Deadline gamepadRateLimit = new Deadline(500,TimeUnit.MILLISECONDS);
        double lowPower = 0.2;
        double highPower = 0.8;
        double currentPower = lowPower;

        //Pausa para reseteo de IMU
        Deadline imuLimit = new Deadline(500,TimeUnit.MILLISECONDS);

        //Crear e inicializar el objeto para la IMU
        IMU imu = hardwareMap.get(IMU.class,"imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ));
        imu.initialize(parameters);

        //Espera a que se presione "Iniciar" en la Driver Station
        waitForStart();

        //Este while será TRUE hasta que se presione "Stop" en la Driver Station
        while (opModeIsActive()) {
            //Leyendo los valores de los sticks del control
            double lx = gamepad1.left_stick_x;
            double ly = gamepad1.left_stick_y;
            double rx = gamepad1.right_stick_x;

            //Variable para que los motores no sobrepasen su límite (1)
            double max = Math.max(Math.abs(lx) + Math.abs(ly) + Math.abs(rx), 1);

            //Establecer heading del robot
            double heading = -imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            double adjustedLx = -ly * Math.sin(heading) + lx * Math.cos(heading);
            double adjustedLy = ly * Math.cos(heading) + lx * Math.sin(heading);

            //Al presionar X se cambia el modo de velocidad
            if(gamepadRateLimit.hasExpired() && gamepad1.a){
                if(currentPower == lowPower){
                    currentPower = highPower;
                }else {
                    currentPower = lowPower;
                }
                gamepadRateLimit.reset();
            }

            //Reseteo de IMU
            if(imuLimit.hasExpired() && gamepad1.b){
                imu.resetYaw();
                imuLimit.reset();
            }

            //Lógica para 4 motores modo Mecanum
            leftFront.setPower(((adjustedLy+adjustedLx+rx)/max)*currentPower); //positivo hacia enfrente
            leftBack.setPower(((adjustedLy-adjustedLx+rx)/max)*currentPower); //se invierte lx para que vaya al mismo sentido que leftFront
            rightFront.setPower(((adjustedLy-adjustedLx-rx)/max)*currentPower); //opuesto a leftFront
            rightBack.setPower(((adjustedLy+adjustedLx-rx)/max)*currentPower); //opuesto a leftBack
        }
    }
}
