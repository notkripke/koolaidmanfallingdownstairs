package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.teamcode.components.Alignment;
import org.firstinspires.ftc.teamcode.components.AutoDrive;
import org.firstinspires.ftc.teamcode.components.Capstone;
import org.firstinspires.ftc.teamcode.components.CustomVision;
import org.firstinspires.ftc.teamcode.components.Grabber;
import org.firstinspires.ftc.teamcode.components.Hooks;
import org.firstinspires.ftc.teamcode.components.MecanumDrive;
import org.firstinspires.ftc.teamcode.components.Parker;
import org.firstinspires.ftc.teamcode.components.RevGyro;
import org.firstinspires.ftc.teamcode.components.Sensors;
import org.firstinspires.ftc.teamcode.components.VuforiaKeyManager;

import java.util.List;
import java.util.Locale;

import static java.lang.Math.abs;
import static org.firstinspires.ftc.teamcode.components.Parker.PARKER_OUT;

public abstract class GorillabotsCentral extends LinearOpMode {

    public AutoDrive ADrive;
    public Alignment alignment;
    public Sensors sensors;
    public MecanumDrive drive;
    public RevGyro gyro;
    public ElapsedTime timer;
    public CustomVision vision;

    /*
    HUB # 1 (says hub 2 on the phone but 1 in real life)

    Motors:
    0: mfr
    1: mbr

    Servos:
    0: rotate
    1: rollerF
    2: capstone
    3: hookR
    4: rollerB

    I2C:
    0: imu1
    1: rangeF
    2: rangeB
    3: rangeR

    HUB # 2 (says hub 1 on the phone but 2 in real life)

    Motors:
    0: parker
    1: lift
    2: mfl
    3: mbl

    Servos:
    4: hookL

    I2C:
    0: imu
    1: rangeL

    Digital Devices:
    3: liftBot
     */

    public void initializeComponents() {
        timer = new ElapsedTime();

        drive = new MecanumDrive(hardwareMap, telemetry);

        telemetry.addData("done:", "init");
        telemetry.update();
    }

    public void initializeComponentsAutonomous() {

        ADrive = new AutoDrive(hardwareMap, telemetry);

        drive = new MecanumDrive(hardwareMap, telemetry);

        telemetry.addData("done:", "init");
        telemetry.update();
    }

    public static final int degreeCorrection = 180;
    //160
    public static final double COUNTS_PER_MOTOR_REV = 384;     //12.5:1
    public static final double DRIVE_GEAR_REDUCTION = 1.0;     // This is < 1.0 if geared UP
    public static final double WHEEL_DIAMETER_INCHES = 4.0;     // For figuring circumference
    public static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    public void StartIntake(){
        DcMotor IntakeMotor;
        IntakeMotor = hardwareMap.dcMotor.get("IntakeMotor");

        static final double IntakeSpeed = 0.4;
        public static final boolean isIntakeSpinning = true;

        if (gamepad1.a || gamepad2.a){
            IntakeMotor.setPower(-IntakeSpeed);
            telemetry.addData("Intake Reversed");
            telemetry.update();
        }
        if (isIntakeSpinning){
            IntakeMotor.setPower(IntakeSpeed);
            telemetry.addData("Intake on");
            telemetry.update();
        }
    }

    public void StopIntake(){
        DcMotor IntakeMotor;
        IntakeMotor = hardwareMap.dcMotor.get("IntakeMotor");
        IntakeMotor.setPower(0);
        telemetry.addData("Intake off");
        telemetry.update();
    }

    public void AlignLeft(){
        TurnAbsolute(0,.3, .8);
        MoveUntilRangeLG(30,270,.7,0);
        TurnAbsolute(20,.3,.8);
        stopMotors();

        telemetry.addData("Alignment Complete");
        telemetry.update();
    }

