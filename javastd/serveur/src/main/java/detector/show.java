package detector;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class show {

    public JFrame Window;
    private ImageIcon image;
    private JLabel label;
    // private MatOfByte matOfByte;
    private Boolean SizeCustom;
    private int Height, Width;


    public show(){};
    public show(String title, int height, int width) {
        Boolean SizeCustom = true;
        Integer Height = height;
        Integer Width = width;

        JFrame Window = new JFrame();
        ImageIcon image = new ImageIcon();
        JLabel label = new JLabel();
        // matOfByte = new MatOfByte();
        label.setIcon(image);
        Window.getContentPane().add(label);
        Window.setResizable(false);
        Window.setTitle(title);
        setCloseOption(0);

    }

    public void showImage(Mat img) {
        if (SizeCustom) {
            Imgproc.resize(img, img, new Size(Height, Width));
        }
        // Highgui.imencode(".jpg", img, matOfByte);
        // byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            // InputStream in = new ByteArrayInputStream(byteArray);
            // bufImage = ImageIO.read(in);
            bufImage = shapedetector.Mat2BufferedImage(img);
            image.setImage(bufImage);
            Window.pack();
            label.updateUI();
            Window.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setCloseOption(int option) {

        switch (option) {
            case 0:
                Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                break;
            case 1:
                Window.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                break;
            default:
                Window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

    }


}
