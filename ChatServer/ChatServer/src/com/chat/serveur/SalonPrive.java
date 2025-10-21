package com.chat.serveur;

import java.util.Objects;

public class SalonPrive {
    private String aliasHote;
    private String aliasInvite; //test

    public SalonPrive(String aliasHote, String aliasInvite) {
        this.aliasHote = aliasHote;
        this.aliasInvite = aliasInvite;
    }

    public boolean contientAlias(String alias1, String alias2){
        return aliasHote.equalsIgnoreCase(alias1) && aliasInvite.equalsIgnoreCase(alias2) ;
    }

    public String getAliasHote(){
        return aliasHote;
    }

    public String getAliasInvite(){
        return aliasInvite;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalonPrive that = (SalonPrive) o;
        return (Objects.equals(aliasHote, that.aliasInvite) && Objects.equals(aliasInvite, that.aliasHote)) || (Objects.equals(aliasHote, that.aliasHote) && Objects.equals(aliasInvite, that.aliasInvite));
    }

    @Override
    public int hashCode() {
        return Objects.hash(aliasHote, aliasInvite);
    }




}
