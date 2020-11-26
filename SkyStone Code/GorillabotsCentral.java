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
    public Grabber grabber;
    public Hooks hooks;
    public Sensors sensors;
    public MecanumDrive drive;
    public Parker parker;
    public RevGyro gyro;
    public Capstone capstone;
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

        //ADrive = new AutoDrive(hardwareMap,telemetry);

        grabber = new Grabber(hardwareMap, telemetry);

        //grabber.rotate(Grabber.ROTATE_INIT);
        //grabber.intake(Grabber.INTAKE_HOLD);

        hooks = new Hooks(hardwareMap, telemetry);

        //hooks.setDown(false);

        alignment = new Alignment(hardwareMap, telemetry);

        alignment.alignment(Alignment.ALGIN_INIT);

        sensors = new Sensors(hardwareMap, telemetry);

        drive = new MecanumDrive(hardwareMap, telemetry);

        parker = new Parker(hardwareMap, telemetry);

        capstone = new Capstone(hardwareMap, telemetry);

        capstone.capstone(Capstone.CAPSTONE_INIT);

        //gyro = new RevGyro(hardwareMap,telemetry);

        telemetry.addData("done:", "init");
        telemetry.update();
    }

    public void initializeComponentsAutonomous() {
        timer = new ElapsedTime();

        ADrive = new AutoDrive(hardwareMap, telemetry);

        grabber = new Grabber(hardwareMap, telemetry);

        grabber.rotate(Grabber.ROTATE_INIT);
        grabber.intake(Grabber.INTAKE_HOLD);

        hooks = new Hooks(hardwareMap, telemetry);

        hooks.setDown(false);

        alignment = new Alignment(hardwareMap, telemetry);

        alignment.alignment(Alignment.ALGIN_INIT);

        sensors = new Sensors(hardwareMap, telemetry);

        drive = new MecanumDrive(hardwareMap, telemetry);

        parker = new Parker(hardwareMap, telemetry);

        capstone = new Capstone(hardwareMap, telemetry);

        capstone.capstone(Capstone.CAPSTONE_INIT);

        capstone.intCapstone(Capstone.INTCAPSTONE_SAFE);

        gyro = new RevGyro(hardwareMap, telemetry);
        vision = new CustomVision(hardwareMap, telemetry);

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

    public void setLiftPos(double pos) {
        grabber.isEncoderModeLift(true);

        int start = grabber.lift.getCurrentPosition();
        int end = start + (int) pos;

        grabber.lift(Grabber.LIFT_GOINUP);

        grabber.lift.setTargetPosition(end);
        timer.reset();
        timer.startTime();
        while (grabber.lift.isBusy() && opModeIsActive() && timer.seconds() < 3) {
            telemetry.addData("pos", grabber.lift.getCurrentPosition());
            telemetry.update();
        }
        grabber.lift(0);
        grabber.isEncoderModeLift(false);
    }

    public void setLiftDown() {
        grabber.isEncoderModeLift(false);

        grabber.lift(Grabber.LIFT_GOINDOWN);

        timer.reset();
        timer.startTime();

        while (opModeIsActive() && sensors.liftBot.getState() && timer.seconds() < 3) {

        }
        grabber.lift(0);

    }

    private static final String TFOD_MODEL_ASSET = "Skystone.tflite";
    private static final String LABEL_FIRST_ELEMENT = "Stone";
    private static final String LABEL_SECOND_ELEMENT = "Skystone";

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

    public int getSkystonePosBlue() {
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        if (tfod != null) {
            tfod.activate();
        }

        int position = 2;
        if (!isStarted() && !isStopRequested()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (!isStarted() && !isStopRequested()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        String Lable0 = "";
                        String Lable1 = "";
                        String Lable2 = "";
                        double left0 = 0;
                        double left1 = 0;
                        double left2 = 0;

                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format(Locale.ENGLISH, "label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format(Locale.ENGLISH, "  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format(Locale.ENGLISH, "  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            if (i == 0) {
                                Lable0 = recognition.getLabel();
                                left0 = recognition.getLeft();
                            }
                            if (i == 1) {
                                Lable1 = recognition.getLabel();
                                left1 = recognition.getLeft();
                            }
                            if (i == 2) {
                                Lable2 = recognition.getLabel();
                                left2 = recognition.getLeft();
                            }
                            i += 1;
                        }
                        if (updatedRecognitions.size() == 2) {
                            if (Lable0 == "Skystone" && (left0 < left1) || (Lable1 == "Skystone" && (left1 < left0))) {
                                position = 1;
                            } else if (Lable0 == "Stone" && Lable1 == "Stone") {
                                position = 3;
                            } else {
                                position = 2;
                            }
                        } else if (updatedRecognitions.size() == 1) {
                            if (Lable0 == "Skystone") {
                                position = 1; //GUESS
                            } else {
                                position = 3; //INFERENCE
                            }
                        }
                        telemetry.update();
                    }


                }

                telemetry.addData("rangeF", sensors.getDistanceF());
                telemetry.addData("rangeB", sensors.getDistanceB());
                telemetry.addData("rangeR", sensors.getDistanceR());
                telemetry.addData("rangeL", sensors.getDistanceL());
                telemetry.addData("position", position);
                telemetry.update();
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
        return position;
    }

    public int getSkystonePosRed() {
        initVuforia();
        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }
        if (tfod != null) {
            tfod.activate();
        }

        int position = 2;
        if (!isStarted() && !isStopRequested()) {
            /** Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            while (!isStarted() && !isStopRequested()) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        String Lable0 = "";
                        String Lable1 = "";
                        String Lable2 = "";
                        double left0 = 0;
                        double left1 = 0;
                        double left2 = 0;

                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format(Locale.ENGLISH, "label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format(Locale.ENGLISH, "  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format(Locale.ENGLISH, "  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            if (i == 0) {
                                Lable0 = recognition.getLabel();
                                left0 = recognition.getLeft();
                            }
                            if (i == 1) {
                                Lable1 = recognition.getLabel();
                                left1 = recognition.getLeft();
                            }
                            if (i == 2) {
                                Lable2 = recognition.getLabel();
                                left2 = recognition.getLeft();
                            }
                            i += 1;
                        }
                        if (updatedRecognitions.size() == 2) {
                            if (Lable0 == "Skystone" && (left0 < left1) || (Lable1 == "Skystone" && (left1 < left0))) {
                                position = 2;
                            } else if (Lable0 == "Stone" && Lable1 == "Stone") {
                                position = 1;
                            } else {
                                position = 3;
                            }
                        } else if (updatedRecognitions.size() == 1) {
                            if (Lable0 == "Skystone") {
                                position = 2; //GUESS
                            } else {
                                position = 1; //INFERENCE
                            }
                        }
                        telemetry.update();
                    }


                }

                telemetry.addData("rangeF", sensors.getDistanceF());
                telemetry.addData("rangeB", sensors.getDistanceB());
                telemetry.addData("rangeR", sensors.getDistanceR());
                telemetry.addData("rangeL", sensors.getDistanceL());
                telemetry.addData("position", position);
                telemetry.update();
            }
        }
        if (tfod != null) {
            tfod.shutdown();
        }
        return position;
    }

    public void setParkerPos(int pos) {
        parker.setParkerEncoder(true);

        int start = parker.parker.getCurrentPosition();
        int end = start - pos;

        parker.parkerPow(Parker.PARKER_OUT * 2);

        parker.parker.setTargetPosition(end);
        timer.reset();
        timer.startTime();
        while (parker.parker.isBusy() && opModeIsActive()) {
            telemetry.addData("pos", parker.parker.getCurrentPosition());
            telemetry.update();
        }
        parker.parkerPow(0);
        parker.setParkerEncoder(false);
    }

    public int getRed() {
        int pos = 2;
        while (!isStarted() && !isStopRequested()) {
            pos = vision.getPositionRed();
            telemetry.addData("rangeF", sensors.getDistanceF());
            telemetry.addData("rangeB", sensors.getDistanceB());
            telemetry.addData("rangeR", sensors.getDistanceR());
            telemetry.addData("rangeL", sensors.getDistanceL());
            telemetry.addData("position", pos);
            telemetry.update();
        }
        return pos;
    }

    public int getBlue() {
        int pos = 2;
        while (!isStarted() && !isStopRequested()) {
            pos = vision.getPositionBlue();
            telemetry.addData("rangeF", sensors.getDistanceF());
            telemetry.addData("rangeB", sensors.getDistanceB());
            telemetry.addData("rangeR", sensors.getDistanceR());
            telemetry.addData("rangeL", sensors.getDistanceL());
            telemetry.addData("position", pos);
            telemetry.update();
        }
        return pos;
    }

}