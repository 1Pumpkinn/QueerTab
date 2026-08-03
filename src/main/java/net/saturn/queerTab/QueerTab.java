package net.saturn.queerTab;

import net.saturn.queerTab.commands.PronounsCommand;
import net.saturn.queerTab.commands.QueerTabCommand;
import net.saturn.queerTab.commands.SexualityCommand;
import net.saturn.queerTab.identity.IdentityManager;
import net.saturn.queerTab.identity.PresetRegistry;
import net.saturn.queerTab.listener.PlayerConnectionListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class QueerTab extends JavaPlugin {

    @Override
    public void onEnable() {
        PresetRegistry.init(this);
        IdentityManager.init(this);

        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        PronounsCommand pronounsCommand = new PronounsCommand();
        getCommand("pronouns").setExecutor(pronounsCommand);
        getCommand("pronouns").setTabCompleter(pronounsCommand);

        SexualityCommand sexualityCommand = new SexualityCommand();
        getCommand("sexuality").setExecutor(sexualityCommand);
        getCommand("sexuality").setTabCompleter(sexualityCommand);

        QueerTabCommand queerTabCommand = new QueerTabCommand(this);
        getCommand("queertab").setExecutor(queerTabCommand);
        getCommand("queertab").setTabCompleter(queerTabCommand);

        getLogger().info("QueerTab enabled.");
    }

    @Override
    public void onDisable() {
        // IdentityManager saves on every change, so nothing to flush here.
    }
}