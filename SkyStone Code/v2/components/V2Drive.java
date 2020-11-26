package org.firstinspires.ftc.teamcode.v2.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class V2Drive
{
	private Telemetry tele;

	private DcMotor motorFL, motorFR, motorBL, motorBR;

	public V2Tracker tracker;

	public V2Drive(HardwareMap hardwareMap, Telemetry telemetry)
	{
		tele = telemetry;

		motorFL = hardwareMap.dcMotor.get("motorFL");
		motorFR = hardwareMap.dcMotor.get("motorFR");
		motorBL = hardwareMap.dcMotor.get("motorBL");
		motorBR = hardwareMap.dcMotor.get("motorBR");

		motorFL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorFR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorBL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
		motorBR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

		tracker = new V2Tracker(hardwareMap, tele);
	}
}
