package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.MecanumDrive;

@TeleOp(group="tests", name="ServoTestRotate")
public class ServoTestsRotate extends LinearOpMode
{
	Grabber grabber;

	@Override
	public void runOpMode()
	{
		grabber = new Grabber(hardwareMap, telemetry);

		double position = 0.5;

		boolean increaseWatch = false;
		boolean decreaseWatch = false;

		waitForStart();

		grabber.rotate(0.5);

		while (opModeIsActive())
		{
			if (gamepad1.right_bumper && !increaseWatch)
			{
				position = position + .01;
			}
			increaseWatch = gamepad1.right_bumper;

			if (gamepad1.left_bumper && !decreaseWatch)
			{
				position = position - .01;
			}
			decreaseWatch = gamepad1.left_bumper;

			if (gamepad1.right_trigger > .5)
			{
				position = position + .1;
			}
			if (gamepad1.left_trigger > .5)
			{
				position = position - .1;
			}

			if (position > 1)
			{
				position = 1;
			}
			if (position < 0)
			{
				position = 0;
			}

			grabber.rotate(position);

			telemetry.addData("position", position);
			telemetry.update();
		}
	}
}
