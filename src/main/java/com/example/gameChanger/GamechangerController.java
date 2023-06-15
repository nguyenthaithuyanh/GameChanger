package com.example.gameChanger;
import com.example.gameChanger.Game.Game;
import com.example.gameChanger.Player.Player;
import com.example.gameChanger.Player.PlayerService;
import com.example.gameChanger.Team.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//Klasse, welche alle Ressourcen für die API hat -> hier sind lediglich alle Methoden, die mit dem Browser kommunizieren
@Controller ("/")
public class GamechangerController {

    private final PlayerService playerService;
    private Game game;
    private Team t1;
    private Team t2;


    @Autowired
    public GamechangerController(PlayerService playerService) {
        this.playerService = playerService;
        this.t1 = new Team();
        this.t2 = new Team();
        game = new Game(t1,t2);
    }
   // @DeleteMapping(path = "{playerID}")
   // public void deletePlayer(@PathVariable ("playerID") int id) {playerService.deletePlayer (id);}

    @GetMapping(path = {"anmelden"})
    public String startseite() {
        return "anmelden";
    }

    @PostMapping(path = {"anmeldungErfolgreich"})
    public String registrieren(Model model,
                               @RequestParam String t1p1vorname, @RequestParam String t1p1nachname,@RequestParam boolean t1p1RightOfService, @RequestParam int t1p1teamnr,
                               @RequestParam String t1p2vorname, @RequestParam String t1p2nachname,@RequestParam boolean t1p2RightOfService, @RequestParam int t1p2teamnr,
                               @RequestParam String t2p1vorname, @RequestParam String t2p1nachname,@RequestParam boolean t2p1RightOfService, @RequestParam int t2p1teamnr,
                               @RequestParam String t2p2vorname, @RequestParam String  t2p2nachname,@RequestParam boolean t2p2RightOfService, @RequestParam int t2p2teamnr){
        model.addAttribute("title", "Badminton Turnier");

        model.addAttribute("vorname", t1p1vorname);
        model.addAttribute("nachname", t1p1nachname);
        model.addAttribute("aufschlagsrecht", t1p1RightOfService);
        model.addAttribute("teamnr", t1p1teamnr);


        model.addAttribute("vorname", t1p2vorname);
        model.addAttribute("nachname", t1p2nachname);
        model.addAttribute("aufschlagsrecht", t1p2RightOfService);
        model.addAttribute("teamnr", t1p2teamnr);

        model.addAttribute("vorname", t2p1vorname);
        model.addAttribute("nachname", t2p1nachname);
        model.addAttribute("aufschlagsrecht", t2p1RightOfService);
        model.addAttribute("teamnr", t2p1teamnr);

        model.addAttribute("vorname", t2p2vorname);
        model.addAttribute("nachname", t2p2nachname);
        model.addAttribute("aufschlagsrecht", t2p2RightOfService);
        model.addAttribute("teamnr", t2p2teamnr);

        t1.setP1(this.playerService.registration(t1p1vorname, t1p1nachname, t1p1RightOfService, t1p1teamnr));
        t1.setP2(this.playerService.registration(t1p2vorname, t1p2nachname,t1p2RightOfService, t1p2teamnr));
        t2.setP1(this.playerService.registration(t2p1vorname, t2p1nachname, t2p1RightOfService, t2p1teamnr));
        t2.setP2(this.playerService.registration(t2p2vorname, t2p2nachname,t2p2RightOfService, t2p2teamnr));

        model.addAttribute("team1Punkte", t1.getCurrentScore());
        model.addAttribute("team2Punkte", t2.getCurrentScore());
        model.addAttribute("aktuellerSatz", game.getCurrentSet());
        return "anmeldungErfolgreich";
    }