    public void AlignRight(){
        TurnAbsolute(0,.3,.8);
        MoveUntilRangeRG(30,90,.7,0);
        TurnAbsolute(20,.3,.8);
        stopMotors();

        telemetry.addData("Alignment Complete");
        telemetry.update();
    }
    public void StartShooter(){
        DcMotor ShooterMotor;
        ShooterMotor = hardwareMap.dcMotor.get("ShooterMotor");

        static final double INCREMENT   = -0.01;     // amount to ramp motor each CYCLE_MS cycle
        static final int    CYCLE_MS    =   50;     // period of each cycle
        static final double MAX_FWD     =  -0.80;     // Maximum FWD power applied to motor

        double ShootSpeed = 0;

        public boolean isShooterSpinning = true;

        while (isShooterSpinning) {
            ShootSpeed += INCREMENT;
            if (ShootSpeed <= MAX_FWD) {
                ShootSpeed = MAX_FWD;
            }
        }
        telemetry.addData("Shooter Speed", "%5.2f", power);

            if(ShootSpeed = 1){
                telemetry.addData("Ready to Fire");
            }

        telemetry.update();

        ShooterMotor.setPower(ShootSpeed);
        sleep(CYCLE_MS);

    }

    public void FireRing(){
        Servo Feeder;
        Feeder = hardwareMap.crservo.get("Feeder");
        Feeder.setPosition(1);
        sleep(300);
        Feeder.setPosition(0);

    }

    public void MoveUntilEncoder(double distance, double degree, double power) {

        drive.mfr.setDirection(DcMotor.Direction.REVERSE);
        drive.mfl.setDirection(DcMotor.Direction.FORWARD);
        drive.mbr.setDirection(DcMotor.Direction.REVERSE);
        drive.mbl.setDirection(DcMotor.Direction.FORWARD);

        double degreeRad = Math.toRadians(degree - degreeCorrection);
        double cs = Math.cos(degreeRad);
        double sn = Math.sin(degreeRad);

        setDriveEncoderOn(true);

        int rightFrontStartPos = drive.mfr.getCurrentPosition();
        int rightRearStartPos = drive.mbr.getCurrentPosition();
        int leftFrontStartPos = drive.mfl.getCurrentPosition();
        int leftRearStartPos = drive.mbl.getCurrentPosition();

        int target = (int) (distance * COUNTS_PER_INCH);

        int rightFrontEndPos = rightFrontStartPos + (int) (target * (-sn + cs));
        int leftFrontEndPos = leftFrontStartPos + (int) (target * (sn + cs));
        int rightRearEndPos = rightRearStartPos + (int) (target * (sn + cs));
        int leftRearEndPos = leftRearStartPos + (int) (target * (-sn + cs));

        double pwr = power;

        double rightFrontPower = pwr * (-sn + cs);
        double leftFrontPower = pwr * (sn + cs);
        double rightRearPower = pwr * (sn + cs);
        double leftRearPower = pwr * (-sn + cs);

        drive.mfr.setPower(rightFrontPower);
        drive.mfl.setPower(leftFrontPower);
        drive.mbr.setPower(rightRearPower);
        drive.mbl.setPower(leftRearPower);

        drive.mfr.setTargetPosition(rightFrontEndPos);
        drive.mfl.setTargetPosition(leftFrontEndPos);
        drive.mbr.setTargetPosition(rightRearEndPos);
        drive.mbl.setTargetPosition(leftRearEndPos);

        while (drive.mfl.isBusy() && opModeIsActive()) {
        }
        /*|| mfl.isBusy() || mbr.isBusy() || mbl.isBusy())*/
        stopMotors();
    }

