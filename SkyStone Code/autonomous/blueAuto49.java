package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Alignment;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;
@Disabled
@Autonomous(group = "test", name = "blue49 - 3 Stone")

public class blueAuto49 extends GorillabotsCentral {
    public void runOpMode() {
        initializeComponentsAutonomous();

        int pos = getBlue();

        grabber.intake(Grabber.INTAKE_IN);
        alignment.alignment(Alignment.ALIGN_DOWN);
        grabber.rotate(Grabber.ROTATE_ALIGN);

        switch (pos){
            case 1:
                return;
                //break;
            case 2:
                MoveUntilEncoderGYRO(12,0,1,0);
                MoveUntilTouch(0,.2,0);
                break;
            case 3:
                return;
                //break;
        }

        grabber.rotate(Grabber.ROTATE_DOWN); //first block
        sleep(550);
        grabber.rotate(Grabber.ROTATE_45);
        alignment.alignment(Alignment.ALIGN_45);
        grabber.intake(Grabber.INTAKE_IN * .1);
        MoveUntilEncoderGYRO(5,180,.3,0);
        MoveUntilEncoderGYRO(60,180,.5,-88); //Turn
        hooks.hookL(Hooks.HOOKL_MID);
        hooks.hookR(Hooks.HOOKR_MID);
        MoveUntilEncoderGYRO(28,180,.75,-88);
        TurnAbsolute(0,.2,5);
        MoveUntilRangeFwithG(4,0,.3,0);
        hooks.setDown(true); //drag foundation
        sleep(500);
        grabber.intake(Grabber.INTAKE_OUT);
        MoveUntilEncoderGYRO(12,180,.5,0);
        TurnAbsolute(90,.5,1);
        MoveUntilEncoderGYRO(20,180,1,98);
        hooks.setDown(false); //done manipulating first foundation
       // TurnAbsolute(90,.2,.5);
        MoveUntilEncoderGYRO(55,180,1,91);
        MoveUntilEncoderGYRO(7,270,1,90);
        alignment.alignment(Alignment.ALIGN_DOWN);
        MoveUntilRangeB(5,180,.2);
        TurnAbsolute(0,.2,.5);
        grabber.rotate(Grabber.ROTATE_ALIGN); //pick up second block
        grabber.intake(Grabber.INTAKE_IN);
        MoveUntilTouch(0,.2,0);
        grabber.rotate(Grabber.ROTATE_DOWN);
        sleep(550);
        grabber.rotate(Grabber.ROTATE_45);
        alignment.alignment(Alignment.ALIGN_45);
        grabber.intake(Grabber.INTAKE_IN * .1);
        MoveUntilEncoderGYRO(3,180,.3,0);
        TurnAbsolute(90,.2,.5);
        MoveUntilEncoderGYRO(52,0,1,91);
        MoveUntilRangeF(4,0,.3);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(400);
        MoveUntilEncoderGYRO(20,180,1,92);
        MoveUntilRangeB(17,180,.2);
        alignment.alignment(Alignment.ALIGN_DOWN);
        MoveUntilEncoderGYRO(5,270,.5,90);
        TurnAbsolute(0,.2,.5);
        grabber.rotate(Grabber.ROTATE_ALIGN); //pick up third block
        grabber.intake(Grabber.INTAKE_IN);
        MoveUntilTouchRangeF(0,.2,0);
        grabber.rotate(Grabber.ROTATE_DOWN);
        sleep(550);
        grabber.rotate(Grabber.ROTATE_45);
        alignment.alignment(Alignment.ALIGN_45);
        grabber.intake(Grabber.INTAKE_IN * .1);
        MoveUntilEncoderGYRO(3,180,.3,0);
        TurnAbsolute(90,.2,.5);
        hooks.hookR(Hooks.HOOKR_MID);
        hooks.hookL(Hooks.HOOKL_MID);
        MoveUntilEncoderGYRO(40,0,1,92);
        grabber.rotate(Grabber.ROTATE_INTCAPDEP);
        alignment.alignment(Alignment.ALGIN_INIT);
        MoveUntilRangeF(4,0,.3);
        MoveUntilEncoderGYRO(10,90,1,90);
        hooks.setDown(true);
        sleep(300);
        grabber.intake(Grabber.INTAKE_OUT);
        TurnAbsolute(130,.5,.1);
        specialPush();
        hooks.setDown(false);
        sleep(1100);
        MoveUntilEncoderGYRO(10,180,1,83);
        TurnAbsolute(90,.3,.7);
        setParkerPos(900);
        return;
    }
    public void specialPush()
    {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        drive.mfr.setPower(-.7);
        drive.mbr.setPower(-.7);
        drive.mbl.setPower(-.5);
        drive.mfl.setPower(-.5);

    }
}
