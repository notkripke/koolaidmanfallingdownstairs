package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.components.Grabber;

@Disabled
@TeleOp(group="tests", name="ServoTestIntakeInner")
public class ServoTestsIntakeInner extends LinearOpMode
{
    CRServo rollerB;


    @Override
    public void runOpMode()
    {

        rollerB =  hardwareMap.crservo.get("rollerB");
        waitForStart();

        while (opModeIsActive())
        {
          rollerB.setPower(1);
        }
    }
}
