package commun;

public class Identification {

    private String nom ;

    public Identification() {}


    public Identification(String nom){
        setNom(nom);
    }

    public Identification(String nom, int level) {
        this.nom = nom;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

}
