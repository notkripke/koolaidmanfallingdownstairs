package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.AutoDrive;
import org.firstinspires.ftc.teamcode.components.Parker;

@Disabled
@Autonomous(group="test", name="AutoTesting")
public class AutoTesting extends GorillabotsCentral
{
    //+x: 5000 / 52 = 96.154
    //+y: 5000 / 56.5 = 88.495
    //+x+y: 5000 / 53 = 94.340

    public void runOpMode()
    {
//      AutoDrive drive = new AutoDrive(hardwareMap, telemetry);
        initializeComponentsAutonomous();;
        waitForStart();

       // ADrive.driveCartesian(0.2, 0.2, 50);
        parker.parkerPow(Parker.PARKER_OUT);
        sleep(2000);
        parker.parkerPow(0);
    }
}
