package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;

@Disabled
@Autonomous(group = "test", name = "ethan2")
public class Ethan2 extends LinearOpMode {
    DigitalChannel touch;

    public void runOpMode() {
        touch = hardwareMap.get(DigitalChannel.class, "touch");
        touch.setMode(DigitalChannel.Mode.INPUT);
        waitForStart();
        while(opModeIsActive())
        {
            telemetry.addData("t" , touch.getState());
            telemetry.update();
        }
    }
    public void encoderMove(int target, double power){

    }
}
