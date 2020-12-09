package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Alignment;
import org.firstinspires.ftc.teamcode.components.Capstone;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Parker;

@TeleOp(group = "AAAAAAAAAA", name = "TeleopRedLion")
public class TeleopRedLion extends GorillabotsCentral {

    @Override
    public void runOpMode() {

        initializeComponents();

        waitForStart(); // RIGHT TRIGGER PROGRESSES THE PROGRAM, RIGHT BUMPER DOES ACTIONS

        grabber.rotate(Grabber.ROTATE_INIT);
        grabber.intake(Grabber.INTAKE_HOLD);

        hooks.setDown(false);

        boolean driveSlow = false;
        boolean driveSlowWatch = false;

        boolean isHookDeployed = false;
        boolean isHookDeployedWatch = false;

        boolean isArmUp = false;
        boolean isArmUpWatch = false;

        boolean doIt = false;
        boolean doItWatch = false;

        int collectStage = 0;

        boolean releasing = false;
        boolean releasingWatch = false;

        int stage = 0;
        boolean switchStageWatch = false;

        int intCapStage = 0;
        boolean intCapStageWatch = false;

        boolean backIntCapStageWatch = false;
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

            // ACCESSORIES ↓
            double liftPower = -gamepad2.left_stick_y;
            if (stage == 3)
            {
                liftPower += .08;
            }

            if (liftPower > 1)
                liftPower = 1;
            grabber.lift(liftPower);

            // RULER ↓
            if (gamepad2.x) {
                parker.parkerPow(Parker.PARKER_IN * 2);
            }
            else if (gamepad2.y) {
                parker.parkerPow(Parker.PARKER_OUT * 2);
            }
            else{
                parker.parkerPow(0);
            }

            // HOOK CONTROL ↓
            hooks.setDown(isHookDeployed);

            // CAPSTONE ↓
            if (gamepad2.dpad_down)
            {
                capstone.capstone(Capstone.CAPSTONE_DEPLOY);
            }
            if (gamepad2.dpad_up)
            {
                capstone.capstone(Capstone.CAPSTONE_INIT);
            }
            // TOGGLES ↓

            if (gamepad1.b && !driveSlowWatch) {
                driveSlow = !driveSlow;
            }
            driveSlowWatch = gamepad1.b;

            if (gamepad2.right_bumper && !isHookDeployedWatch) {
                isHookDeployed = !isHookDeployed;
            }
            isHookDeployedWatch = gamepad2.right_bumper;

            if (gamepad2.left_bumper && !isArmUpWatch) {
                isArmUp = !isArmUp;
            }
            isArmUpWatch = gamepad2.left_bumper;

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

            if (gamepad2.dpad_right && !intCapStageWatch) {
                intCapStage += 1;
            }
            intCapStageWatch = gamepad2.dpad_right;

            if (gamepad2.dpad_left && !backIntCapStageWatch) {
                intCapStage -= 1;
            }
            backIntCapStageWatch = gamepad2.dpad_left;

            switch (intCapStage)
            {
                case -1:
                    intCapStage = 2;
                    break;
                case 0:
                    capstone.intCapstone(Capstone.INTCAPSTONE_SAFE);
                    break;
                case 1:
                    grabber.rotate(Grabber.ROTATE_INTCAPDEP);
                    capstone.intCapstone(Capstone.INTCAPSTONE_SAFE);
                    alignment.alignment(Alignment.ALGIN_INIT);
                    break;
                case 2:
                    grabber.rotate(Grabber.ROTATE_INTCAPDEP);
                    capstone.intCapstone(Capstone.INTCAPSTONE_ACTIVATE);
                    alignment.alignment(Alignment.ALGIN_INIT);
                    break;
                case 3:
                    intCapStage = 0;
                    break;

            }

            telemetry.addData("stage:", stage);
            telemetry.addData("capStage:", intCapStage);
            //BULK OF PROGRAM: STAGES 0-3 ↓

            switch (stage) {

                case -1: // for looping
                    stage = 3;
                    break;
                case 0: // normal: drive to collect
                    drive.go(x, y, r); // drive speed max

                    grabber.intake(Grabber.INTAKE_HOLD);


                    if(isArmUp){
                        grabber.rotate(Grabber.ROTATE_INIT);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else if (intCapStage >= 1) {
                        grabber.rotate(Grabber.ROTATE_INTCAPDEP);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else {
                        grabber.rotate(Grabber.ROTATE_45);
                        alignment.alignment(Alignment.ALIGN_TELEREST);
                    }
                    doIt = false; // prep for next stage ↓
                    collectStage = 0;
                    break;
                case 1: //collection
                    drive.go(x * .35, y * .35, r * .35); // drive speed 7/20
                    //alignment.alignment(Alignment.ALIGN_DOWN);
                    alignment.alignment(Alignment.ALIGN_TELEREST);
                    if(!sensors.alignT.getState()){
                        doIt = true;
                    }

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

                    if(isArmUp){
                        grabber.rotate(Grabber.ROTATE_INIT);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else if (intCapStage >= 1) {
                        grabber.rotate(Grabber.ROTATE_INTCAPDEP);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else {
                        grabber.rotate(Grabber.ROTATE_45);
                        alignment.alignment(Alignment.ALIGN_TELEREST);
                    }

                    releasing = false;
                    break;
                case 3:  //releasing
                    drive.go(x * .3, y * .3, r * .3); // drive speed 3/10

                    if(isArmUp){
                        grabber.rotate(Grabber.ROTATE_INIT);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else if (intCapStage >= 1) {
                        grabber.rotate(Grabber.ROTATE_INTCAPDEP);
                        alignment.alignment(Alignment.ALGIN_INIT);
                    }
                    else if (gamepad2.b)
                    {
                        grabber.rotate(Grabber.ROTATE_BARELY);
                    }
                    else {
                        grabber.rotate(Grabber.ROTATE_DOWN);
                        alignment.alignment(Alignment.ALIGN_TELEREST);
                    }


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
                    manualOverride = false;
                    alignment.alignment(Alignment.ALIGN_45);

                    while (opModeIsActive() && sensors.liftTop.getState() && !manualOverride) {

                        if (gamepad2.left_bumper && !isArmUpWatch) {
                            isArmUp = !isArmUp;
                        }
                        isArmUpWatch = gamepad2.left_bumper;

                        grabber.lift(-1);

                        manualOverride = (gamepad1.a || gamepad2.a);

                        drive.go(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
                    }

                    while (opModeIsActive() && sensors.liftBot.getState() && !manualOverride) {

                        if (gamepad2.left_bumper && !isArmUpWatch) {
                            isArmUp = !isArmUp;
                        }
                        isArmUpWatch = gamepad2.left_bumper;

                        grabber.lift(-.4);

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
