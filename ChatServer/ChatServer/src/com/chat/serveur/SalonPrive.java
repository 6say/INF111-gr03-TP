package com.chat.serveur;

import java.util.Objects;

public class SalonPrive{

    private String aliasHote, aliasInvite;

    /**
     On fait un copié-collé de la classe invitation pour les besoins du TP
     */
    public SalonPrive(String aliasHote, String aliasInvite) {
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
        if (this == o) return true;
        if (!(o instanceof SalonPrive that)) return false;
        return Objects.equals(aliasHote, that.aliasInvite) && Objects.equals(aliasInvite, that.aliasHote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasHote, aliasInvite);
    }

}
