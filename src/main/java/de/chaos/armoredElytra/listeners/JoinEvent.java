package de.chaos.armoredElytra.listeners;

import de.chaos.armoredElytra.utils.TexturePack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {
    TexturePack texturePack;
    public JoinEvent(TexturePack texturePack) {
        this.texturePack = texturePack;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        texturePack.sendResourcePack(event.getPlayer());
    }
}
