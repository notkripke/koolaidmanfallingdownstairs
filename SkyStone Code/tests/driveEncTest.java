package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.AutoDrive;
import org.firstinspires.ftc.teamcode.components.Grabber;

@Disabled
@TeleOp(group = "test", name = "driveEncTest")
public class driveEncTest extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        waitForStart();
        grabber.rotate(Grabber.ROTATE_45);

        while (opModeIsActive()) {
            if (gamepad1.a) {
                MoveUntilEncoderGYRO(80,0,.3,0);
            }
            if (gamepad1.b) {
                MoveUntilEncoderGYRO(80,0,.4,0);
            }
            if (gamepad1.y) {
                MoveUntilEncoderGYRO(80,0,.5,0);
            }
            if (gamepad1.x) {
                MoveUntilEncoderGYRO(80,0,1,0);
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
