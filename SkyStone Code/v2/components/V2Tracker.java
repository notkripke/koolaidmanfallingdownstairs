package org.firstinspires.ftc.teamcode.v2.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.components.RevGyro;

public class V2Tracker
{
	//Other Components
	private Telemetry tele;
	public RevGyro gyro;

	//Hardware
	private DcMotor encoderL, encoderR, encoderF;

	//Constants
	private final double TWO_PI = 2 * Math.PI;

	private final double WHEEL_DIAMETER = 4; //inches
	private final double WHEEL_CIRCUMFERENCE = TWO_PI * WHEEL_DIAMETER; //inches
	private final double ENCODER_TICKS_PER_REVOLUTION = 360;
	private final double TICKS_PER_INCH = ENCODER_TICKS_PER_REVOLUTION / WHEEL_CIRCUMFERENCE;
	private final double INCHES_PER_TICK = 1 / TICKS_PER_INCH;

	private final double ENCODER_L_RATE = -INCHES_PER_TICK;
	private final double ENCODER_R_RATE = INCHES_PER_TICK;
	private final double ENCODER_F_RATE = INCHES_PER_TICK;

	private final double LR_RADIUS = 2; //inches
	private final double LR_CIRCUMFERENCE = LR_RADIUS * TWO_PI; //inches

	private final double FB_RADIUS = 2; //inches
	private final double FB_CIRCUMFERENCE = FB_RADIUS * TWO_PI; //inches

	//Local Variables
	private double lastL = 0;
	private double lastR = 0;
	private double lastF = 0;
	private double lastRot = 0;

	private double x, y, rot;

	private double rotFix = 0;

	public V2Tracker(HardwareMap hardwareMap, Telemetry telemetry)
	{
		tele = telemetry;

		encoderL = hardwareMap.dcMotor.get("encoderL");
		encoderR = hardwareMap.dcMotor.get("encoderR");
		encoderF = hardwareMap.dcMotor.get("encoderF");

		gyro = new RevGyro(hardwareMap, tele);

		x = 0;
		y = 0;
		rot = 0;
	}

	public void setPos(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void setRot(double rot)
	{
		double gyroVal = gyro.getAngle();

		rotFix = fixRadians(rot - gyroVal);
	}

	public void update()
	{
		double curL = encoderL.getCurrentPosition();
		double curR = encoderR.getCurrentPosition();
		double curF = encoderF.getCurrentPosition();
		double curRot = gyro.getAngle() + rotFix;

		double deltaRot = fixRadians(curRot - lastRot);
		if(deltaRot > Math.PI)
		{
			deltaRot -= TWO_PI;
		}

		double lr_rot_fix = (deltaRot / TWO_PI) * LR_CIRCUMFERENCE;
		double fb_rot_fix = (deltaRot / TWO_PI) * FB_CIRCUMFERENCE;

		double deltaL = (curL - lastL) * ENCODER_L_RATE + lr_rot_fix;
		double deltaR = (curR - lastR) * ENCODER_R_RATE + lr_rot_fix;
		double deltaF = (curF - lastF) * ENCODER_F_RATE + fb_rot_fix;

		double deltaFB = (deltaL + deltaR) / 2;
		double deltaRL = deltaF;

		x += deltaRL;
		y += deltaFB;
		rot = curRot;

		lastL = curL;
		lastR = curR;
		lastF = curF;
		lastRot = curRot;
	}

	public double getX()
	{
		return x;
	}

	public double getY()
	{
		return y;
	}

	public double getRot()
	{
		return rot;
	}

	//Turns ugly radian values like 5pi/2 or -3pi/2 into a nice pi/2
	private double fixRadians(double in)
	{
		double rad = in % TWO_PI;
		if(rad < 0)
		{
			rad = rad + (2 * Math.PI);
		}

		return rad;
	}
}
