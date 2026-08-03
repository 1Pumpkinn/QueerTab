package net.saturn.queerTab.identity;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Loads the selectable pronoun and sexuality presets from config.yml.
 *
 * Server owners add/remove/reorder entries under the "pronouns" and
 * "sexualities" sections of config.yml — nothing needs to be recompiled.
 * Call {@link #reload(JavaPlugin)} after editing config.yml (e.g. via
 * /queertab reload) to pick up changes without a server restart.
 */
public class PresetRegistry {

    private static PresetRegistry instance;

    private final Map<String, Preset> pronouns;
    private final Map<String, Preset> sexualities;

    private PresetRegistry(JavaPlugin plugin) {
        this.pronouns = load(plugin, "pronouns");
        this.sexualities = load(plugin, "sexualities");
    }

    public static void init(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        instance = new PresetRegistry(plugin);
    }

    /**
     * Re-reads config.yml from disk and rebuilds both preset lists.
     * Existing players keep their currently-set preset even if it was
     * removed from config (it just won't be selectable again until
     * re-added), so this is always safe to run live.
     */
    public static void reload(JavaPlugin plugin) {
        plugin.reloadConfig();
        instance = new PresetRegistry(plugin);
    }

    private static PresetRegistry get() {
        if (instance == null) {
            throw new IllegalStateException("PresetRegistry.init() was not called");
        }
        return instance;
    }

    public static List<Preset> getPronouns() {
        return List.copyOf(get().pronouns.values());
    }

    public static List<Preset> getSexualities() {
        return List.copyOf(get().sexualities.values());
    }

    public static Preset findPronoun(String id) {
        return id == null ? null : get().pronouns.get(id.toLowerCase());
    }

    public static Preset findSexuality(String id) {
        return id == null ? null : get().sexualities.get(id.toLowerCase());
    }

    private static Map<String, Preset> load(JavaPlugin plugin, String sectionName) {
        Map<String, Preset> result = new LinkedHashMap<>();

        List<Map<?, ?>> entries = plugin.getConfig().getMapList(sectionName);
        if (entries.isEmpty()) {
            plugin.getLogger().warning("No entries found under '" + sectionName
                    + "' in config.yml — that command will have nothing to offer.");
            return result;
        }

        for (Map<?, ?> raw : entries) {
            Object idObj = raw.get("id");
            Object displayObj = raw.get("display");
            Object colorObj = raw.get("color");

            if (idObj == null || displayObj == null) {
                plugin.getLogger().warning("Skipping " + sectionName
                        + " entry missing 'id' or 'display': " + raw);
                continue;
            }

            String id = idObj.toString().toLowerCase();
            String display = displayObj.toString();
            String colorName = colorObj == null ? "WHITE" : colorObj.toString();

            ChatColor color;
            try {
                color = ChatColor.valueOf(colorName.toUpperCase());
            } catch (IllegalArgumentException e) {
                plugin.getLogger().warning("Unknown color '" + colorName + "' for " + sectionName
                        + " entry '" + id + "', defaulting to WHITE. Valid colors: "
                        + colorNames());
                color = ChatColor.WHITE;
            }

            if (result.containsKey(id)) {
                plugin.getLogger().warning("Duplicate " + sectionName + " id '" + id
                        + "' in config.yml — keeping the first one.");
                continue;
            }

            result.put(id, new Preset(id, display, color));
        }

        return result;
    }

    private static String colorNames() {
        List<String> names = new ArrayList<>();
        for (ChatColor color : ChatColor.values()) {
            if (color.isColor()) {
                names.add(color.name());
            }
        }
        Collections.sort(names);
        return String.join(", ", names);
    }
}