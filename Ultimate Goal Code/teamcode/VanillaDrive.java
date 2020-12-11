package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.GorillabotsCentral;


@TeleOp(group = "AAAAAAAAAA", name = "FirstTeleop")
public class VanillaDrive extends GorillabotsCentral {

    @Override
    public void runOpMode() {

        waitForStart();
        
        double x = 0;
        double y = 0;
        double r = 0;
        int distance;
        int slow = 0;

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
                slow += 1;
            }

            switch (slow) {

                case 0:
                    drive.go(x, y, r); // drive speed max

                    telemetry.addData("Driving slow?", "No");
                    telemetry.update();
                    break;
                case 1:
                    drive.go(x * 0.25, y * 0.25, r * 0.25);

                    telemetry.addData("Driving slow?", "Yes");
                    telemetry.update();
                    break;
                case 2:
                    slow = 0;
                    break;
            }



            telemetry.update();
        }
        stopMotors();
    }
}
