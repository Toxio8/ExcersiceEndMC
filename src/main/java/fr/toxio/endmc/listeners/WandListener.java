package fr.toxio.endmc.listeners;

import fr.toxio.endmc.EndMcPlugin;
import fr.toxio.endmc.commands.ZoneCommand;
import fr.toxio.endmc.utils.Selection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class WandListener implements Listener {
    private final EndMcPlugin plugin;

    public WandListener(EndMcPlugin plugin) { this.plugin = plugin; }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if (item == null) return;

        if (!item.getType().equals(Material.WOOD_AXE)) return;

        if (!item.hasItemMeta() || !item.getItemMeta().hasDisplayName()) return;

        String displayName = item.getItemMeta().getDisplayName();
        String expectedName = ChatColor.GREEN + "Zone Wand";

        if (!displayName.equals(expectedName)) {
            System.out.println("Wallah c'est pas la bonne hache");
            return;
        }

        if (!(plugin.getCommand("zone").getExecutor() instanceof ZoneCommand)) return;

        ZoneCommand cmd = (ZoneCommand) plugin.getCommand("zone").getExecutor();
        Selection sel = cmd.getSelections().computeIfAbsent(e.getPlayer(), k -> new Selection());

        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            sel.setPos1(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(ChatColor.GREEN + "Position 1 définie.");

        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            sel.setPos2(e.getClickedBlock().getLocation());
            e.getPlayer().sendMessage(ChatColor.GREEN + "Position 2 définie.");

        }
        e.setCancelled(true);
    }
}
