package fr.toxio.endmc.listeners;

import fr.toxio.endmc.EndMcPlugin;
import fr.toxio.endmc.entities.Zone;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerMoveListener implements Listener {

    private final EndMcPlugin plugin;
    private final Map<UUID, String> lastZone = new HashMap<>();

    public PlayerMoveListener(EndMcPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockY() == e.getTo().getBlockY() && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) return;

        Optional<Zone> opt = plugin.getZoneManager().findContaining(e.getTo());
        String current = opt.map(Zone::getName).orElse(null);
        String prev = lastZone.get(e.getPlayer().getUniqueId());

        if (prev == null && current != null) {
            e.getPlayer().sendMessage("§aTu es entré dans la zone " + current);
        } else if (prev != null && current == null) {
            e.getPlayer().sendMessage("§cTu as quitté la zone " + prev);
        } else if (prev != null && current != null && !prev.equals(current)) {
            e.getPlayer().sendMessage("§aTu es entré dans la zone " + current);
        }

        if (current == null) {
            lastZone.remove(e.getPlayer().getUniqueId());
        } else {
            lastZone.put(e.getPlayer().getUniqueId(), current);
        }
    }
}
