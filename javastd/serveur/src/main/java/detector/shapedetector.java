package detector;

import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.Random;

import static org.opencv.core.Core.FONT_HERSHEY_SIMPLEX;
import static org.opencv.imgproc.Imgproc.boundingRect;

public class shapedetector {

    public shapedetector() {
    }

    public String detectShape(Mat img) {

        String shape = "not a shape";

        MatOfPoint approxImg = new MatOfPoint();
        approxImg = convertUtil(img);


        switch ((int) approxImg.size().height) {

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
                if (ar >= 0.90 && ar <= 1.10) {
                    shape = "Carre";
                } else {
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
            default:
                shape = "Circle";
                break;
        }

        return shape;
    }


    public String detectRto(Mat image) {

        String formeDetecte = "not a shape";

        MatOfPoint approxImg = new MatOfPoint();
        approxImg = convertUtil(image);


        double height = approxImg.size().height;

        if (height == 0 | height == 1 | height == 2 | height == 5) {
            formeDetecte = "Not a Shape";
        } else if (height == 3) {
            formeDetecte = "Triangle";
        } else if (height == 4) {
            Rect rect = boundingRect(approxImg);
            float ar = (float) rect.width / (float) rect.height;
            if (ar >= 0.90 && ar <= 1.10) {
                formeDetecte = "Carre";
            } else {
                formeDetecte = "Carre";
            }
        } else {
            formeDetecte = "Circle";
        }

        return formeDetecte;
    }


    /**
     * Transforme une matrice (opencv.Mat) en image
     *
     * @param m Matrice
     * @return une image
     */


    public static BufferedImage Mat2BufferedImage(Mat m) {
        // Fastest code
        // output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;

        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }


    /**
     * Permet l'affichage de l'image passé en paramètre (uniquement présent pour procéder à des vérifications)
     *
     * @param img2
     */


    public void displayImage(Image img2) {

        ImageIcon icon = new ImageIcon(img2);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null) + 50, img2.getHeight(null) + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public String detectShapes(String exo, String file) {


        Mat matrix = Imgcodecs.imread(file);

        //Mat matrix = matrice;

        Mat resizematrix = new Mat();
        Size sz = new Size(matrix.width(), matrix.height());
        Imgproc.resize(matrix, resizematrix, sz);

        Mat resizeColor = new Mat();
        Imgproc.cvtColor(resizematrix, resizeColor, Imgproc.COLOR_BGR2GRAY);

        Mat resizeGauss = new Mat();
        Size sz2 = new Size(5, 5);
        Imgproc.GaussianBlur(resizeColor, resizeGauss, sz2, 0);

        Mat resizeThresh = new Mat();
        Imgproc.threshold(resizeGauss, resizeThresh, 60, 255, Imgproc.RETR_EXTERNAL);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(resizeThresh, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        shapedetector sd = new shapedetector();


        ArrayList<String> res = new ArrayList<>();

        for (MatOfPoint c : contours) {
            Moments M = Imgproc.moments(c);
            int cX = (int) (M.m10 / M.m00 * 3.857142857142857);
            int cY = (int) (M.m01 / M.m00 * 3.857142857142857);
            Point sz3 = new Point(cX, cY);

            Scalar scalar = new Scalar(0, 255, 0);
            Scalar scalar2 = new Scalar(255, 255, 255);
            Imgproc.drawContours(matrix, contours, -1, scalar, 2);


            if (exo.equals("timeDetector")) {
                String shape = sd.detectShape(c);
                res.add(shape);
                Imgproc.putText(matrix, shape, sz3, FONT_HERSHEY_SIMPLEX, 0.5, scalar2, 2);

            } else {
                String shape = sd.detectRto(c);
                res.add(shape);
                Imgproc.putText(matrix, shape, sz3, FONT_HERSHEY_SIMPLEX, 0.5, scalar2, 2);

            }

            Image imageRes = sd.Mat2BufferedImage(matrix);
        }


        String res2;
        if (res.size() > 1) {

            res2 = ("more than one");
            return res2;
        } else if (res.size() == 0) {
            res2 = ("aucune forme");
            return res2;
        } else {
            return res.get(0);
        }
    }


    public static MatOfPoint convertUtil(Mat image) {

        MatOfPoint2f img2f = new MatOfPoint2f();
        MatOfPoint approxImg = new MatOfPoint();
        MatOfPoint2f approxImg2f = new MatOfPoint2f();

        image.convertTo(img2f, CvType.CV_32FC2);

        Imgproc.approxPolyDP(img2f, approxImg2f, 42, true);

        approxImg2f.convertTo(approxImg, CvType.CV_32S);

        return approxImg;
    }


    public ArrayList<String> reponseServeurRto(String file) {

        String coupServeur = randCoupServeurRto();
        String coupJoueur = detectShapes("detectRto", file);

        ArrayList<String> reponse = new ArrayList<>();

        reponse.add(coupJoueur);
        reponse.add(coupServeur);

        if (coupServeur.equals(coupJoueur)) {
            reponse.add("Egalite");
        } else if (coupJoueur.equals("Triangle") && coupServeur.equals("Circle")) {
            reponse.add("Serveur");
        } else if (coupJoueur.equals("Triangle") && coupServeur.equals("Carre")) {
            reponse.add("Joueur");
        } else if (coupJoueur.equals("Carre") && coupServeur.equals("Circle")) {
            reponse.add("Joueur");
        } else if (coupJoueur.equals("Carre") && coupServeur.equals("Triangle")) {
            reponse.add("Serveur");
        } else if (coupJoueur.equals("Circle") && coupServeur.equals("Carre")) {
            reponse.add("Serveur");
        } else if (coupJoueur.equals("Circle") && coupServeur.equals("Triangle")) {
            reponse.add("Joueur");
        } else reponse.add("erreur");


        return reponse;


    }

    private String randCoupServeurRto(){
        String formeCourant;
        final String[] formes = {"Triangle", "Carre", "Circle"};


        Random rand = new Random();
        int x = rand.nextInt(3);
        formeCourant = formes[x];
        return formeCourant;
    }

}