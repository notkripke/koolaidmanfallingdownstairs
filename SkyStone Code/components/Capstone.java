package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Capstone {
    Telemetry tele;

    public Servo capstone;
    public Servo intCapstone;

    public static final double CAPSTONE_INIT = .24; //
    public static final double CAPSTONE_DEPLOY = .5;

    public static final double INTCAPSTONE_SAFE = 0;
    public static final double INTCAPSTONE_ACTIVATE = 1;

    public Capstone(HardwareMap hardwareMap, Telemetry telemetry) {
        tele = telemetry;
        capstone = hardwareMap.get(Servo.class, "capstone");
        intCapstone = hardwareMap.get(Servo.class, "intCapstone");
    }
    public void capstone(double pos)
    {
        capstone.setPosition(pos);
    }
    public void intCapstone(double pos){intCapstone.setPosition(pos);}
}
