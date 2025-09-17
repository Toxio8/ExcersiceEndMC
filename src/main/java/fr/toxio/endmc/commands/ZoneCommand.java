package fr.toxio.endmc.commands;

import fr.toxio.endmc.EndMcPlugin;
import fr.toxio.endmc.entities.Zone;
import fr.toxio.endmc.utils.Selection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ZoneCommand implements CommandExecutor {

    private final EndMcPlugin plugin;
    private final Map<Player, Selection> selections = new HashMap<>();

    public ZoneCommand(EndMcPlugin plugin) { this.plugin = plugin; }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seul un joueur peut utiliser cette commande.");
            return true;
        }

        Player p = (Player) sender;
        if (args.length == 0) {
            p.sendMessage("/zone wand | create <name> | list | remove <name> | info <name>");
            return true;
        }

        String sub = args[0].toLowerCase();
        switch (sub) {

            case "wand":
                ItemStack wandItem = new ItemStack(Material.WOOD_AXE);
                ItemMeta itemMeta = wandItem.getItemMeta();
                itemMeta.setDisplayName("§aZone Wand");
                wandItem.setItemMeta(itemMeta);

                p.getInventory().addItem(wandItem);
                p.sendMessage(ChatColor.GREEN + "Wand donnée.");
                return true;

            case "create":
                Selection sel = selections.get(p);

                if (args.length < 2) {
                    p.sendMessage("Usage: /zone create <name>"); return true;
                }

                String name = args[1];

                if (sel == null || !sel.isComplete()) {
                    p.sendMessage(ChatColor.RED + "Sélection incomplète. Utilise la wand."); return true;
                }


                if (plugin.getZoneManager().getByName(name).isPresent()) {
                    p.sendMessage(ChatColor.YELLOW + "Zone existante."); return true;
                }

                Zone z = new Zone(name, sel.getPos1(), sel.getPos2());
                plugin.getZoneManager().addZone(z);
                p.sendMessage(ChatColor.GREEN + "Zone créée: " + name);
                selections.remove(p);

                return true;

            case "list":
                for (Zone zone1 : plugin.getZoneManager().list()) {
                    p.sendMessage("- " + zone1.getName());
                }
                return true;

            case "remove":
                if (args.length < 2) {
                    p.sendMessage("Usage: /zone remove <name>"); return true;
                }

                boolean removed = plugin.getZoneManager().removeZone(args[1]);
                p.sendMessage(removed ? ChatColor.GREEN + "Zone supprimée." : ChatColor.RED + "Zone introuvable.");

                return true;

            case "info":
                if (args.length < 2) {
                    p.sendMessage("Usage: /zone info <name>"); return true;
                }

                plugin.getZoneManager().getByName(args[1]).ifPresentOrElse(zone -> {
                    p.sendMessage("Zone: " + zone.getName());
                    p.sendMessage("X: " + zone.getX1() + " -> " + zone.getX2());
                    p.sendMessage("Y: " + zone.getY1() + " -> " + zone.getY2());
                    p.sendMessage("Z: " + zone.getZ1() + " -> " + zone.getZ2());
                }, () -> p.sendMessage(ChatColor.RED + "Zone introuvable."));

                return true;

            default:
                p.sendMessage("Commande inconnue.");
                return true;
        }
    }

    public Map<Player, Selection> getSelections() { return selections; }
}
