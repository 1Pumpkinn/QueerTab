package net.saturn.queerTab.identity;

import org.bukkit.ChatColor;

import java.util.Objects;

/**
 * A single selectable tag (pronoun or sexuality) loaded from config.yml.
 * Replaces the old hardcoded PronounPreset/SexualityPreset enums so new
 * options can be added purely by editing the config — no code changes
 * or recompiling needed.
 */
public final class Preset {

    private final String id;
    private final String display;
    private final ChatColor color;

    public Preset(String id, String display, ChatColor color) {
        this.id = id.toLowerCase();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Preset preset)) return false;
        return id.equals(preset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}