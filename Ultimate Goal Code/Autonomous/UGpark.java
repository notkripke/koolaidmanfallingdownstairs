package org.firstinspires.ftc.teamcode.Ultimate Goal Code.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;

@Autonomous(group = "test", name = "UGpark")

public class UGpark extends GorillabotsCentral {
    public void runOpMode() {

        waitForStart();

        MoveUntilEncoderGYRO(5, 180, .3, 0);//distance (rotations[12.57"]),
                                            // direction (degrees), power, gyroT(?)
        if(!opModeIsActive())
        {
            return;
        }
        sleep(28000);//time until end of auto period

        return;
    }

}
