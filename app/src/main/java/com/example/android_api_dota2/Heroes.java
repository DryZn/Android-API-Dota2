package com.example.android_api_dota2;

public class Heroes {
    String localized_name;

    //Url des images des personnages
    public String getImgUrl() {
        // lien de l'image avec le nom au bon format
        return "https://static1.millenium.org/article_old/images/contenu/actus/DotA2/Portrait_heros/dota2_heros_" + localized_name.replace(" ","_") + ".png";
    }

    //Url des imagees de leurs statistiques
    public String getStatsUrl() {
        return "https://steamcdn-a.akamaihd.net/steam/apps/570/header.jpg?t=1572450795";
        //return MainImageUrl;
    }
}
