package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Alignment;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;

@Autonomous(group = "test", name = "red43touchSAFE")

public class redAuto43touchSAFE extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        int pos = getRed();
        if(!opModeIsActive())
        {
            return;
        }

        grabber.intake(Grabber.INTAKE_IN);
        alignment.alignment(Alignment.ALIGN_DOWN);
        grabber.rotate(Grabber.ROTATE_ALIGN);
        if(!opModeIsActive())
        {
            return;
        }

        switch (pos) { //pick up first block
            case 1:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(24, 270, .4, 0);
                MoveUntilEncoderGYRO(8, 0, .7, 0);
                MoveUntilTouchRangeF(0, .2, 0);
                break;
            case 2:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(18, 270, .4, 0);
                MoveUntilEncoderGYRO(8, 0, .7, 0);
                MoveUntilTouchRangeF(0, .2, 0);//9.5
                break;
            case 3:
                MoveUntilEncoderGYRO(5, 0, .3, 0);
                MoveUntilEncoderGYRO(8, 270, .3, 0);
                MoveUntilEncoderGYRO(8, 0, .7, 0);
                MoveUntilTouchRangeF(0, .2, 0);//9.5
                break;
        }
        if(!opModeIsActive())
        {
            return;
        }
        grabber.rotate(Grabber.ROTATE_DOWN); //first block
        sleep(600);
        grabber.rotate(Grabber.ROTATE_45);
        alignment.alignment(Alignment.ALIGN_45);
        grabber.intake(Grabber.INTAKE_IN * .1);
        MoveUntilEncoder(2, 180, .3);
        TurnAbsolute(90, .2, .5);
        if(!opModeIsActive())
        {
            return;
        }
        switch (pos) {//drive over the first time
            case 1:
                MoveUntilEncoderGYRO(68, 180, .8, 92);
                MoveUntilRangeB(32, 180, .3);
                break;
            case 2:
                MoveUntilEncoderGYRO(55, 180, .8, 92);
                MoveUntilRangeB(30, 180, .3);
                break;
            case 3:
                MoveUntilEncoderGYRO(54, 180, .7, 92);
                MoveUntilRangeB(32, 180, .3);
                break;
        }
        if(!opModeIsActive())
        {
            return;
        }
        grabber.rotate(Grabber.ROTATE_INIT);
        alignment.alignment(Alignment.ALGIN_INIT);
        TurnAbsolute(0, .2, .5);
        MoveUntilRangeF(4, 0, .3);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(550);
        if(!opModeIsActive())
        {
            return;
        }
        grabber.intake(Grabber.INTAKE_OUT);
        grabber.rotate(Grabber.ROTATE_45);
        MoveUntilEncoder(5, 180, .2);
        grabber.intake(Grabber.INTAKE_HOLD);
        TurnAbsolute(-90, .2, .5);
        // MoveUntilEncoder(100,180,.3);
        if(!opModeIsActive())
        {
            return;
        }
        switch (pos) { //drive back and pick up the 2nd block
            case 1:
                MoveUntilEncoderGYRO(83, 180, .7, -83);
                MoveUntilEncoderGYRO(7,90,1,-90);
                MoveUntilRangeB(5, 180, .2);
                alignment.alignment(Alignment.ALIGN_DOWN);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_ALIGN);
                if(!opModeIsActive())
                {
                    return;
                }
                TurnAbsolute(0, .2, .5);
                MoveUntilTime(250, 270, .5);
                grabber.rotate(Grabber.ROTATE_ALIGN);
                MoveUntilTouchRangeF(0,.2, 0);
                TurnAbsolute(29, .2, .5);
                grabber.rotate(Grabber.ROTATE_DOWN);
                MoveUntilEncoderGYRO(.5, 180, .4, 30);
                sleep(500);
                TurnAbsolute(0, .2, .5);
                sleep(400);
                break;
            case 2:
                MoveUntilEncoderGYRO(75, 180, .8, -83);
                MoveUntilEncoderGYRO(7, 90, 1, -90);
                alignment.alignment(Alignment.ALIGN_DOWN);
                MoveUntilRangeB(5, 180, .2);
                TurnAbsolute(0, .2, .5);
                MoveUntilTime(500,270,.4);
                MoveUntilEncoderGYRO(4,90,.3,0);
                if(!opModeIsActive())
                {
                    return;
                }
                grabber.rotate(Grabber.ROTATE_ALIGN); //pick up second block
                grabber.intake(Grabber.INTAKE_IN);
                MoveUntilTouchRangeF(0, .2, 0);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(750);
                break;
            case 3:
                MoveUntilEncoderGYRO(64, 180, .55, -83);
                MoveUntilRangeB(13, 180, .2);
                MoveUntilEncoderGYRO(7, 90, .5, -90);
                alignment.alignment(Alignment.ALIGN_DOWN);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_ALIGN);
                TurnAbsolute(0, .2, .5);
                MoveUntilTouchRangeF(0, .2, 0);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(750);
                break;
        }
        if(!opModeIsActive())
        {
            return;
        }
        grabber.rotate(Grabber.ROTATE_45);
        alignment.alignment(Alignment.ALIGN_45);
        grabber.intake(Grabber.INTAKE_IN * .1);
        switch (pos) { //back up to get ready for final trek
            case 1:
                grabber.intake(Grabber.INTAKE_IN * .5);
                //MoveUntilEncoder(2, 180, .3);
                break;
            case 2:
                MoveUntilEncoder(2, 180, .3);
                break;
            case 3:
                MoveUntilEncoder(2, 180, .3);
                break;
        }
        if(!opModeIsActive())
        {
            return;
        }
        TurnAbsolute(-90, .2, .5);
        grabber.intake(Grabber.INTAKE_IN * .1);
        switch (pos) { //final trek
            case 1:
                MoveUntilEncoderGYRO(99, 0, .9, -85);
                break;
            case 2:
                MoveUntilEncoderGYRO(99, 0, .8, -85);
                break;
            case 3:
                MoveUntilEncoderGYRO(90, 0, .7, -85.7);
                break;
        } if(!opModeIsActive())
        {
            return;
        }
        grabber.rotate(Grabber.ROTATE_INIT);
        alignment.alignment(Alignment.ALGIN_INIT);
        hooks.hookR(Hooks.HOOKR_MID);
        hooks.hookL(Hooks.HOOKL_MID);
        if(!opModeIsActive())
        {
            return;
        }
        MoveUntilEncoderGYRO(7,90,1,-90);
        MoveUntilRangeF(20, 0, .5);
        TurnAbsolute(0, .2, .5);
        MoveUntilRangeF(4, 0, .3);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(350);
        grabber.rotate(Grabber.ROTATE_INIT);
        if(!opModeIsActive())
        {
            return;
        }
        MoveUntilEncoderGYRO(5,270,.5,0);
        MoveUntilRangeF(4, 0, .3);
        hooks.setDown(true);
        MoveUntilEncoder(4, 180, .2);
        if(!opModeIsActive())
        {
            return;
        }
        MoveUntilEncoder(14, 180, 1);
        specialTurn();
        grabber.intake(Grabber.INTAKE_HOLD);
        hooks.setDown(false);
        MoveUntilTime(500, 0, .7);
        if(!opModeIsActive())
        {
            return;
        }
        MoveUntilEncoder(8, 160, .5);
        MoveUntilEncoderGYRO(10,270,1,-90);
        if(!opModeIsActive())
        {
            return;
        }
        TurnAbsolute(-102, .2, .5);
        setParkerPos(900);
        parker.parkerPow(0);
        MoveUntilEncoder(10, 180, .5);
        return;
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
