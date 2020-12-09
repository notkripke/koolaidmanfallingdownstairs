package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Hooks
{
    Telemetry tele;

    Servo hookR;
    Servo hookL;

    public static final double HOOKR_DOWN = .72;
    public static final double HOOKR_UP = .09;
    public static final double HOOKR_MID = .54;

    public static final double HOOKL_DOWN = .82;
    public static final double HOOKL_UP = .01;
    public static final double HOOKL_MID = .6;

    public Hooks(HardwareMap hardwareMap, Telemetry telemetry)
    {
        tele = telemetry;
        hookR = hardwareMap.servo.get("hookR");
        hookL = hardwareMap.servo.get("hookL");
    }

    public void hookR(double pos)
    {
        hookR.setPosition(pos);
    }
    public void hookL(double pos)
    {
        hookL.setPosition(pos);
    }
    public void setDown(boolean down)
    {
        hookR(down ? HOOKR_DOWN : HOOKR_UP);
        hookL(down ? HOOKL_DOWN : HOOKL_UP);
    }

}
