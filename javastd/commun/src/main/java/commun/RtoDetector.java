package commun;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;

import static org.opencv.imgproc.Imgproc.boundingRect;

public class RtoDetector {

    public RtoDetector(){ }

    public String detectRto(Mat image) {


        String shape = "not a shape";


        MatOfPoint approxImg = new MatOfPoint();
        approxImg = shapedetector.convertUtil(image);



        double height = approxImg.size().height;

        if (height == 0 | height == 1 | height == 2 | height == 8) {
            shape = "Not a Shape";
        }
        else if (height == 3) {
            shape = "Triangle";
        }
        else if (height == 4) {
            Rect rect = boundingRect(approxImg);
            float ar = (float) rect.width / (float) rect.height;
            if (ar >= 0.90 && ar <= 1.10) {
                shape = "Carre";
            } else {
                shape = "Carre";
            }
        }
        else {
            shape = "Circle";
        }

        return shape;
    }




}
