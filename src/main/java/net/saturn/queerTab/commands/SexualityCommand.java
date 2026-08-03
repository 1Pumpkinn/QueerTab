package net.saturn.queerTab.commands;

import net.saturn.queerTab.identity.IdentityManager;
import net.saturn.queerTab.identity.SexualityPreset;
import net.saturn.queerTab.tab.TabListFormatter;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SexualityCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "set" -> {
                if (args.length < 2) {
                    sendUsage(player);
                    return true;
                }
                SexualityPreset preset = SexualityPreset.fromId(args[1]);
                if (preset == null) {
                    player.sendMessage(ChatColor.RED + "Unknown preset. Use /sexuality list to see options.");
                    return true;
                }
                IdentityManager.setSexuality(player.getUniqueId(), preset);
                TabListFormatter.apply(player, IdentityManager.getIdentity(player.getUniqueId()));
                player.sendMessage(ChatColor.GREEN + "Identity tag set to " + preset.getTag());
            }
            case "clear" -> {
                IdentityManager.clearSexuality(player.getUniqueId());
                TabListFormatter.apply(player, IdentityManager.getIdentity(player.getUniqueId()));
                player.sendMessage(ChatColor.GREEN + "Identity tag cleared.");
            }
            case "list" -> {
                player.sendMessage(ChatColor.YELLOW + "Available identity presets:");
                for (SexualityPreset preset : SexualityPreset.values()) {
                    player.sendMessage("  " + preset.getTag() + ChatColor.GRAY + " -> /sexuality set " + preset.getId());
                }
            }
            default -> sendUsage(player);
        }

        return true;
    }

    private void sendUsage(Player player) {
        player.sendMessage(ChatColor.YELLOW + "Usage: /sexuality <set|clear|list> [preset]");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return filter(Arrays.asList("set", "clear", "list"), args[0]);
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return filter(
                    Arrays.stream(SexualityPreset.values()).map(SexualityPreset::getId).collect(Collectors.toList()),
                    args[1]
            );
        }

        return new ArrayList<>();
    }

    private List<String> filter(List<String> options, String prefix) {
        return options.stream()
                .filter(option -> option.toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }
}
