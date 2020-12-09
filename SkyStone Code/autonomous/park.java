package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;


@Autonomous(group = "test", name = "park5")

public class park extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        waitForStart();

        MoveUntilEncoderGYRO(5, 180, .3, 0);
        if(!opModeIsActive())
        {
            return;
        }
        sleep(28000);

        return;
    }

}
