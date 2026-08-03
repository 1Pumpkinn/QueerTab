package net.saturn.queerTab.identity;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

/**
 * Handles reading and writing player identity data to identities.yml.
 * Knows nothing about caching or the plugin's runtime state — just I/O.
 *
 * Presets are stored by id and resolved against the current
 * {@link PresetRegistry} on load. If a player's stored id no longer
 * exists in config.yml (e.g. it was removed), that field is silently
 * left unset rather than failing to load the whole file.
 */
public class IdentityStorage {

    private final JavaPlugin plugin;
    private final File file;

    public IdentityStorage(JavaPlugin plugin) {
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), "identities.yml");
    }

    public Map<UUID, PlayerIdentity> loadAll() {
        Map<UUID, PlayerIdentity> result = new HashMap<>();

        if (!file.exists()) {
            return result;
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        for (String key : config.getKeys(false)) {
            try {
                UUID uuid = UUID.fromString(key);
                PlayerIdentity identity = new PlayerIdentity(uuid);

                String pronounId = config.getString(key + ".pronoun");
                if (pronounId != null) {
                    identity.setPronoun(PresetRegistry.findPronoun(pronounId));
                }

                String sexualityId = config.getString(key + ".sexuality");
                if (sexualityId != null) {
                    identity.setSexuality(PresetRegistry.findSexuality(sexualityId));
                }

                result.put(uuid, identity);
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Skipping invalid UUID in identities.yml: " + key);
            }
        }

        return result;
    }

    public void saveAll(Map<UUID, PlayerIdentity> identities) {
        YamlConfiguration config = new YamlConfiguration();

        for (Map.Entry<UUID, PlayerIdentity> entry : identities.entrySet()) {
            PlayerIdentity identity = entry.getValue();

            if (identity.isEmpty()) {
                continue;
            }

            String key = entry.getKey().toString();

            if (identity.getPronoun() != null) {
                config.set(key + ".pronoun", identity.getPronoun().getId());
            }
            if (identity.getSexuality() != null) {
                config.set(key + ".sexuality", identity.getSexuality().getId());
            }
        }

        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdirs();
            }
            config.save(file);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save identities.yml", e);
        }
    }
}