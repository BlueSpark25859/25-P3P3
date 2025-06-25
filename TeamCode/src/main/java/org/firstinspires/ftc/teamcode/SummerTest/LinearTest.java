package org.firstinspires.ftc.teamcode.SummerTest;
//imports
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "Summer test 1 by Raúl", group = "Summer Tests")
public class LinearTest extends LinearOpMode {

    //Para crear las variables globales y configuraciones iniciales
    private ElapsedTime runtime = new ElapsedTime(); //variable para contar el tiempo transcurrido
    DcMotor leftdrive, rightdrive; //variables para motor izq y motor der

    @Override
    public void runOpMode() throws InterruptedException {
        //Iniciar telemetría
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        //Inicializar motores
        leftdrive = hardwareMap.get(DcMotor.class,"left_drive");
        rightdrive = hardwareMap.get(DcMotor.class,"right_drive");

        //Invertir un lado del robot (izquierdo)
        leftdrive.setDirection(DcMotorSimple.Direction.REVERSE);
        rightdrive.setDirection(DcMotorSimple.Direction.FORWARD);

        //Seleccionar modo de los motores (brake or Coast)
        leftdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightdrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //Esperar a que se active el programa en la Driver Station
        waitForStart();
        runtime.reset();

        //Ciclo para cuando la Driver Station está activada
        while (opModeIsActive()){
            //Asignación de sticks del control para conducción
            double drive = gamepad1.left_stick_y;
            double turn = gamepad1.left_stick_x;

            //Configurar mi drivetrain para modo Arcade
            double minPower = -0.8;
            double maxPower = 0.8;
            double leftPower = Range.clip(drive + turn, minPower, maxPower);
            double rightPower = Range.clip(drive - turn, minPower, maxPower);

            //Mandar potencia a las ruedas
            leftdrive.setPower(leftPower);
            rightdrive.setPower(rightPower);

            //Incluir telemetria (lo que queremos imprimir en la Driver Station)
            telemetry.addData("Status", "Run Time: " + runtime.toString());
            telemetry.addData("Motors", "left (%.2f), right (%.2f)", leftPower, rightPower);
        }
    }
}
