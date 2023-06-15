package com.example.gameChanger.Player;

import com.example.gameChanger.Team.Team;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "player",
        schema = "public"
)
public class Player {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE
    )
    private int id;
    @Column(
            name = "vorname"
    )
    private String vorname;
    @Column(
            name = "nachname"
    )
    private String nachname;
    @Column(
            name = "rightOfService"
    )
    private boolean rightOfService;

    @Column(
            name = "teamNr"
    )
    private int teamNr;


    public Player() {
    }
    public Player(String vorname, String nachname, boolean rightOfService, int teamnr) {
        this.vorname = vorname;
        this.nachname = nachname;
        this.rightOfService = rightOfService;
        this.teamNr = teamnr;
    }

    public String getVorname() {return vorname;}
    public String getNachname() {
        return nachname;
    }
    public void setNachname(String nachname) {
        this.nachname = nachname;
    }
    public boolean getRightOfService () {return rightOfService;}

    public void setRightOfService(boolean b) {rightOfService = b;}
    public int getTeamNr () {
        return teamNr;
    }

    public int getId () {return id;}



}
