package commun;



import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.boundingRect;


public class detector {
    public detector(){}

    public String detect(MatOfPoint img) {

        String shape = "Unindentified";

        MatOfPoint2f img2f = new MatOfPoint2f();
        MatOfPoint approxImg = new MatOfPoint();
        MatOfPoint2f approxImg2f = new MatOfPoint2f();

        img.convertTo(img2f, CvType.CV_32FC2);

        Imgproc.approxPolyDP(img2f, approxImg2f, 4, true);

        approxImg2f.convertTo(approxImg, CvType.CV_32S);
        System.out.println(approxImg.size().height);
        switch ((int) approxImg.size().height) {

            case 0:
                shape = "Not a Shape";
                break;
            case 1:
                shape = "Point";
                break;
            case 2:
                shape = "Line";
                break;
            case 3:
                shape = "Triangle";
                break;
            case 4:
                Rect rect = boundingRect(approxImg);
                float ar =(float) rect.width / (float)rect.height;
                if (ar >= 0.95 && ar <= 1.05) {
                    System.out.println(ar);
                    shape = "Square";
                } else {
                    shape = "Rectangle";
                }
                break;
            case 5:
                shape = "Pentagon";
                break;
            case 6:
                shape = "Hexagone";
                break;
            default:
                shape = "Circle";
                break;


        }
        return shape;
    }

}
