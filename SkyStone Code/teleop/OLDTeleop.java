package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.MecanumDrive;

@Disabled
@TeleOp(group = "main", name = "ooooTeleOp")
public class OLDTeleop extends LinearOpMode
{
    MecanumDrive drive;
    Grabber grabber;

    @Override
    public void runOpMode()
    {

        drive = new MecanumDrive(hardwareMap, telemetry);
        grabber = new Grabber(hardwareMap, telemetry);

        waitForStart(); // RIGHT TRIGGER PROGRESSES THE PROGRAM, RIGHT BUMPER DOES ACTIONS

        boolean driveSlow = false;
        boolean driveSlowWatch = false;

        boolean doIt = false;
        boolean doItWatch = false;

        int collectStage = 0;
        int releaseStage = 0;
        boolean releaseStageWatch = false;

        int stage = 0;
        boolean switchStageWatch = false;

        boolean backStageWatch = false;


        while (opModeIsActive())
        {

            // SET DRIVING STUFF ↓

            double x = gamepad1.left_stick_x;
            double y = -gamepad1.left_stick_y;
            double r = gamepad1.right_stick_x;

            // SET PERFECT DRIVE ↓

            if(gamepad1.dpad_up){
                drive.go(0,.15,0);
            }
            if(gamepad1.dpad_down){
                drive.go(0,-.15,0);
            }
            if(gamepad1.dpad_right){
                drive.go(.15,0,0);
            }
            if (gamepad1.dpad_left){
                drive.go(-.15,0,0);
            }

            // TOGGLES ↓

            if (gamepad1.b && !driveSlowWatch) {
                driveSlow = !driveSlow;
            }
            driveSlowWatch = gamepad1.b;

            if (gamepad1.right_bumper && !doItWatch) {
                doIt = !doIt;
            }
            doItWatch = gamepad1.right_bumper;
            if (gamepad1.right_bumper && !releaseStageWatch) {
                releaseStage += 1;
            }
            releaseStageWatch = gamepad1.right_bumper;
            if (gamepad1.right_trigger > .5 && !switchStageWatch) {
                stage += 1;
            }
            switchStageWatch = gamepad1.right_trigger > .5;
            if (gamepad1.left_trigger > .5 && !backStageWatch) {
                stage -= 1;
            }
            backStageWatch = gamepad1.left_trigger > .5;

            telemetry.addData("stage:", stage);

            //BULK OF PROGRAM: STAGES 0-3 ↓

            switch (stage)
            {

                case -1: // for looping
                    stage = 3;
                    break;
                case 0: // normal: drive to collect
                    drive.go(x, y, r); // drive speed max

                    grabber.intake(Grabber.INTAKE_HOLD);
                    grabber.rotate(Grabber.ROTATE_45);

                    doIt = false; // prep for next stage ↓
                    collectStage = 0;
                    break;
                case 1: //collection
                    drive.go(x * .2, y * .2, r * .2); // drive speed 1/5

                    switch (collectStage)
                    {
                        case 0: //collecting
                            if (doIt)
                            {
                                grabber.rotate(Grabber.ROTATE_DOWN);
                                grabber.intake(Grabber.INTAKE_IN);
                            }
                            else
                                {
                                grabber.rotate(Grabber.ROTATE_ALIGN);
                                grabber.intake(Grabber.INTAKE_HOLD);
                            }
                            break;
                        case 1: //if collected bad - need to release
                            grabber.intake(Grabber.INTAKE_OUT);
                            break;
                    }

                    if (gamepad1.right_bumper || gamepad1.a)
                    { // re-collect if collected bad - collectStage = 0 default
                        collectStage = 0;
                    }

                    if (gamepad1.left_bumper)
                    { // if collected bad
                        collectStage = 1;
                    }
                    break;
                case 2:  //normal: transporting
                    drive.go(x, y, r); // drive speed max

                    grabber.intake(Grabber.INTAKE_HOLD);
                    grabber.rotate(Grabber.ROTATE_45);

                    releaseStage = 0;
                    break;
                case 3:  //releasing
                    drive.go(x * .3, y * .3, r * .3); // drive speed 3/10

                    switch (releaseStage)
                    {
                        case 0:
                            grabber.rotate(Grabber.ROTATE_ALIGN);
                            break;
                        case 1:
                            grabber.rotate(Grabber.ROTATE_DOWN);
                            grabber.intake(Grabber.INTAKE_OUT); //backwards
                            break;
                        case 2:
                            grabber.rotate(Grabber.ROTATE_45);
                            break;
                        case 3:
                            releaseStage = 0;
                            break;
                    }
                    break;
                case 4: //set back to stage 0 - loop
                    stage = 0;
                    break;
            }
            telemetry.update();
        }
    }
}