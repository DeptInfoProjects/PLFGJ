package commun;

import java.util.List;

public interface Affichage {

    void majScor(boolean ok);

    void timeGameScor(Integer score, Integer tentative);

    void listTimeGame(String listDem,String listRec);

    void rtoGameScore(String coupJoueur, String coupServeur, String resultat);
	
	void riddleGame(boolean rep);

}
