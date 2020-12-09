package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;
import org.firstinspires.ftc.teamcode.components.Parker;

@Disabled
@Autonomous(group="test", name="blue43Safe")

public class blueAuto43Safe extends GorillabotsCentral
{
    public void runOpMode()
    {
        initializeComponentsAutonomous();

        int pos = getBlue();

        if(!opModeIsActive())
        {
            return;
        }

        grabber.rotate(Grabber.ROTATE_INIT);
        grabber.intake(Grabber.INTAKE_IN);
        //ADrive.driveCartesian(.3,.3,50);



        switch (pos)
        {
            case 1:
                MoveUntilRangeF(9.25,0,.3);
                MoveUntilEncoderGYRO(11.5,270,.3,0);
                TurnAbsolute(-15,.2,.5);
                break;
            case 2:
                MoveUntilRangeF(9,0,.3);
                TurnAbsolute(-15,.2,.5);
                break;
            case 3:
                /*MoveUntilRangeF(9.5,0,.3);
                MoveUntilEncoderGYRO(7,90,.3,0);
                TurnAbsolute(-15,.2,.5);*/

                MoveUntilEncoderGYROfl(20,45,.3,0);
                MoveUntilRangeF(9.25,0,.3);
                TurnAbsolute(-15,.2,.5);
                break;

        }

        if(!opModeIsActive())
        {
            return;
        }

        grabber.rotate(Grabber.ROTATE_DOWN);
        sleep(1500);
        grabber.intake(Grabber.INTAKE_HOLD);
        grabber.rotate(Grabber.ROTATE_45);

        if(!opModeIsActive())
        {
            return;
        }

        MoveUntilEncoder(5,180,.3);
        TurnAbsolute(-90,.2,.5);
        switch (pos){

            case 1:
                MoveUntilEncoderGYRO(54,180,.5,-88);
                break;
            case 2:
                MoveUntilEncoderGYRO(60,180,.5,-88);
                break;
            case 3:
                MoveUntilEncoderGYRO(68,180,.5,-88);
                break;
        }

        if(!opModeIsActive())
        {
            return;
        }

        MoveUntilRangeB(28,180,.3);
        grabber.rotate(Grabber.ROTATE_INIT);
        TurnAbsolute(0,.2,.5);
        MoveUntilRangeF(4,0,.3);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(750);
        grabber.rotate(Grabber.ROTATE_45);
        grabber.intake(Grabber.INTAKE_HOLD);
        MoveUntilEncoder(5,180,.3);
        TurnAbsolute(93,.2,.5);
       // MoveUntilEncoder(100,180,.3);

        if(!opModeIsActive())
        {
            return;
        }

        switch(pos) {
            case 1:
                MoveUntilEncoderGYRO(86,180,.5,90);
                MoveUntilRangeB(18,180,.3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                TurnAbsolute(-6,.2,.5);
                MoveUntilRangeF(8,0,.2);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(1000);
                break;
            case 2:
                MoveUntilEncoderGYRO(100,180,.5,90);
                MoveUntilRangeB(5,180,.3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                TurnAbsolute(-6,.2,.5);
                MoveUntilRangeF(8,0,.2);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(1000);
                break;
            case 3:
                MoveUntilEncoderGYRO(100,180,.5,90);
                MoveUntilRangeB(5,180,.3);
                grabber.intake(Grabber.INTAKE_IN);
                grabber.rotate(Grabber.ROTATE_INIT);
                TurnAbsolute(0,.2,.5);
                MoveUntilTime(500,90,.3);
                MoveUntilRangeF(8,0,.2);
                TurnAbsolute(-29,.2,.5);
                grabber.rotate(Grabber.ROTATE_ALIGN);
                MoveUntilEncoderGYRO(3,30,.4,-30);
                grabber.rotate(Grabber.ROTATE_DOWN);
                sleep(600);
                TurnAbsolute(0,.2,.5);
                break;
        }

        if(!opModeIsActive())
        {
            return;
        }

        grabber.intake(Grabber.INTAKE_HOLD);
        grabber.rotate(Grabber.ROTATE_45);
        switch (pos) {
            case 1:
                MoveUntilEncoder(2,180,.3);
                break;
            case 2:
                MoveUntilEncoder(2,180,.3);
                break;
            case 3:
                MoveUntilEncoder(3.5,180,.3);
                break;
        }

        if(!opModeIsActive())
        {
            return;
        }

        TurnAbsolute(90,.2,.5);
        switch (pos){
            case 1:
                MoveUntilEncoderGYRO(100,0,.5,93);
                break;
            case 2:
                MoveUntilEncoderGYRO(106,0,.5,93);
                break;
            case 3:
                MoveUntilEncoderGYRO(106,0,.5,93);
                break;
        }

        if(!opModeIsActive())
        {
            return;
        }

        grabber.rotate(Grabber.ROTATE_INIT);
        MoveUntilRangeF(20, 0,.5);
        TurnAbsolute(0,.2,.5);
        MoveUntilRangeF(4,0,.3);
        hooks.setDown(true);
        grabber.rotate(Grabber.ROTATE_DOWN);
        grabber.intake(Grabber.INTAKE_OUT);
        sleep(750);
        grabber.rotate(Grabber.ROTATE_45);
        grabber.intake(Grabber.INTAKE_HOLD);
        MoveUntilEncoder(11,180,.3);
        specialTurn();

        if(!opModeIsActive())
        {
            return;
        }

        switch (pos){
            case 1:
                hooks.setDown(false);
                sleep(500);
                MoveUntilEncoder(4,180,.5);
                hooks.setDown(true);
                sleep(750);
                parker.parkerPow(Parker.PARKER_OUT);
                MoveUntilTime(1000,0,.3);
                MoveUntilEncoder(4,160,.5);
                TurnAbsolute(110,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.3);
                break;
            case 2:
                hooks.setDown(false);
                sleep(500);
                MoveUntilEncoder(4,180,.5);
                hooks.setDown(true);
                sleep(750);
                parker.parkerPow(Parker.PARKER_OUT);
                MoveUntilTime(1000,0,.3);
                MoveUntilEncoder(4,160,.5);
                TurnAbsolute(110,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.3);
                break;
            case 3:
                parker.parkerPow(Parker.PARKER_OUT);
                hooks.hookR(Hooks.HOOKR_MID);
                hooks.hookL(Hooks.HOOKL_MID);
                sleep(250);
                MoveUntilEncoder(4,180,.5);
                hooks.setDown(true);
                sleep(250);
                MoveUntilTime(400,0,.5);
                MoveUntilEncoder(10,160,1);
                TurnAbsolute(110,.2,.5);
                parker.parkerPow(0);
                MoveUntilEncoder(10,180,.3);
                break;
        }

        if(!opModeIsActive())
        {
            return;
        }

    }
    private void specialTurn(){
        setDriveEncoderOn(false);
        drive.mfr.setPower(-.3);
        drive.mbr.setPower(-.3);
        drive.mbl.setPower(.7);
        drive.mfl.setPower(.7);

        while ((gyro.getAngle() < 90) && opModeIsActive()){
            //hello
        }
        stopMotors();

        //TurnAbsolute(110,.9,1);
    }
}
