package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;

@TeleOp(group = "test", name = "variousEncTest")
public class variousEncTest extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        waitForStart();
        grabber.rotate(Grabber.ROTATE_45);

        while (opModeIsActive()) {
            if (gamepad1.a) {
                setParkerPos(100);
            }
            if (gamepad1.b) {
                setParkerPos(400);
            }
            if (gamepad1.y) {
                setLiftPos(100);
            }
            if (gamepad1.x) {
                setLiftPos(400);
            }
            if (gamepad2.a) {
                MoveUntilEncoder(10, 180, .3);
            }
            if (gamepad2.b) {
                MoveUntilEncoder(20, 180, .3);
            }
            if (gamepad2.y) {
                MoveUntilEncoder(40, 180, .3);
            }
            if (gamepad2.x) {
                MoveUntilEncoder(80, 180, .3);
            }
        }
    }
}
