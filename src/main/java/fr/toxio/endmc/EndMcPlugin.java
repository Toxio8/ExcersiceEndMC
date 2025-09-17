package fr.toxio.endmc;

import fr.toxio.endmc.commands.ZoneCommand;
import fr.toxio.endmc.listeners.PlayerMoveListener;
import fr.toxio.endmc.listeners.WandListener;
import fr.toxio.endmc.services.ZoneManager;
import fr.toxio.endmc.services.ZoneStorage;
import org.bukkit.plugin.java.JavaPlugin;

public class EndMcPlugin extends JavaPlugin {

    private ZoneManager zoneManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        saveResource("messages.yml", false);

        this.zoneManager = new ZoneManager(this, new ZoneStorage(this));
        this.zoneManager.loadAll();

        getCommand("zone").setExecutor(new ZoneCommand(this));

        getServer().getPluginManager().registerEvents(new WandListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(this), this);

        getLogger().info("EndMc enabled");
    }

    @Override
    public void onDisable() {
        this.zoneManager.saveAll();
        getLogger().info("EndMc disabled");
    }

    public ZoneManager getZoneManager() {
        return zoneManager;
    }
}
