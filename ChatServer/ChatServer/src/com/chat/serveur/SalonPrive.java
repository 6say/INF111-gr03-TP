package com.chat.serveur;

import java.util.Objects;

public class SalonPrive extends Serveur {

    private String aliasHote, aliasInvite;

    /**
     On fait un copié-collé de la classe invitation pour les besoins du TP
     */
    public SalonPrive(int port, String aliasHote, String aliasInvite) {
        //on récupère l'alias hôte, l'alias invité et le port du serveur pour l'invitation
        super(port);
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
