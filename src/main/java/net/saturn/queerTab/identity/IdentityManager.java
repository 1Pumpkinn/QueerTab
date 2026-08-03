package net.saturn.queerTab.identity;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Central access point for player identity data. Exposes static helpers
 * (e.g. IdentityManager.getIdentity(uuid)) so callers — commands,
 * listeners, the tab formatter — don't need to pass an instance around.
 *
 * Every mutation is saved immediately; identities.yml stays small so this
 * is cheap, and it avoids losing data on an unclean shutdown.
 */
public class IdentityManager {

    private static IdentityManager instance;

    private final IdentityStorage storage;
    private final Map<UUID, PlayerIdentity> cache;

    private IdentityManager(JavaPlugin plugin) {
        this.storage = new IdentityStorage(plugin);
        this.cache = new HashMap<>(storage.loadAll());
    }

    public static void init(JavaPlugin plugin) {
        instance = new IdentityManager(plugin);
    }

    private static IdentityManager get() {
        if (instance == null) {
            throw new IllegalStateException("IdentityManager.init() was not called");
        }
        return instance;
    }

    public static PlayerIdentity getIdentity(UUID uuid) {
        return get().cache.computeIfAbsent(uuid, PlayerIdentity::new);
    }

    public static void setPronoun(UUID uuid, Preset preset) {
        getIdentity(uuid).setPronoun(preset);
        get().save();
    }

    public static void setSexuality(UUID uuid, Preset preset) {
        getIdentity(uuid).setSexuality(preset);
        get().save();
    }

    public static void clearPronoun(UUID uuid) {
        getIdentity(uuid).setPronoun(null);
        get().save();
    }

    public static void clearSexuality(UUID uuid) {
        getIdentity(uuid).setSexuality(null);
        get().save();
    }

    private void save() {
        storage.saveAll(cache);
    }
}