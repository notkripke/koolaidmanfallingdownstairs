package org.firstinspires.ftc.teamcode.Components;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

public class CustomVision
{
    private Telemetry tele;

    VuforiaLocalizer vuforia;
    VuforiaLocalizer.Parameters params;

    final boolean USE_WEBCAM = true;

    public CustomVision(HardwareMap hardwareMap, Telemetry telemetry)
    {
        tele = telemetry;

        params = new VuforiaLocalizer.Parameters();

        params.vuforiaLicenseKey = VuforiaKeyManager.getVuforiaKey(hardwareMap, tele, "vuforiakey.txt");

        if(USE_WEBCAM)
        {
            params.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        }
        else
        {
            int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
            params.cameraMonitorViewIdParent = cameraMonitorViewId;
        }


        vuforia = ClassFactory.getInstance().createVuforia(params);

        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);
        vuforia.setFrameQueueCapacity(1);
    }

    public Bitmap getImage()
    {
        VuforiaLocalizer.CloseableFrame frame = null;

        try
        {
            frame = vuforia.getFrameQueue().take();
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
            return null;
        }

        Bitmap bmp = vuforia.convertFrameToBitmap(frame);

        frame.close();

        return bmp;
    }

    final int RL_LEFT = 0;
    final int RL_RIGHT = 150;
    final int RL_TOP = 100;
    final int RL_BOTTOM = 175;

    final int RR_LEFT = 185;
    final int RR_RIGHT = 370;
    final int RR_TOP = 130;
    final int RR_BOTTOM = 190;

    final int RL_XINC = 5;
    final int RL_YINC = 5;
    final int RL_XYINC = RL_XINC * RL_YINC;

    final int RL_WIDTH = RL_RIGHT - RL_LEFT;
    final int RL_HEIGHT = RL_BOTTOM - RL_TOP;
    final int RL_SIZE = RL_WIDTH * RL_HEIGHT;
    final int RL_SCANSIZE = RL_SIZE / RL_XYINC;

    final int RR_XINC = 5;
    final int RR_YINC = 5;
    final int RR_XYINC = RR_XINC * RR_YINC;

    final int RR_WIDTH = RR_RIGHT - RR_LEFT;
    final int RR_HEIGHT = RR_BOTTOM - RR_TOP;
    final int RR_SIZE = RR_WIDTH * RR_HEIGHT;
    final int RR_SCANSIZE = RR_SIZE / RR_XYINC;

    public int getPositionRed()
    {
        Bitmap bmp = getImage();

        long suml = 0;
        long sumr = 0;

        for(int x = RL_LEFT; x < RL_RIGHT; x += RL_XINC)
        {
            for (int y = RL_TOP; y < RL_BOTTOM; y += RL_YINC)
            {
                int color = bmp.getPixel(x, y);

                suml += Color.red(color) + Color.green(color) + Color.blue(color);
            }
        }

        for(int x = RR_LEFT; x < RR_RIGHT; x += RR_XINC)
        {
            for (int y = RR_TOP; y < RR_BOTTOM; y += RR_YINC)
            {
                int color = bmp.getPixel(x, y);

                sumr += Color.red(color) + Color.green(color) + Color.blue(color);
            }
        }

        double avgl = suml / (double) RL_SCANSIZE;
        double avgr = sumr / (double) RR_SCANSIZE;

        double max = Math.max(avgl, avgr);
        avgl /= max;
        avgr /= max;

        if(avgl < 0.65) //Black = Left Visible = Middle
        {
            tele.addData("Position", "Middle");
            return 2;
        }
        else if(avgr < 0.65) //Black = Right Visible = Right
        {
            tele.addData("Position", "Right");
            return 3;
        }
        else //Black = Neither = Left
        {
            tele.addData("Position", "Left");
            return 1;
        }
    }

    final int BL_LEFT = 175; //TODO: Update numbers
    final int BL_RIGHT = 340;
    final int BL_TOP = 125;
    final int BL_BOTTOM = 195;

    final int BR_LEFT = 375;
    final int BR_RIGHT = 550;
    final int BR_TOP = 145;
    final int BR_BOTTOM = 210;

    final int BL_XINC = 5;
    final int BL_YINC = 5;
    final int BL_XYINC = BL_XINC * BL_YINC;

    final int BL_WIDTH = BL_RIGHT - BL_LEFT;
    final int BL_HEIGHT = BL_BOTTOM - BL_TOP;
    final int BL_SIZE = BL_WIDTH * BL_HEIGHT;
    final int BL_SCANSIZE = BL_SIZE / BL_XYINC;

    final int BR_XINC = 5;
    final int BR_YINC = 5;
    final int BR_XYINC = BR_XINC * BR_YINC;

    final int BR_WIDTH = BR_RIGHT - BR_LEFT;
    final int BR_HEIGHT = BR_BOTTOM - BR_TOP;
    final int BR_SIZE = BR_WIDTH * BR_HEIGHT;
    final int BR_SCANSIZE = BR_SIZE / BR_XYINC;
    
    public int getPositionBlue()
    {
        Bitmap bmp = getImage();

        long suml = 0;
        long sumr = 0;

        for(int x = BL_LEFT; x < BL_RIGHT; x += BL_XINC)
        {
            for (int y = BL_TOP; y < BL_BOTTOM; y += BL_YINC)
            {
                int color = bmp.getPixel(x, y);

                suml += Color.red(color) + Color.green(color) + Color.blue(color);
            }
        }

        for(int x = BR_LEFT; x < BR_RIGHT; x += BR_XINC)
        {
            for (int y = BR_TOP; y < BR_BOTTOM; y += BR_YINC)
            {
                int color = bmp.getPixel(x, y);

                sumr += Color.red(color) + Color.green(color) + Color.blue(color);
            }
        }

        double avgl = suml / (double) BL_SCANSIZE;
        double avgr = sumr / (double) BR_SCANSIZE;

        double max = Math.max(avgl, avgr);
        avgl /= max;
        avgr /= max;

        if(avgl < 0.65) //Black = Left Visible = Left
        {
            tele.addData("Position", "Left");
            return 1;
        }
        else if(avgr < 0.65) //Black = Right Visible = Middle
        {
            tele.addData("Position", "Middle");
            return 2;
        }
        else //Black = Neither = Right
        {
            tele.addData("Position", "Right");
            return 3;
        }
    }
}
