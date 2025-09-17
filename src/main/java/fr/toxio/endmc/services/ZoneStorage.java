package fr.toxio.endmc.services;

import fr.toxio.endmc.entities.Zone;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ZoneStorage {
    private final JavaPlugin plugin;
    private final File file;

    public ZoneStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "zones.yml");

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Impossible de créer zones.yml : " + e.getMessage());
            }
        }
    }

    public List<Zone> loadZones() {
        if (!file.exists()) return new ArrayList<>();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        List<Zone> out = new ArrayList<>();

        for (String key : config.getKeys(false)) {
            try {
                Zone z = new Zone();
                z.setName(key);
                z.setWorld(UUID.fromString(Objects.requireNonNull(config.getString(key + ".world"))));
                z.setX1(config.getInt(key + ".x1"));
                z.setY1(config.getInt(key + ".y1"));
                z.setZ1(config.getInt(key + ".z1"));
                z.setX2(config.getInt(key + ".x2"));
                z.setY2(config.getInt(key + ".y2"));
                z.setZ2(config.getInt(key + ".z2"));
                out.add(z);
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to load zone: " + key);
            }
        }
        return out;
    }

    public void saveZones(List<Zone> zones) {
        YamlConfiguration config = new YamlConfiguration();
        for (Zone z : zones) {
            String base = z.getName();
            config.set(base + ".world", z.getWorld().toString());
            config.set(base + ".x1", z.getX1());
            config.set(base + ".y1", z.getY1());
            config.set(base + ".z1", z.getZ1());
            config.set(base + ".x2", z.getX2());
            config.set(base + ".y2", z.getY2());
            config.set(base + ".z2", z.getZ2());
        }
        try {
            config.save(file);
            plugin.getLogger().info("Zones sauvegardées dans zones.yml (" + zones.size() + ")");
        } catch (Exception e) {
            plugin.getLogger().severe("Erreur lors de la sauvegarde de zones.yml: " + e.getMessage());
        }
    }
}
