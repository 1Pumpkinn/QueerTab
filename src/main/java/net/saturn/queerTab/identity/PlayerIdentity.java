package net.saturn.queerTab.identity;

import java.util.UUID;

/**
 * Holds a single player's chosen pronoun and/or sexuality preset.
 * Either field may be null — a player can set one, both, or neither.
 */
public class PlayerIdentity {

    private final UUID uuid;
    private Preset pronoun;
    private Preset sexuality;

    public PlayerIdentity(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Preset getPronoun() {
        return pronoun;
    }

    public void setPronoun(Preset pronoun) {
        this.pronoun = pronoun;
    }

    public Preset getSexuality() {
        return sexuality;
    }

    public void setSexuality(Preset sexuality) {
        this.sexuality = sexuality;
    }

    public boolean isEmpty() {
        return pronoun == null && sexuality == null;
    }
}