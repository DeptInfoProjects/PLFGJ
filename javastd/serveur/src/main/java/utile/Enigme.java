package utile;

public class Enigme {

    private String enigme;
    private String reponse;
    private static final String separateur = ";";
    private static final String lineSeparateur = "\n";

    public String getEnigme() {
        return enigme;
    }

    public void setEnigme(String enigme) {
        this.enigme = enigme;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public Enigme(String enigme, String reponse){
        this.enigme = enigme;
        this.reponse = reponse;
    }

    public String getSeparateur(){
        return separateur;
    }

    public String getLineSeparateur(){
        return lineSeparateur;
    }




}
