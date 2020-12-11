package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;


@TeleOp(group = "AAAAAAAAAA", name = "FirstTeleop")
public class VanillaDrive extends GorillabotsCentral {

    @Override
    public void runOpMode() {

        waitForStart();

        boolean DriveSlow = false;
        double x = 0;
        double y = 0;
        double r = 0;
        int distance;

        while (opModeIsActive()) {

            // SET DRIVING STUFF ↓

            x = gamepad1.left_stick_x;
            y = -gamepad1.left_stick_y;
            r = gamepad1.right_stick_x;


            //if statement watches↓↓

            if (gamepad1.b && gamepad1.dpad_left || gamepad1.b && gamepad1.dpad_left){
                distance = 0;
            }


            if(gamepad1.dpad_left){
                AlignLeft();
            }

            if(gamepad1.dpad_right){
                AlignRight();
            }

            if(gamepad1.b){
                DriveSlow = !DriveSlow;
            }

            if (DriveSlow) {
                x = x * 0.25;
                y = y * 0.25;
                r = r * 0.25;

                telemetry.addData("IsDriveSlow", DriveSlow);
                telemetry.update();
            }

                    drive.go(x, y, r); // drive speed max



            telemetry.update();
        }
        stopMotors();
    }
}