    public void MoveUntilEncoder2(double distance, double degree, double power) {

        drive.mfr.setDirection(DcMotor.Direction.REVERSE);
        drive.mfl.setDirection(DcMotor.Direction.FORWARD);
        drive.mbr.setDirection(DcMotor.Direction.REVERSE);
        drive.mbl.setDirection(DcMotor.Direction.FORWARD);

        double degreeRad = Math.toRadians(degree - degreeCorrection);
        double cs = Math.cos(degreeRad);
        double sn = Math.sin(degreeRad);

        setDriveEncoderOn(true);

        int rightFrontStartPos = drive.mfr.getCurrentPosition();
        int rightRearStartPos = drive.mbr.getCurrentPosition();
        int leftFrontStartPos = drive.mfl.getCurrentPosition();
        int leftRearStartPos = drive.mbl.getCurrentPosition();

        int target = (int) (distance * COUNTS_PER_INCH);

        int rightFrontEndPos = rightFrontStartPos + (int) (target * (-sn + cs));
        int leftFrontEndPos = leftFrontStartPos + (int) (target * (sn + cs));
        int rightRearEndPos = rightRearStartPos + (int) (target * (sn + cs));
        int leftRearEndPos = leftRearStartPos + (int) (target * (-sn + cs));

        double pwr = power;

        double rightFrontPower = pwr * (-sn + cs);
        double leftFrontPower = pwr * (sn + cs);
        double rightRearPower = pwr * (sn + cs);
        double leftRearPower = pwr * (-sn + cs);

        drive.mfr.setPower(rightFrontPower);
        drive.mfl.setPower(leftFrontPower);
        drive.mbr.setPower(rightRearPower);
        drive.mbl.setPower(leftRearPower);

        drive.mfr.setTargetPosition(rightFrontEndPos);
        drive.mfl.setTargetPosition(leftFrontEndPos);
        drive.mbr.setTargetPosition(rightRearEndPos);
        drive.mbl.setTargetPosition(leftRearEndPos);

        while ((drive.mfr.isBusy() || drive.mfl.isBusy()) && opModeIsActive()) {
        }
        /*|| mfl.isBusy() || mbr.isBusy() || mbl.isBusy())*/
        stopMotors();
    }

    public void MoveUntilRangeF(double distance, double direction, double power) {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        MoveTo(direction, power);
        while ((sensors.getDistanceF() > distance) && opModeIsActive()) {
            MoveTo(direction, power);
            telemetry.addData("d", sensors.getDistanceF());
            telemetry.update();
        }
        stopMotors();
    }

    public void MoveUntilRangeB(double distance, double direction, double power) {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        MoveTo(direction, power);
        while ((sensors.getDistanceB() > distance) && opModeIsActive()) {
            MoveTo(direction, power);
            telemetry.addData("d", sensors.getDistanceB());
            telemetry.update();
        }
        stopMotors();
    }
    public void MoveUntilRangeFwithG(double distance, double direction, double power,double gyroT) {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        MoveTo(direction, power);
        while ((sensors.getDistanceF() > distance) && opModeIsActive()) {
            MoveTowR(direction, power, (gyro.getAngle() - gyroT) / 50);
            telemetry.addData("d", sensors.getDistanceB());
            telemetry.update();
        }
        stopMotors();
    }

    public void MoveUntilRangeRG(double distance, double direction, double power, double gyroT) {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        MoveTo(direction, power);
        while (abs(sensors.getDistanceR() - distance) > .2 && opModeIsActive()) {
            MoveTowR(direction, power, (gyro.getAngle() - gyroT) / 50);
            telemetry.addData("d", sensors.getDistanceR());
            telemetry.update();
        }
        stopMotors();
    }
    public void MoveUntilRangeLG(double distance, double direction, double power, double gyroT) {
        setDriveEncoderOn(false);
        setMotorsBackwards();
        MoveTo(direction, power);
        while ((sensors.getDistanceL() > distance) && opModeIsActive())  {
            MoveTowR(direction, power, (gyro.getAngle() - gyroT) / 50);
            telemetry.addData("d", sensors.getDistanceL());
            telemetry.update();
        }
        stopMotors();
    }

