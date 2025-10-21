package com.chat.serveur;

import java.util.Objects;

public class Invitation {
    private String aliasHote;
    private String aliasInvite; //test

    public Invitation(String aliasHote, String aliasInvite) {
        this.aliasHote = aliasHote;
        this.aliasInvite = aliasInvite;
    }

    public boolean contientAlias(String alias1, String alias2){
        return aliasHote.equalsIgnoreCase(alias1) && aliasInvite.equalsIgnoreCase(alias2) ;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Invitation that = (Invitation) o;
        return Objects.equals(aliasHote, that.aliasInvite) && Objects.equals(aliasInvite, that.aliasHote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasHote, aliasInvite);
    }
}
