package commun;

import android.media.Image;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;


import static org.opencv.imgproc.Imgproc.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.boundingRect;

public class ServeurHorsConnexion {

    public ServeurHorsConnexion() { }

    public String detectShape(Mat img) {

        String shape;

        MatOfPoint2f img2f = new MatOfPoint2f();
        MatOfPoint approxImg = new MatOfPoint();
        MatOfPoint2f approxImg2f = new MatOfPoint2f();

        img.convertTo(img2f, CvType.CV_32FC2);

        Imgproc.approxPolyDP(img2f, approxImg2f, 42.42, true);

        approxImg2f.convertTo(approxImg, CvType.CV_32S);

        switch ((int) approxImg.size().height){

            case 0:
                shape = "Not a Shape";
                break;
            case 1:
                shape = "Point";
                break;
            case 2:
                shape = "Segment";
                break;
            case 3:
                shape = "Triangle";
                break;
            case 4:
                Rect rect = boundingRect(approxImg);
                float ar = (float) rect.width / (float) rect.height;
                if(ar >= 0.90 && ar <= 1.10){
                    shape = "Carre";
                }
                else {
                    shape = "Rectangle";
                }
                break;
            case 5:
                shape = "Pentagon";
                break;
            case 6:
                shape = "Hexagon";
                break;
            case 7:
                shape = "Heptagon";
                break;
            case 8:
                shape = "Octagon";
                break;
            default:
                shape = "Circle";
                break;
        }

        return shape;
    }



    public ArrayList<String> detectShapes(String file) {


        //String file = "C:/Users/Kyriakos Petrou/Desktop/shape-detection/shape-detection/test.png";
        Mat matrix = Imgcodecs.imread(file);

        //Mat matrix = matrice;

        Mat resizematrix = new Mat();
        Size sz = new Size(matrix.width(), matrix.height());
        Imgproc.resize(matrix, resizematrix, sz);

        Mat resizeColor = new Mat();
        Imgproc.cvtColor(resizematrix,resizeColor,Imgproc.COLOR_BGR2GRAY);

        Mat resizeGauss = new Mat();
        Size sz2 = new Size(5,5);
        Imgproc.GaussianBlur(resizeColor,resizeGauss,sz2,0);

        Mat resizeThresh = new Mat();
        Imgproc.threshold(resizeGauss,resizeThresh,60,255,Imgproc.RETR_EXTERNAL);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(resizeThresh,contours,new Mat(),Imgproc.RETR_EXTERNAL,Imgproc.CHAIN_APPROX_SIMPLE);
        ServeurHorsConnexion sd = new ServeurHorsConnexion();

        ArrayList<String> res = new ArrayList<>();

        for(MatOfPoint c : contours) {
            Moments M = Imgproc.moments(c);
            int cX = (int) (M.m10 / M.m00 * 3.857142857142857);
            int cY = (int) (M.m01 / M.m00 * 3.857142857142857);
            Point sz3 = new Point(cX,cY);
            String shape = sd.detectShape(c);

            res.add(shape);

            Scalar scalar = new Scalar(0,255,0);
            Scalar scalar2 = new Scalar(255,255,255);

            Imgproc.drawContours(matrix,contours,-1,scalar,2);
            Imgproc.putText(matrix,shape,sz3,FONT_HERSHEY_SIMPLEX,0.5,scalar2,2);



        }


        return res;
    }


}
