package net.saturn.queerTab.identity;

import java.util.UUID;

/**
 * Holds a single player's chosen pronoun and/or sexuality preset.
 * Either field may be null — a player can set one, both, or neither.
 */
public class PlayerIdentity {

    private final UUID uuid;
    private PronounPreset pronoun;
    private SexualityPreset sexuality;

    public PlayerIdentity(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PronounPreset getPronoun() {
        return pronoun;
    }

    public void setPronoun(PronounPreset pronoun) {
        this.pronoun = pronoun;
    }

    public SexualityPreset getSexuality() {
        return sexuality;
    }

    public void setSexuality(SexualityPreset sexuality) {
        this.sexuality = sexuality;
    }

    public boolean isEmpty() {
        return pronoun == null && sexuality == null;
    }
}
