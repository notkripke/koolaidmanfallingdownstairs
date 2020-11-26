package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.RevGyro;

@Disabled
@TeleOp(group="tests", name="MecanumDriverCentricTest")
public class MecanumDriverCentricTest extends LinearOpMode
{
    MecanumDrive drive;
    RevGyro gyro;

    @Override
    public void runOpMode()
    {
        drive = new MecanumDrive(hardwareMap, telemetry);
        gyro = new RevGyro(hardwareMap, telemetry);
        gyro.resetAngle();

        waitForStart();

        while(opModeIsActive())
        {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            double dir = Math.atan2(x, y);
            double pow = Math.hypot(x, y);

            dir += 2 * Math.PI - gyro.getAngle();
            dir %= 2 * Math.PI;

            double nx = pow * Math.cos(dir);
            double ny = pow * Math.sin(dir);

            drive.go(nx, ny, r);
        }
    }
}
