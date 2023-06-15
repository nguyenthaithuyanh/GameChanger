package com.example.gameChanger.Player;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//verantworlich für das Management von Daten (=Business Logic)
//Service, welcher Methoden für den Controller liefert z.B. hinzufügen, löschen
@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayerList ()
    {
        return playerRepository.findAll();
    }


    public Player registration(String vorname,String nachname, boolean rightOfService, int teamnr) {
        Player player = new Player(vorname, nachname, rightOfService,teamnr);
        ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("vorname", ExampleMatcher.GenericPropertyMatchers.ignoreCase()).withMatcher("nachname", ExampleMatcher.GenericPropertyMatchers.ignoreCase()).withMatcher("rightOfService", ExampleMatcher.GenericPropertyMatchers.ignoreCase());
        Example<Player> example = Example.of(player, matcher);
        Player result = (Player)this.playerRepository.findOne(example).orElse((null));
        return result != null ? result : (Player)this.playerRepository.save(player);
    }


    public void deletePlayer(int id) {
        boolean exists = playerRepository.existsById(id);
        if(!exists) {
            throw new IllegalStateException("player mit id " + id + " existiert nicht.");
        }
        playerRepository.deleteById(id);
    }

    @Transactional
    public void updatePlayer(int playerID, String name) {
        //1.Überprüfung, ob der Player mit der Id überhaupt existiert
        Player player = playerRepository.
                findById(playerID).
                orElseThrow(() -> new IllegalStateException(("Player mit id " + playerID + " existiert nicht.")));
        //2.Name nur updaten, wenn es wirklich ein neuer Name ist und != null
        if (name != null && name.length() > 0 && !Objects.equals(player.getNachname(), name)) {
            player.setNachname(name);
        }
    }
}
