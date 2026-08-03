package net.saturn.queerTab.commands;

import net.saturn.queerTab.identity.PresetRegistry;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Admin command for the plugin itself, separate from the per-player
 * /pronouns and /sexuality commands. Currently just reloads config.yml
 * so added/edited/removed presets take effect without a server restart.
 */
public class QueerTabCommand implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin;

    public QueerTabCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            PresetRegistry.reload(plugin);
            sender.sendMessage(ChatColor.GREEN + "QueerTab config reloaded — "
                    + PresetRegistry.getPronouns().size() + " pronoun presets, "
                    + PresetRegistry.getSexualities().size() + " sexuality presets loaded.");
            return true;
        }

        sender.sendMessage(ChatColor.YELLOW + "Usage: /queertab reload");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload").stream()
                    .filter(option -> option.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}