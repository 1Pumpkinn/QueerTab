package net.saturn.queerTab.tab;

import net.saturn.queerTab.identity.Preset;
import net.saturn.queerTab.identity.PlayerIdentity;
import org.bukkit.entity.Player;

/**
 * Turns a PlayerIdentity into the string shown in the tab list, and
 * applies it to a Player.
 *
 * Note: this sets Player#setPlayerListName directly, so if the server
 * also uses a rank/prefix plugin that touches the tab list name (rather
 * than a scoreboard team prefix), the two will fight over it — in that
 * case switch this to append via the player's scoreboard team suffix
 * instead.
 */
public final class TabListFormatter {

    private TabListFormatter() {
    }

    public static String buildName(Player player, PlayerIdentity identity) {
        StringBuilder builder = new StringBuilder(player.getName());

        if (identity != null) {
            Preset pronoun = identity.getPronoun();
            Preset sexuality = identity.getSexuality();

            if (pronoun != null) {
                builder.append(' ').append(pronoun.getTag());
            }
            if (sexuality != null) {
                builder.append(' ').append(sexuality.getTag());
            }
        }

        return builder.toString();
    }

    public static void apply(Player player, PlayerIdentity identity) {
        String name = buildName(player, identity);

        // Tab list names are capped at 16 legacy characters *only* when they
        // must also serve as the scoreboard entry name pre-1.13; on modern
        // Paper the display name is decoupled, but we still guard length to
        // avoid absurdly long lines with many tags.
        if (name.length() > 64) {
            name = name.substring(0, 64);
        }

        player.setPlayerListName(name);
    }
}