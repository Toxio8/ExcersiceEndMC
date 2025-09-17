package fr.toxio.endmc.services;

import fr.toxio.endmc.EndMcPlugin;
import fr.toxio.endmc.entities.Zone;
import org.bukkit.Location;
import java.util.*;


public class ZoneManager {
    private final EndMcPlugin plugin;
    private final ZoneStorage storage;
    private final List<Zone> zones = new ArrayList<>();

    public ZoneManager(EndMcPlugin plugin, ZoneStorage storage) {
        this.plugin = plugin;
        this.storage = storage;
    }

    public void loadAll() {
        List<Zone> loaded = storage.loadZones();
        if (loaded != null) zones.addAll(loaded);
    }

    public void saveAll() {
        storage.saveZones(zones);
    }

    public Optional<Zone> getByName(String name) {
        for (Zone z : zones) {
            if (z.getName().equalsIgnoreCase(name)) {
                return Optional.of(z);
            }
        }
        return Optional.empty();
    }

    public void addZone(Zone zone) {
        zones.add(zone);
        saveAll();
    }

    public boolean removeZone(String name) {
        Optional<Zone> z = getByName(name);
        if (z.isPresent()) {
            zones.remove(z.get());
            saveAll();
            return true;
        }
        return false;
    }

    public List<Zone> list() {
        return Collections.unmodifiableList(zones);
    }

    public Optional<Zone> findContaining(Location location) {
        for (Zone z : zones) {
            if (z.contains(location)) {
                return Optional.of(z);
            }
        }
        return Optional.empty();
    }
}
