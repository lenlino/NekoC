package com.gmail.kurumitk78.nekoc.events;

import com.gmail.kurumitk78.nekoc.NekoC;
import org.bukkit.event.player.*;
import org.bukkit.event.*;

public class NekoChat implements Listener
{
    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        if (NekoC.isNeko(event.getPlayer())) {
            String msg = event.getMessage(); //to-do: Use fucking regex instead of this garbage
            msg = msg.replace("な","にゃ");
            msg = msg.replace("に","にぃ");
            msg = msg.replace("にぃゃ","にゃ");
            msg = msg.replace("ぬ","にゅ");
            msg = msg.replace("ね","にぇ");
            msg = msg.replace("の","にょ");
            msg = msg.replace("na", "nya");
            msg = msg.replace("nu", "ny");
            msg = msg.replace("ni", "nyi");
            msg = msg.replace("ne", "nye");
            msg = msg.replace("no", "nyo");
            msg = msg.replace("mo", "myo");
            msg = msg.replace("Na", "Nya");
            msg = msg.replace("Nu", "Ny");
            msg = msg.replace("Ni", "Nyi");
            msg = msg.replace("Ne", "Nye");
            msg = msg.replace("No", "Nyo");
            msg = msg.replace("Mo", "Myo");
            msg = msg.replace("please", "pwease");
            msg = msg.replace("yeah", "nya");
            msg = msg.replace("huh", "nya");
            msg = msg.replace("meow", "nya");
            msg = msg.replace("Meow", "Nya");
            msg = msg.replace("honyey", "honey");
            msg = msg.replace("evernyunye", "everyonye");
            msg = msg.replace("Honyey", "Honey");
            msg = msg.replace("Evernyunye", "Everyonye");
            if (msg.equalsIgnoreCase("yay") || msg.equalsIgnoreCase("yay!")) {
                msg = "Nya!";
            }
            if (msg.matches("^[A-Za-z]+$")) {
                msg += "nya";
            } else {
                msg += "にゃ";
            }

            msg = msg.replace("nyanya", "nya");
            msg = msg.replace("Nyanya", "Nya");
            event.setMessage(msg);
        }
    }
}