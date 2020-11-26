package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;

@Disabled
@Autonomous(group = "test", name = "turnTest")
public class turnTest extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponents();

        waitForStart();

        while (opModeIsActive()) {

            if (gamepad1.a) {
                TurnAbsolute(90,.2,.5);
            }
            if (gamepad1.b) {
                TurnAbsolute(-90,.2,.5);
            }
            if (gamepad1.y) {
                TurnAbsolute(45,.2,.5);
            }
            if (gamepad1.x) {
                TurnAbsolute(-45,.2,.5);
            }
            if (gamepad2.a) {
                TurnAbsolute(180,.2,.5);
            }
            if (gamepad2.b) {
                TurnAbsolute(-180,.2,.5);
            }
            if (gamepad2.y) {
                TurnAbsolute(0,.2,.5);
            }
            if (gamepad2.x) {
                TurnAbsolute(-15,.2,.5);
            }

        }
    }
}
