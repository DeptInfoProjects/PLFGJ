package detector;

import org.junit.Test;
import serveur.Serveur;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;

import static org.junit.Assert.assertEquals;

public class RtoDetectorTest {

    @Test
    public void detectRtoS() throws IOException {
        Serveur serveur = new Serveur();
        ArrayList<String> resultatAttendue = new ArrayList<>();
        resultatAttendue.add("Carre");
        resultatAttendue.add("Carre"); // Carre plein
        resultatAttendue.add("Circle"); // Cas de base
        resultatAttendue.add("Triangle");
        resultatAttendue.add("trop de formes !");

        try {
            serveur.loadOpenCV();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }


        InputStream resource = getClass().getClassLoader().getResourceAsStream("ressourcesRTO.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(resource));
        String base64;
        Integer compter = 0;
        while ((base64 = br.readLine()) != null) {
            byte[] imgbytes;

            //System.out.println("s de talle : "+s.length());
            imgbytes = Base64.getMimeDecoder().decode(base64);

            final File file = new File("test.png");
            final FileOutputStream fileOut = new FileOutputStream(file);
            fileOut.write(imgbytes);
            fileOut.flush();
            fileOut.close();

            RtoDetector rd = new RtoDetector();
            String reponse = rd.detectRtoS("test.png");
            assertEquals("Expected      | Donner    ", resultatAttendue.get(compter), reponse);
            compter +=1;
        }
        br.close();

    }
}