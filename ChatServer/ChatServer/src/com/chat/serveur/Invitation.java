package com.chat.serveur;

import java.util.Objects;

public class Invitation{

    private String aliasHote, aliasInvite;

    /**
     * Cree un serveur qui va ecouter sur le port specifie.
     */
    public Invitation(String aliasHote, String aliasInvite) {
        //on récupère l'alias hôte, l'alias invité pour l'invitation
        this.aliasHote = aliasHote;
        this.aliasInvite = aliasInvite;
    }

    public String getAliasHote() {
        return aliasHote;
    }

    public String getAliasInvite() {
        return aliasInvite;
    }

    @Override
    public boolean equals(Object o) {
        //on override le equals avec le constructeur fourni avec Ingellij pour comparer les alias nécessaires dans la classe GertionnaireEvenementServeur
        if (this == o) return true;
        if (!(o instanceof Invitation that)) return false;
        return Objects.equals(aliasHote, that.aliasInvite) && Objects.equals(aliasInvite, that.aliasHote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasHote, aliasInvite);
    }
}
