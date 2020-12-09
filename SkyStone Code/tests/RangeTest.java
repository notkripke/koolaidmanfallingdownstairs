package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;

@Disabled
@TeleOp(group = "test", name = "rangeTest")
public class RangeTest extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponents();

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("rangeF", sensors.getDistanceF());
            telemetry.addData("rangeB", sensors.getDistanceB());
            telemetry.addData("rangeR", sensors.getDistanceR());
            telemetry.addData("rangeL", sensors.getDistanceL());
            telemetry.update();
        }
    }
}
