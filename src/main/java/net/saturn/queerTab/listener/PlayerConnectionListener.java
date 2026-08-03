package net.saturn.queerTab.listener;

import net.saturn.queerTab.identity.IdentityManager;
import net.saturn.queerTab.tab.TabListFormatter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectionListener implements Listener {

    // MONITOR so we apply the tab name after any other plugin (e.g. a
    // rank plugin) has finished setting up the player on join.
    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        TabListFormatter.apply(event.getPlayer(), IdentityManager.getIdentity(event.getPlayer().getUniqueId()));
    }
}
