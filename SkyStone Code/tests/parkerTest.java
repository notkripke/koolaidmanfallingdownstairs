package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;

@Disabled
@TeleOp(group = "test", name = "parkerTest")
public class parkerTest extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponents();

        waitForStart();
        grabber.rotate(Grabber.ROTATE_45);

        while (opModeIsActive()) {
            if (gamepad1.a) {
               parker.parkerPow(.3);
            }
            if (gamepad1.b) {
                parker.parkerPow(-.3);
            }
            if (gamepad1.y) {
                setParkerPos(800);
            }
            if (gamepad1.x) {
                parker.setParkerEncoder(false);
                parker.parkerPow(1);
            }
            parker.parkerPow(0);

        }
    }
}
