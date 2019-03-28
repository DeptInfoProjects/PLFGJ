package projet.license;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import commun.Identification;
import io.socket.client.Socket;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;



@RunWith(MockitoJUnitRunner.class)
public class ConnexionTest {

    Connexion connexion;

    @Mock
    Controleur ctrl;

    @Mock
    Socket socket;


    @Before
    public void setUp() throws Exception {
        // MockitoAnnotations.initMocks(this);


        connexion = new Connexion();
        connexion.setSocket(socket);
        connexion.setControleur(ctrl);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void seConnecter() {
        //Test si le mock marche bien
        assertEquals(true, connexion.seConnecter());
        // connexion.sendLB64(6);
    }

    @Test
    public void envoyerId() throws JSONException {
        // donnnée en entrée
        Identification id = new Identification("Essai");

        // oracle
        JSONObject obj = new JSONObject();
        obj.put("nom", "Essai");

        // méthode testée
        connexion.envoyerId(id);

        // assertion / vérification
        Mockito.verify(socket, Mockito.times(1)).emit(eq("identification"), any());
    }
    @Test
    public void envoyerStart(){
        connexion.envoyerStart();
        Mockito.verify(socket,Mockito.times(1)).emit(eq("btnStart"),any());
    }
    @Test
    public void envoyerReset(){
        connexion.envoyerReset();
        Mockito.verify(socket,Mockito.times(1)).emit(eq("btnEffacer"),any());
    }
    @Test
    public void envoyerColor(){
        connexion.envoyerColor();
        Mockito.verify(socket,Mockito.times(1)).emit(eq("btnColor"),any());
    }
    @Test
    public void envoyerValider(){
        String list = "Triangle,3";

        connexion.envoyerValider(list);
        Mockito.verify(socket,Mockito.times(1)).emit(eq("nbpoints"),any());
    }

    @Test
    public void envoyerTutoriel(){
        connexion.envoyerTutoriel();
        Mockito.verify(socket,Mockito.times(1)).emit(eq("Bien recu5"),any());
    }

    //@Test
    //public void


}