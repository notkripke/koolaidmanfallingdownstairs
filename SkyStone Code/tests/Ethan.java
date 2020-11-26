package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Parker;

@Disabled
@Autonomous(group = "test", name = "ethan")
public class Ethan extends LinearOpMode {
    DcMotor motor;

    public void runOpMode() {
        motor = hardwareMap.get(DcMotor.class, "motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        waitForStart();
        encoderMove(4000,1);
        sleep(300);
        encoderMove(2000,.5);


    }
    public void encoderMove(int target, double power){
        motor.setPower(power);
        int start = motor.getCurrentPosition();
        int end = target + start;
        while (motor.getCurrentPosition() < end && opModeIsActive()) {
            telemetry.addData("curpos", motor.getCurrentPosition());
            telemetry.addData("end",end);
            telemetry.update();
        }
        motor.setPower(0);
    }
}
