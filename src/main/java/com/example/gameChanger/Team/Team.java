package com.example.gameChanger.Team;
import com.example.gameChanger.Player.Player;

public class Team {

    Player p1;
    Player p2;
    private int currentScore;
    private final String FIELDPOSRIGHT = "rechte Feldhälfte";
    private final String FIELDPOSLEFT = "linke Feldhälfte";
    private boolean punktErzielt;

    private int gewonneneSaetze;

    public Team (){}
    public Team(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.gewonneneSaetze = 0;
        this.currentScore = 0;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int punkt) {
        currentScore+= punkt;
    }

    public void resetScore(){currentScore= 0;}

    public String getFieldPosition () {
        //wenn Punktezahl gerade --> rechts
        //wenn Punktezahl ungerade --> links
        if (getCurrentScore() % 2 == 0) {
            return FIELDPOSRIGHT;
        } else return FIELDPOSLEFT;
    }

    public void setPunktErzielt (boolean b) {punktErzielt = b;}
    public boolean getPunktErzielt () {return  punktErzielt;}

    public void setP1 (Player p1) {this.p1 = p1;}
    public void setP2 (Player p2) {this.p2 = p2;}
    public Player getP1 () {return p1;}
    public Player getP2 () {return p2;}
    public void setSatzGewonnen () {gewonneneSaetze++;}
    public int getGewonneneSaetze () {return gewonneneSaetze;}

    public int scoreNull () {return currentScore = 0;}










}
