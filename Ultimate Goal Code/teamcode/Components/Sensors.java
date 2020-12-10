package org.firstinspires.ftc.teamcode.Components;

import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sensors
{
    public DigitalChannel liftBot;
    public DigitalChannel liftTop;
    public DigitalChannel alignT;
    public Rev2mDistanceSensor rangeF;
    public Rev2mDistanceSensor rangeB;
    public Rev2mDistanceSensor rangeL;
    public Rev2mDistanceSensor rangeR;

    public Sensors(HardwareMap hardwareMap, Telemetry telemetry)
    {
        liftBot = hardwareMap.get(DigitalChannel.class,"liftBot");
        liftBot.setMode(DigitalChannel.Mode.INPUT);
        liftTop = hardwareMap.get(DigitalChannel.class,"liftTop");
        liftTop.setMode(DigitalChannel.Mode.INPUT);
        alignT = hardwareMap.get(DigitalChannel.class,"alignT");
        alignT.setMode(DigitalChannel.Mode.INPUT);
        rangeF = hardwareMap.get(Rev2mDistanceSensor.class, "rangeF");
        rangeB = hardwareMap.get(Rev2mDistanceSensor.class, "rangeB");
        rangeR = hardwareMap.get(Rev2mDistanceSensor.class, "rangeR");
        rangeL = hardwareMap.get(Rev2mDistanceSensor.class, "rangeL");
    }
    public double getDistanceF(){
        return rangeF.getDistance(DistanceUnit.INCH);
    }
    public double getDistanceB(){
        return rangeB.getDistance(DistanceUnit.INCH);
    }
    public double getDistanceR(){
        return rangeR.getDistance(DistanceUnit.INCH);
    }
    public double getDistanceL(){
        return rangeL.getDistance(DistanceUnit.INCH);
    }
}
