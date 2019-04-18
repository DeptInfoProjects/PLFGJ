package commun;

public class StatsData {
    int JoueurWin;
    int serveurWin;
    double statRto;

    public StatsData(){}

    public StatsData(int JoueurWin, int ServeurWin, double statRto){
        this.JoueurWin = JoueurWin;
        this.serveurWin = ServeurWin;
        this.statRto = statRto;
    }


    public int getJoueurWin(){return JoueurWin;}
    public void setJoueurWin(int joueurWin){this.JoueurWin = joueurWin;}

    public int getServeurWin(){return serveurWin;}
    public void setServeurWin(int serveurWin){this.serveurWin = serveurWin;}

    public double getStatRto(){return statRto;}
    public void setStatRto(double Statrto){this.statRto = Statrto;}

}
