package serveurTest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import serveur.Serveur;

import static org.junit.jupiter.api.Assertions.*;

class serveurTest {

    @Test
    @DisplayName("Tests sur la methode Verifier")
    void verifierTest (){

        // TESTS SUR BONNE REPONSES RECU
        String[] test1 = {"Carre","4"};
        assertTrue(Serveur.verifier(test1), "Test de bonne reponse rate pour carre" );

        String[] test2 = {"Triangle","3"};
        assertTrue(Serveur.verifier(test2), "Test de bonne reponse rate pour Triangle" );

        String[] test3 = {"Rond","5"};
        assertTrue(Serveur.verifier(test3), "Test de bonne reponse rate pour Cercle" );

        String[] test4 = {"Point","1"};
        assertTrue(Serveur.verifier(test4), "Test de bonne reponse rate pour Point" );

        String[] test5 = {"Segment","2"};
        assertTrue(Serveur.verifier(test5), "Test de bonne reponse rate pour Segment" );



        // TESTS SUR MAUVAISES REPONSES RECUS
        String[] test6 = {"Carre","2"};
        assertFalse(Serveur.verifier(test6), "Test d'erreur rate pour carre" );

        String[] test7 = {"Triangle","5"};
        assertFalse(Serveur.verifier(test7), "Test d'erreur rate pour triangle" );

        String[] test8 = {"Rond","1"};
        assertFalse(Serveur.verifier(test8), "Test d'erreur rate pour cercle" );

        String[] test9 = {"Point","3"};
        assertFalse(Serveur.verifier(test9), "Test d'erreur rate pour point" );

        String[] test10 = {"Segment", "4"};
        assertFalse(Serveur.verifier(test10), "Test d'erreur rate pour segment" );

    }

}