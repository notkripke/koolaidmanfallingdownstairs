package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class AutoDrive
{
    Telemetry tele;

    MecanumDrive drive;
    RevGyro gyro;

    public static final double COUNTS_PER_MOTOR_REV = 160;     //12.5:1
    public static final double DRIVE_GEAR_REDUCTION = 2.0;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public AutoDrive(HardwareMap hardwareMap, Telemetry telemetry)
    {
        tele = telemetry;
        drive = new MecanumDrive(hardwareMap, telemetry);
        gyro = new RevGyro(hardwareMap, telemetry);
    }

    double gyroOffset = 0;

    public void driveCartesian(double x, double y, double dist)
    {
        drive.resetDrivenDistance();
        dist = dist * COUNTS_PER_INCH;
        while(drive.getDrivenDistance() < dist)
        {
            drive.go(x, y, gyro.getAngle() / 100);
            tele.addData("Gyro Angle", gyro.getAngle());
            tele.update();
        }
        drive.go(0,0,0);
    }
}
