package net.saturn.queerTab.identity;

import org.bukkit.ChatColor;

/**
 * Registry of selectable sexuality/identity tags shown in the tab list.
 * Same pattern as {@link PronounPreset} — add entries here only.
 */
public enum SexualityPreset {

    GAY("gay", "Gay", ChatColor.BLUE),
    LESBIAN("lesbian", "Lesbian", ChatColor.LIGHT_PURPLE),
    BISEXUAL("bisexual", "Bi", ChatColor.DARK_PURPLE),
    PANSEXUAL("pansexual", "Pan", ChatColor.YELLOW),
    ASEXUAL("asexual", "Ace", ChatColor.DARK_GRAY),
    STRAIGHT("straight", "Straight", ChatColor.WHITE),
    QUEER("queer", "Queer", ChatColor.RED),
    ASK("ask", "Ask Me", ChatColor.GRAY);

    private final String id;
    private final String display;
    private final ChatColor color;

    SexualityPreset(String id, String display, ChatColor color) {
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
     * Formatted bracket tag, e.g. "[Pan]" in this preset's color.
     */
    public String getTag() {
        return color + "[" + display + ChatColor.RESET + color + "]";
    }

    public static SexualityPreset fromId(String id) {
        for (SexualityPreset preset : values()) {
            if (preset.id.equalsIgnoreCase(id)) {
                return preset;
            }
        }
        return null;
    }
}
