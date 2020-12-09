package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;

@Disabled
@TeleOp(group = "AAAAAAAAAA", name = "TeleopLancaster")
public class TeleopLancaster extends GorillabotsCentral {

    @Override
    public void runOpMode() {

        initializeComponents();

        waitForStart(); // RIGHT TRIGGER PROGRESSES THE PROGRAM, RIGHT BUMPER DOES ACTIONS

        boolean driveSlow = false;
        boolean driveSlowWatch = false;

        boolean doIt = false;
        boolean doItWatch = false;

        boolean endgame = false;
        boolean endgameWatch = false;

        int collectStage = 0;

        boolean releasing = false;
        boolean releasingWatch = false;

        int stage = 0;
        boolean switchStageWatch = false;

        boolean backStageWatch = false;

        boolean manualOverride = false;

        double x = 0;
        double y = 0;
        double r = 0;

        while (opModeIsActive()) {

            // SET DRIVING STUFF ↓

            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = gamepad1.right_stick_x;

            // ACCESSORIES
            double liftPower = -gamepad2.left_stick_y + .05;
            if (liftPower > 1)
                liftPower = 1;
            grabber.lift(liftPower);

            // TOGGLES ↓

            if ((gamepad1.dpad_left) || (gamepad1.dpad_right) && !endgameWatch) {
                endgame = !endgame;
            }
            endgameWatch = (gamepad1.dpad_left) || (gamepad1.dpad_right);

            if (gamepad1.b && !driveSlowWatch) {
                driveSlow = !driveSlow;
            }
            driveSlowWatch = gamepad1.b;

            if (gamepad1.right_bumper && !doItWatch) {
                doIt = !doIt;
            }
            doItWatch = gamepad1.right_bumper;
            if (gamepad1.right_bumper && !releasingWatch) {
                releasing = !releasing;
            }
            releasingWatch = gamepad1.right_bumper;
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
            if (endgame) {
                grabber.rotate(Grabber.ROTATE_INIT);
                drive.go(x * .4, y * .4, r * .4);

                if(gamepad1.dpad_down)
                    hooks.setDown(true);
                if (gamepad1.dpad_up)
                    hooks.setDown(false);

            } else {

                switch (stage) {

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
                        drive.go(x * .25, y * .25, r * .25); // drive speed 1/4

                        switch (collectStage) {
                            case 0: //collecting
                                if (doIt) {
                                    grabber.rotate(Grabber.ROTATE_DOWN);
                                    grabber.intake(Grabber.INTAKE_IN);
                                } else {
                                    grabber.rotate(Grabber.ROTATE_ALIGN);
                                    grabber.intake(Grabber.INTAKE_HOLD);
                                }
                                break;
                            case 1: //if collected bad - need to release
                                grabber.intake(Grabber.INTAKE_OUT);
                                break;
                        }

                        if (gamepad1.right_bumper || gamepad1.a) { // re-collect if collected bad - collectStage = 0 default
                            collectStage = 0;
                        }

                        if (gamepad1.left_bumper) { // if collected bad
                            collectStage = 1;
                        }
                        break;
                    case 2:  //normal: transporting
                        drive.go(x, y, r); // drive speed max

                        grabber.intake(Grabber.INTAKE_HOLD);
                        grabber.rotate(Grabber.ROTATE_45);

                        releasing = false;
                        break;
                    case 3:  //releasing
                        drive.go(x * .3, y * .3, r * .3); // drive speed 3/10
                        grabber.rotate(Grabber.ROTATE_DOWN);

                        if (releasing) {
                            grabber.intake(Grabber.INTAKE_OUT);
                            grabber.lift(.4);
                            releasing = false;
                            while (!releasing && opModeIsActive()) {
                                releasing = gamepad1.right_trigger > .5;
                            }
                            releasing = false;
                        } else {
                            grabber.intake(Grabber.INTAKE_HOLD);
                        }

                        break;

                    case 4: //bring lift down
                        grabber.rotate(Grabber.ROTATE_INIT);

                        sleep(100);

                        while (opModeIsActive() && sensors.liftBot.getState() && !manualOverride) {
                            grabber.lift(-1);

                            manualOverride = (gamepad1.a || gamepad2.a);

                            drive.go(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
                        }
                        stage += 1;
                        break;
                    case 5:
                        stage = 0;
                        break;
                }
                telemetry.update();
            }
        }
    }
}
