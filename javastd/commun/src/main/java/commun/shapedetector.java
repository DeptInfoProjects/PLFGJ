package commun;


import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import static org.opencv.imgproc.Imgproc.boundingRect;

public class shapedetector {

    public shapedetector() {
    }

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
                //Un carré possède un ratio w/h d'eniron 1.0
                //Dans le cas contraire on suppose qu'il s'agit d'un rectangle
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
    public void displayImage(Image img2) {

        //BufferedImage img=ImageIO.read(new File("/HelloOpenCV/lena.png"));
        ImageIcon icon=new ImageIcon(img2);
        JFrame frame=new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(img2.getWidth(null)+50, img2.getHeight(null)+50);
        JLabel lbl=new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public BufferedImage Mat2BufferedImage(Mat m) {
        // Fastest code
        // output can be assigned either to a BufferedImage or to an Image

        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }


}