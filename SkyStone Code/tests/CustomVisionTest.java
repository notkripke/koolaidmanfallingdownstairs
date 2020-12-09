package org.firstinspires.ftc.teamcode.tests;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.provider.MediaStore;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.components.CustomVision;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Disabled
@Autonomous(name="CustomVisionTest", group="tests")
public class CustomVisionTest extends LinearOpMode
{
    public void runOpMode()
    {
        final CustomVision vision = new CustomVision(hardwareMap, telemetry);

        waitForStart();

        double fps = 0;

        int i = 0;
        int refi = 0;

        long time = System.currentTimeMillis();
        long reftime = time;

        Bitmap bmp = vision.getImage();
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        boolean lastA = false;
        int savei = 0;
        long savet = 0;

        while(opModeIsActive())
        {
            i++;
            bmp = vision.getImage();

            if(gamepad1.a && !lastA)
            {
                File path = Environment.getExternalStorageDirectory();
                String file = "pics/" + i + ".png";
                File dest = new File(path, file);

                try (FileOutputStream out = new FileOutputStream(dest))
                {
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
                    // PNG is a lossless format, the compression factor (100) is ignored
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                savei = i;
                savet = System.currentTimeMillis();
            }
            lastA = gamepad1.a;

            int posr = vision.getPositionRed();
            int posb = vision.getPositionBlue();

            time = System.currentTimeMillis();

            if(time > reftime + 1000)
            {
                fps = (i - refi) / ((time - reftime) / 1000d);
                refi = i;
                reftime = time;
            }

            telemetry.addData("i", i);
            telemetry.addData("fps", fps);
            telemetry.addData("width", width);
            telemetry.addData("height", height);
            telemetry.addData("Position int red", posr);
            telemetry.addData("Position int blu", posb);

            if(time - savet <= 1000)
            {
                telemetry.addData("Saved Image", savei);
            }

            telemetry.update();
        }
    }
}
