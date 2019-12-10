package com.example.android_api_dota2;

public class Heroes {
    String name;
    String localized_name;

    // Url des images des personnages
    public String getImgUrl() {
        // lien de l'image avec le nom au bon format
        return "https://api.opendota.com/apps/dota2/images/heroes/" + name.replace("npc_dota_hero_","") + "_full.png";
    }

    //Url des imagees de leurs statistiques
    public String getStatsUrl() {
        return "https://api.opendota.com/apps/dota2/images/heroes/" + localized_name.replace(" ","_").replace("-","").toLowerCase() + "_full.png";
    }

    // Nom des heros
    public String getName() {
        return localized_name;
    }
}
