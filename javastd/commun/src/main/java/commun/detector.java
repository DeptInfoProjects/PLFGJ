package commun;

import org.opencv.core.Point;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class detector {


//    private List<String> detect(Mat matrix) {
//        List<String> shapeDessiner = new ArrayList<>();
//        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//        //Imgcodecs imgcodecs;
//        //imgcodecs = new Imgcodecs();
//        //String file = "C:/Users/Kyriakos Petrou/Desktop/shape-detection/shape-detection/test.png";
//        //Mat matrix = imgcodecs.imread(img);
//
//        Mat resizematrix = new Mat();
//        Size sz = new Size(matrix.width(), matrix.height());
//        Imgproc.resize(matrix, resizematrix, sz);
//
//        Mat resizeColor = new Mat();
//        Imgproc.cvtColor(resizematrix, resizeColor, Imgproc.COLOR_BGR2GRAY);
//
//        Mat resizeGauss = new Mat();
//        Size sz2 = new Size(5, 5);
//        Imgproc.GaussianBlur(resizeColor, resizeGauss, sz2, 0);
//        Mat resizeThresh = new Mat();
//
//        Imgproc.threshold(resizeGauss, resizeThresh, 60, 255, Imgproc.RETR_EXTERNAL);
//        List<MatOfPoint> contours = new ArrayList<>();
//        Imgproc.findContours(resizeThresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//        shapedetector sd = new shapedetector();
//        for (MatOfPoint c : contours) {
//            Moments M = Imgproc.moments(c);
//            int cX = (int) (M.m10 / M.m00);
//            int cY = (int) (M.m01 / M.m00);
//            Point sz3 = new Point(cX, cY);
//            String shape = sd.detect(c);
//            Scalar scalar = new Scalar(0, 255, 0);
//            Scalar scalar2 = new Scalar(125, 125, 125);
//            System.out.println(shape);
//            Imgproc.drawContours(matrix, contours, -1, scalar, 2);
//            Imgproc.putText(matrix, shape, sz3, Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, scalar2, 2);
//            Image imageRes = sd.Mat2BufferedImage(matrix);
//            sd.displayImage(imageRes);
//            shapeDessiner.add(shape);
//
//        }
//        return shapeDessiner;
//    }
//
}
