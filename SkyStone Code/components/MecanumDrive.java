package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDrive
{
    Telemetry tele;

    public DcMotor mfl, mfr, mbl, mbr;

    public MecanumDrive(HardwareMap hardwareMap, Telemetry telemetry)
    {
        tele = telemetry;

        mfl = hardwareMap.dcMotor.get("mfl");
        mfr = hardwareMap.dcMotor.get("mfr");
        mbl = hardwareMap.dcMotor.get("mbl");
        mbr = hardwareMap.dcMotor.get("mbr");

        mfr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mfl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mbr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mbl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private double fl, fr, bl, br;
    
    public void go(double x, double y, double r)
    {
        tele.addData("x", x);
        tele.addData("y", y);
        tele.addData("r", r);

        fl = -x - y - r;
        fr = -x + y - r;
        bl = x - y - r;
        br = x + y - r;

        tele.addData("pfl", fl);
        tele.addData("pfr", fr);
        tele.addData("pbl", bl);
        tele.addData("pbr", br);

        mfl.setPower(fl);
        mfr.setPower(fr);
        mbl.setPower(bl);
        mbr.setPower(br);

        tele.addData("ofl", mfl.getCurrentPosition());
        tele.addData("ofr", mfr.getCurrentPosition());
        tele.addData("obl", mbl.getCurrentPosition());
        tele.addData("obr", mbr.getCurrentPosition());
    }

    private int froffset = 0;
    private int floffset = 0;

    public void resetDrivenDistance()
    {
        froffset = mbr.getCurrentPosition() - mfl.getCurrentPosition();
        floffset = mfr.getCurrentPosition() - mbl.getCurrentPosition();
    }

    public double getDrivenDistance()
    {
        double frdist = mbr.getCurrentPosition() - mfl.getCurrentPosition() - froffset;
        double fldist = mfr.getCurrentPosition() - mbl.getCurrentPosition() - floffset;

        tele.addData("frdist", frdist);
        tele.addData("fldist", fldist);

        return Math.sqrt(frdist*frdist + fldist*fldist);
    }
}
