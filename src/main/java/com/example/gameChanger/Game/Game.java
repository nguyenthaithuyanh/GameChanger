package com.example.gameChanger.Game;


import com.example.gameChanger.Player.Player;
import com.example.gameChanger.Player.PlayerRepository;
import com.example.gameChanger.Team.Team;

public class Game {

    private int currentSet;
    private final String FELDLINKS = "links";
    private final String FELDRECHTS = "rechts";

    private Team t1;
    private Team t2;

    public Game(Team t1, Team t2){
       this.t1 = t1;
       this.t2 = t2;
       this.currentSet = 1;
    }

    public void setCurrentSet() {currentSet++;}
    public int getCurrentSet() {return currentSet;}

    public Team getT1 () {return t1;}
    public Team getT2 () {return t2;}
    public String pauseAnzeige () {return "Es steht 11:11 - Zeit für eine 10 minütige Pause!";}
    public String pauseAnzeigeSatz3 () {return "Danach müssen die Teams bitte die Seiten wechseln!";}
    public String seitenWechselAnzeige () {return "Teams bitte einmal die Seiten wechseln!";}
}
