package net.saturn.queerTab.identity;

import org.bukkit.ChatColor;

/**
 * Registry of selectable pronoun tags shown in the tab list.
 * Add new entries here — everything else (commands, tab completion,
 * formatting) reads from this enum automatically.
 */
public enum PronounPreset {

    HE_HIM("he_him", "He/Him", ChatColor.AQUA),
    SHE_HER("she_her", "She/Her", ChatColor.LIGHT_PURPLE),
    THEY_THEM("they_them", "They/Them", ChatColor.YELLOW),
    HE_THEY("he_they", "He/They", ChatColor.AQUA),
    SHE_THEY("she_they", "She/They", ChatColor.LIGHT_PURPLE),
    ANY("any", "Any", ChatColor.GREEN),
    ASK("ask", "Ask Me", ChatColor.GRAY);

    private final String id;
    private final String display;
    private final ChatColor color;

    PronounPreset(String id, String display, ChatColor color) {
        this.id = id;
        this.display = display;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public String getDisplay() {
        return display;
    }

    public ChatColor getColor() {
        return color;
    }

    /**
     * Formatted bracket tag, e.g. "[He/Him]" in this preset's color.
     */
    public String getTag() {
        return color + "[" + display + ChatColor.RESET + color + "]";
    }

    public static PronounPreset fromId(String id) {
        for (PronounPreset preset : values()) {
            if (preset.id.equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }
}
