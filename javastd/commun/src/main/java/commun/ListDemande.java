package commun;

public class ListDemande {
    String forme;
    Integer ticks;


    public ListDemande(){}
    public  ListDemande(String forme,Integer ticks){
        this.forme = forme;
        this.ticks = ticks;
    }

    public void setForme(String forme){
        this.forme = forme;
    }
    public void setTicks(Integer ticks){
        this.ticks = ticks;
    }

    public Integer getTicks(){
        return this.ticks;
    }

    public String getForme(){
        return this.forme;
    }
}
