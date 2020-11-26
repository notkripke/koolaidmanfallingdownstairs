package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.MecanumDrive;

@Disabled
@TeleOp(group="teleop", name="TeleOpSimple")
public class TeleOpSimple extends LinearOpMode
{
    MecanumDrive drive;
    Grabber grabber;

    @Override
    public void runOpMode()
    {
        drive = new MecanumDrive(hardwareMap, telemetry);
        grabber = new Grabber(hardwareMap, telemetry);

        boolean slowMode = false;
        boolean slowWatch = false;
        double speed;

        boolean upWatch = false;
        boolean downWatch = false;
        int rotState = 0;

        waitForStart();

        while(opModeIsActive())
        {
            if(gamepad1.b && !slowWatch)
            {
                slowMode = !slowMode;
            }
            slowWatch = gamepad1.b;

            speed = slowMode ? 0.2 : 1;

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            drive.go(x * speed, y * speed, r * speed);

            if(gamepad1.y)
            {
                grabber.intake(Grabber.INTAKE_IN);
            }
            else if(gamepad1.a)
            {
                grabber.intake(Grabber.INTAKE_OUT);
            }
            else
            {
                grabber.intake(Grabber.INTAKE_HOLD);
            }

            if(gamepad1.dpad_up && !upWatch)
            {
                switch(rotState)
                {
                    case 0:
                        rotState = 1;
                        grabber.rotate(Grabber.ROTATE_ALIGN);
                        break;
                    case 1:
                        rotState = 2;
                        grabber.rotate(Grabber.ROTATE_DOWN);
                        break;
                    case 2:
                        break;
                }
            }
            else if(gamepad1.dpad_down && !downWatch)
            {
                switch(rotState)
                {
                    case 0:
                        break;
                    case 1:
                        rotState = 0;
                        grabber.rotate(Grabber.ROTATE_45);
                        break;
                    case 2:
                        rotState = 1;
                        grabber.rotate(Grabber.ROTATE_ALIGN);
                }
            }
            upWatch = gamepad1.dpad_up;
            downWatch = gamepad1.dpad_down;

            telemetry.update();
        }
    }
}
