package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Parker {
    Telemetry tele;

    public DcMotor parker;

    public static final double PARKER_IN = .5; //
    public static final double PARKER_OUT = -.5;

    public Parker(HardwareMap hardwareMap, Telemetry telemetry) {
        tele = telemetry;
        parker = hardwareMap.get(DcMotor.class, "parker");
    }

    public void parkerPow(double pow) {
        parker.setPower(pow);
    }

    public void setParkerEncoder(boolean encoder) {

        if (encoder) {
            parker.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            parker.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            parker.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        } else {
            parker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

    }
}