    public void MoveUntilEncoderGYRO(double distance, double direction, double power, double gyroT) {
        setMotorsBackwards();
        setDriveEncoderOn(false);
        int initPos = drive.mfr.getCurrentPosition();
        MoveTo(direction, power);
        distance = distance * 1000 / 34;
        while ((abs(drive.mfr.getCurrentPosition() - initPos) < abs(distance)) && opModeIsActive()) {
            telemetry.addData("getCurPos", drive.mfr.getCurrentPosition());
            telemetry.addData("s", drive.mfr.getCurrentPosition() - initPos);
            telemetry.update();
            MoveTowR(direction, power, (gyro.getAngle() - gyroT) / 50);
        }
        stopMotors();
    }

    public void MoveUntilEncoderGYROfl(double distance, double direction, double power, double gyroT) {
        setMotorsBackwards();
        setDriveEncoderOn(false);
        int initPos = drive.mfl.getCurrentPosition();
        MoveTo(direction, power);
        distance = distance * 1000 / 34;
        while ((abs(drive.mfl.getCurrentPosition() - initPos) < abs(distance)) && opModeIsActive()) {
            telemetry.addData("getCurPos", drive.mfl.getCurrentPosition());
            telemetry.addData("s", drive.mfl.getCurrentPosition() - initPos);
            telemetry.update();
            MoveTowR(direction, power, (gyro.getAngle() - gyroT) / 50);
        }
        stopMotors();
    }
    public void MoveUntilEncoderGYRORangeR(double distance, double direction, double power, double gyroT, double rangeT) {
        setMotorsBackwards();
        setDriveEncoderOn(false);
        int initPos = drive.mfr.getCurrentPosition();
        double correctionDirection = 0;
        MoveTo(direction, power);
        distance = distance * 1000 / 34;
        while ((abs(drive.mfr.getCurrentPosition() - initPos) < abs(distance)) && opModeIsActive()) {
            telemetry.addData("getCurPos", drive.mfr.getCurrentPosition());
            telemetry.addData("s", drive.mfr.getCurrentPosition() - initPos);
            telemetry.addData("range",correctionDirection);
            telemetry.update();
            correctionDirection = (sensors.rangeR.getDistance(DistanceUnit.INCH) - rangeT) * 5;
            MoveTowR(direction + correctionDirection, power, (gyro.getAngle() - gyroT) / 50);
        }
        stopMotors();
    }

    public void MoveUntilTime(long timeMilli, double direction, double power) {
        setMotorsBackwards();
        setDriveEncoderOn(false);
        MoveTo(direction, power);
        sleep(timeMilli);
        stopMotors();
    }
    public void MoveUntilTouch (double direction, double power, double gyroT){
        setDriveEncoderOn(false);
        setMotorsBackwards();
        while ((sensors.alignT.getState()) && opModeIsActive()) {
            MoveTowR(direction, power,(gyro.getAngle() - gyroT) / 50);
        }
        stopMotors();
    }

    public void MoveUntilTouchRangeF (double direction, double power, double gyroT){
        setDriveEncoderOn(false);
        setMotorsBackwards();
        while ((sensors.alignT.getState() && sensors.getDistanceF() > 7.75) && opModeIsActive()) { //9
            MoveTowR(direction, power,(gyro.getAngle() - gyroT) / 50);
        }
        stopMotors();
    }
    public void MoveTo(double degree, double power) {
        double degreeRad = Math.toRadians(degree - degreeCorrection); // Convert to radians
        double cs = Math.cos(degreeRad);
        double sn = Math.sin(degreeRad);

        double fr = power * (-sn + cs);
        double fl = power * (sn + cs);
        double br = power * (sn + cs);
        double bl = power * (-sn + cs);

        drive.mfl.setPower(fl);
        drive.mfr.setPower(fr);
        drive.mbl.setPower(bl);
        drive.mbr.setPower(br);
    }

    public void MoveTowR(double degree, double power, double r) {
        double degreeRad = Math.toRadians(degree - degreeCorrection); // Convert to radians
        double cs = Math.cos(degreeRad);
        double sn = Math.sin(degreeRad);

        double fr = Range.clip((power * (-sn + cs)) + r, -1, 1);
        double fl = Range.clip((power * (sn + cs)) - r, -1, 1);
        double br = Range.clip((power * (sn + cs)) + r, -1, 1);
        double bl = Range.clip((power * (-sn + cs)) - r, -1, 1);


        drive.mfl.setPower(fl);
        drive.mfr.setPower(fr);
        drive.mbl.setPower(bl);
        drive.mbr.setPower(br);
    }