    @GetMapping(path="updatepunkt")
    public String updatePunkt(Model model,@RequestParam int punkt, @RequestParam int team){

        //negativen Punktestand vermeiden
        if(t1.getCurrentScore() <0 && t2.getCurrentScore() <0 || t1.getCurrentScore() <0 || t2.getCurrentScore() <0){
            t1.scoreNull();
            t2.scoreNull();
        }
        //Pause bei 11:11
        if (t1.getCurrentScore() == 10 && t2.getCurrentScore() == 11 || t1.getCurrentScore() == 11 && t2.getCurrentScore()==10) {
            model.addAttribute("pause", game.pauseAnzeige());
        }
        //Pause, bei 3. Satz + Seitenwechsel
        if (t1.getCurrentScore() == 10 && t2.getCurrentScore() == 11 && game.getCurrentSet() == 3 || t1.getCurrentScore() == 11 && t2.getCurrentScore()==10 && game.getCurrentSet()==3) {
            model.addAttribute("pauseSatz3", game.pauseAnzeigeSatz3());
        }
        //Gewinner prüfen:
        if (t1.getGewonneneSaetze() == 2 || t1.getGewonneneSaetze() == 3 || game.getCurrentSet()>3) {
                return "winnerT1";
        }
        else if (t2.getGewonneneSaetze()==2 || t2.getGewonneneSaetze()==3 || game.getCurrentSet()>3) {
            return "winnerT2";
        }

        if(team==1){
            t1.setCurrentScore(punkt);
            t1.setPunktErzielt(true);
            t2.setPunktErzielt(false);

        }else if(team==2){
            t2.setCurrentScore(punkt);
            t2.setPunktErzielt(true);
            t1.setPunktErzielt(false);
        }

        //aktuellen Satz erhöhen, wenn einer der beiden Teams gewinnt & Score resetten:
        //Fall1: Team1 gewinnt einen Satz
        if(t1.getCurrentScore() >= 21 && t1.getCurrentScore()-t2.getCurrentScore() >=2 || t1.getCurrentScore()==30) {
            game.setCurrentSet();
            model.addAttribute("seitenwechsel", game.seitenWechselAnzeige());
            t1.resetScore();
            t2.resetScore();
            t1.setSatzGewonnen();
        }
        //Fall2: Team2 gewinnt einen Satz
        else if (t2.getCurrentScore() >=21 && t2.getCurrentScore()-t1.getCurrentScore() >=2 || t2.getCurrentScore() ==30) {
            game.setCurrentSet();
            model.addAttribute("seitenwechsel", game.seitenWechselAnzeige());
            t1.resetScore();
            t2.resetScore();
            t2.setSatzGewonnen();
        }
        model.addAttribute("team1Punkte", t1.getCurrentScore());
        model.addAttribute("team2Punkte", t2.getCurrentScore());
        model.addAttribute("aktuellerSatz", game.getCurrentSet());
        model.addAttribute("gewonneneSaetze1", t1.getGewonneneSaetze());
        model.addAttribute("gewonneneSaetze2", t2.getGewonneneSaetze());


        //Spielstart
        for (Player p : playerService.getPlayerList()) {
            if (p.getRightOfService())
            {
                if (p.getTeamNr() == 1) {
                    model.addAttribute("aufschlag", p.getVorname());
                    model.addAttribute("feldposition",t1.getFieldPosition());
                    model.addAttribute("teamnr",p.getTeamNr());
                    if (!t1.getPunktErzielt()) {
                        p.setRightOfService(false);
                        t2.getP2().setRightOfService(true);
                        model.addAttribute("aufschlag", t2.getP2().getVorname());
                        model.addAttribute("feldposition",t2.getFieldPosition());
                        model.addAttribute("teamnr",t2.getP2().getTeamNr());
                    }

                }
                else if (p.getTeamNr() == 2) {
                    model.addAttribute("aufschlag", p.getVorname());
                    model.addAttribute("feldposition",t2.getFieldPosition());
                    model.addAttribute("teamnr",p.getTeamNr());
                    if (!t2.getPunktErzielt()) {
                        p.setRightOfService(false);
                        t1.getP1().setRightOfService(true);
                        model.addAttribute( "aufschlag", t1.getP1().getVorname());
                        model.addAttribute("feldposition",t1.getFieldPosition());
                        model.addAttribute("teamnr",p.getTeamNr());
                    }
                }
            }
        }
        return "anmeldungErfolgreich";
    }


}
