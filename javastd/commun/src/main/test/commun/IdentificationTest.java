package commun;


import org.junit.Test;

import static org.junit.Assert.*;

public class IdentificationTest{
    @Test
    public void Identification() {
        Identification ide = new Identification();
        String nom = "kyriakos";
        ide.setNom(nom);
        assertTrue(ide.getNom().equals(nom));    // (3)
    }}