    public void stopMotors() {
        drive.mfr.setPower(0);
        drive.mfl.setPower(0);
        drive.mbr.setPower(0);
        drive.mbl.setPower(0);
        ShooterMotor.setPower(0);
        IntakeMotor.setPower(0);
    }

    public void setMotorsBackwards() {
        drive.mfr.setDirection(DcMotor.Direction.REVERSE);
        drive.mfl.setDirection(DcMotor.Direction.FORWARD);
        drive.mbr.setDirection(DcMotor.Direction.REVERSE);
        drive.mbl.setDirection(DcMotor.Direction.FORWARD);
    }

    public void setDriveEncoderOn(boolean on) {
        if (on) {
            drive.mfr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drive.mfr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drive.mfr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            drive.mbr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drive.mbr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drive.mbr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            drive.mfl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drive.mfl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drive.mfl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            drive.mbl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            drive.mbl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drive.mbl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else {
            drive.mfr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            drive.mbr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            drive.mfl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            drive.mbl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
    }

    public void TurnAbsolute(double TargetDegree, double min, double max) {
        // clock is negative; anti-clock positive degree
        // rotate range is (-90,90)

        setMotorsBackwards();

        if (TargetDegree > 180) {
            TargetDegree = 180;
        }
        if (TargetDegree < -180) {
            TargetDegree = -180;
        }

        double MaxPower = max;
        double minPower = min;

        double correctionDegree = 5;
        double beginDegree;
        double currentDegree;

        double target;
        double angleDiff;
        double maxTime = 6; //seconds
        ElapsedTime runtime = new ElapsedTime();

        setDriveEncoderOn(false);

        beginDegree = gyro.getAngle();

        runtime.reset();
        runtime.startTime();

        angleDiff = TargetDegree - beginDegree;
        while (abs(angleDiff) > 1 && runtime.seconds() < maxTime && opModeIsActive()) {
            double leftPower;
            double rightPower;
            currentDegree = gyro.getAngle();
            angleDiff = TargetDegree - currentDegree;
            if (angleDiff > 180) {
                angleDiff = angleDiff - 360;
            }
            if (angleDiff < -180) {
                angleDiff = angleDiff + 360;
            }

            if (angleDiff < 0) {
                angleDiff = angleDiff + correctionDegree;
            }
            if (angleDiff > 0) {
                angleDiff = angleDiff - correctionDegree;
            }

            double drivea;
            drivea = (angleDiff) / 100.0;

            if (abs(drivea) > MaxPower) {
                drivea = MaxPower * abs(drivea) / drivea;
            }
            if (abs(drivea) < minPower) {
                if (drivea > 0) {
                    drivea = minPower;
                } else if (drivea < 0) {
                    drivea = -minPower;
                } else {
                    drivea = 0;
                }
            }

            leftPower = Range.clip(-drivea, -1.0, 1.0);
            rightPower = Range.clip(drivea, -1.0, 1.0);

            drive.mfl.setPower(rightPower);
            drive.mbl.setPower(rightPower);
            drive.mfr.setPower(leftPower);
            drive.mbr.setPower(leftPower);

            telemetry.addData("Left Power", leftPower);
            telemetry.addData("right Power", rightPower);
            telemetry.addData("beginDegree", beginDegree);
            telemetry.addData("CurrentDegree", currentDegree);
            telemetry.addData("angleDiff", angleDiff);
            telemetry.update();
        }
        stopMotors();

        telemetry.addData("Current ZDegree", gyro.getAngle());
        telemetry.update();
    }

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VuforiaKeyManager.getVuforiaKey(hardwareMap, telemetry, "vuforiakey.txt");
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the TensorFlow Object Detection engine.
    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.8;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    }



}