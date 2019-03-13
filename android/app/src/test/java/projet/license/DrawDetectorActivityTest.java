package projet.license;

import org.junit.Test;
import org.junit.Assert.* ;
import java.util.Random;

import static org.junit.Assert.*;

public class DrawDetectorActivityTest {

    @Test
    public void onCreate() {
    }

    @Test
    public void newForme() {
        final String[] formes = {"Point", "Segment", "Triangle", "Carre", "Rond"};
        Random rand = new Random();
        int rando = rand.nextInt(5);
        String TestformeRandom = formes[rando];

        switch (TestformeRandom) {
            case "Point":
                assertEquals(TestformeRandom, "Point");
            case "Segment":
                assertEquals(TestformeRandom, "Segment");
            case "Triangle":
                assertEquals(TestformeRandom, "Triangle");
            case "Carre":
                assertEquals(TestformeRandom, "Carre");
            case "Rond":
                assertEquals(TestformeRandom, "Rond");


        }
    }


    @Test
    public void onClick() {
    }

    @Test
    public void majScor() {



    }


}