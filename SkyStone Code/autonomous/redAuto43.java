package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;
import org.firstinspires.ftc.teamcode.components.Parker;
@Disabled
@Autonomous(group = "test", name = "red43")

public class redAuto43 extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        int pos = getRed();

        grabber.rotate(Grabber.ROTATE_INIT);
        grabber.intake(Grabber.INTAKE_IN);
        //ADrive.driveCartesian(.3,.3,50);
        switch (pos) { //pick up first block
            case 1:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(27, 270, .6, 0);
                MoveUntilRangeF(10, 0, .3);//9
                //TurnAbsolute(-15, .2, .5);
                break;
            case 2:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(21, 270, .3, 0);
                MoveUntilRangeF(10, 0, .3);//9.5
                //TurnAbsolute(-15, .2, .5);
                break;
            case 3:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(11, 270, .3, 0);
                MoveUntilRangeF(10, 0, .3);//9.5
                //TurnAbsolute(-15, .2, .5);
                break;
        }

        grabber.rotate(Grabber.ROTATE_DOWN);
        sleep(1000);
        grabber.intake(Grabber.INTAKE_HOLD);
        grabber.rotate(Grabber.ROTATE_45);
        MoveUntilEncoder(2, 180, .3);
        TurnAbsolute(90, .2, .5);

        switch (pos) {//drive over the first time
            case 1:
                MoveUntilEncoderGYRO(68, 180, .7, 92);
                MoveUntilRangeB(32, 180, .3);
                break;
            case 2:
                MoveUntilEncoderGYRO(60, 180, .6, 92);
                MoveUntilRangeB(28, 180, .3);
                break;
            case 3:
                MoveUntilEncoderGYRO(54, 180, .5, 92);
                MoveUntilRangeB(28, 180, .3);
                break;
        }
        grabber.rotate(Grabber.ROTATE_INIT);
        TurnAbsolute(0, .2, .5);
        MoveUntilRangeF(4, 0, .3);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(750);
        grabber.rotate(Grabber.ROTATE_45);
        grabber.intake(Grabber.INTAKE_HOLD);
        MoveUntilEncoder(7, 180, .3);
        TurnAbsolute(-90, .2, .5);
        // MoveUntilEncoder(100,180,.3);

        switch (pos) { //drive back and pick up the 2nd block
            case 1:
                MoveUntilEncoderGYRO(83, 180, .7, -83);
                MoveUntilRangeB(5, 180, .3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                TurnAbsolute(0, .2, .5);
                MoveUntilTime(500, 270, .5);
                MoveUntilTime(250,180,.4);
                MoveUntilRangeF(11, 0, .2);
                grabber.rotate(Grabber.ROTATE_ALIGN);
                TurnAbsolute(29, .2, .5);
                MoveUntilEncoderGYRO(6, 0, .4, 30);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(500);
                grabber.rotate(Grabber.INTAKE_HOLD);
                TurnAbsolute(0, .2, .5);
                break;
            case 2:
                MoveUntilEncoderGYRO(75, 180, .5, -83);
                MoveUntilRangeB(5, 180, .3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                //MoveUntilEncoderGYRO(7.5,90,.3,-83);
                //TurnAbsolute(0,.2,.5);
                TurnAbsolute(0, .2, .5);
                MoveUntilTime(500,270,.3);
                TurnAbsolute(0, .2, .5);
                MoveUntilTime(250,180,.4);
                MoveUntilRangeF(9.5, 0,.3);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(1000);
                break;
            case 3:
                MoveUntilEncoderGYRO(86, 180, .5, -83);
                MoveUntilRangeB(16, 180, .3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                TurnAbsolute(0, .2, .5);
                MoveUntilRangeF(10, 0, .3);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(1000);
                break;
        }

        grabber.intake(Grabber.INTAKE_HOLD);
        grabber.rotate(Grabber.ROTATE_45);
        switch (pos) { //back up to get ready for final trek
            case 1:
                MoveUntilEncoder(2, 180, .3);
                break;
            case 2:
                MoveUntilEncoder(2, 180, .3);
                break;
            case 3:
                MoveUntilEncoder(2, 180, .3);
                break;
        }

        TurnAbsolute(-90, .2, .5);

        switch (pos) { //final trek
            case 1:
                MoveUntilEncoderGYRO(106, 0, .7, -85.7);
                break;
            case 2:
                MoveUntilEncoderGYRO(106, 0, .6, -85.7);
                break;
            case 3:
                MoveUntilEncoderGYRO(100, 0, .5, -85.7);
                break;
        }
        grabber.rotate(Grabber.ROTATE_INIT);
        hooks.hookR(Hooks.HOOKR_MID);
        hooks.hookL(Hooks.HOOKL_MID);
        MoveUntilRangeF(20, 0, .5);
        TurnAbsolute(0, .2, .5);
        MoveUntilRangeF(4,0,.3);
        hooks.setDown(true);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(750);
        grabber.rotate(Grabber.ROTATE_45);
        grabber.intake(Grabber.INTAKE_HOLD);

        switch (pos){ //reposition the platform
            case 1:
                MoveUntilEncoder(4,180,.2);
                MoveUntilEncoder(14,180,.35);
                specialTurn();
                hooks.hookR(Hooks.HOOKR_MID);
                hooks.hookL(Hooks.HOOKL_MID);
                sleep(250);
                //parker.parkerPow(Parker.PARKER_OUT);
                MoveUntilEncoder(4, 180, .5);
                hooks.setDown(true);
                sleep(150);
                setParkerPos(900);
                MoveUntilTime(500, 0, .7);
                MoveUntilEncoder(8, 160, .5);
                TurnAbsolute(-102,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.5);
                break;
            case 2:
                MoveUntilEncoder(4,180,.2);
                MoveUntilEncoder(13,180,.3);
                specialTurn();
                hooks.setDown(false);
                sleep(500);
                MoveUntilEncoder(4, 180, .5);
                hooks.setDown(true);
                //parker.parkerPow(Parker.PARKER_OUT);
                sleep(150);
                setParkerPos(900);
                MoveUntilTime(500, 0, .7);
                MoveUntilEncoder(8, 160, .5);
                TurnAbsolute(-102,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.5);
                break;
            case 3:
                MoveUntilEncoder(4,180,.2);
                MoveUntilEncoder(12.9,180,.3);
                specialTurn();
                hooks.setDown(false);
                sleep(500);
                MoveUntilEncoder(4, 180, .5);
                hooks.setDown(true);
              //  parker.parkerPow(Parker.PARKER_OUT);
                sleep(150);
                setParkerPos(900);
                MoveUntilTime(500, 0, .7);
                MoveUntilEncoder(8, 160, .5);
                TurnAbsolute(-102,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.5);
                break;
        }


        //parker.parkerPow(0);
        //MoveUntilEncoder(20,180,.4);
    }

    private void specialTurn() {
        setDriveEncoderOn(false);
        drive.mfr.setPower(.7);
        drive.mbr.setPower(.7);
        drive.mbl.setPower(-.3);
        drive.mfl.setPower(-.3);

        while ((gyro.getAngle() > -90) && opModeIsActive()) {
            //hello
        }
        stopMotors();

        //TurnAbsolute(110,.9,1);
    }
}
