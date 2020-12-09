package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;

@Disabled
@TeleOp(group = "tests", name = "TestingTeleop")
public class TestingTeleop extends GorillabotsCentral {


    @Override
    public void runOpMode() {

        initializeComponents();

        waitForStart();

        boolean driveSlow = false;
        boolean driveSlowWatch = false;

        while (opModeIsActive()) {
            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            if (gamepad1.b && !driveSlowWatch) {
                driveSlow = !driveSlow;
            }
            driveSlowWatch = gamepad1.b;
            if (driveSlow) {
                drive.go(x * .5, y * .5, r * .5);
            } else {
                drive.go(x, y, r);
            }
            if (gamepad1.right_bumper) {
                grabber.intake(Grabber.INTAKE_IN);
            }
            if (gamepad1.right_trigger > .5) {
                grabber.intake(Grabber.INTAKE_HOLD);
            }
            if (gamepad1.y) {
                grabber.intake(Grabber.INTAKE_HOLD);
            }
            if (gamepad1.left_bumper) {
                grabber.rotate(Grabber.ROTATE_DOWN);
            }
            if (gamepad1.left_trigger > .5) {
                grabber.rotate(Grabber.ROTATE_45);
            }
            if (gamepad1.a) {
                grabber.rotate(Grabber.ROTATE_ALIGN);
            }
            if(gamepad1.dpad_down){
                hooks.setDown(true);
            }
            if (gamepad1.dpad_up){
                hooks.setDown(false);
            }


        }
    }
}
