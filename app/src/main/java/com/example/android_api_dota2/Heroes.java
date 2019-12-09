package com.example.android_api_dota2;

public class Heroes {
    String localized_name;
    String name;

    //Url des images des personnages
    public String getImgUrl() {
        // lien de l'image avec le nom au bon format
        System.out.println(localized_name.replace(" ","_"));
        return "https://api.opendota.com/apps/dota2/images/heroes/" + localized_name.replace(" ","_").replace("-","").toLowerCase() + "_full.png";
    }

    //Url des imagees de leurs statistiques
    public String getStatsUrl() {
        return "https://api.opendota.com/apps/dota2/images/heroes/" + localized_name.replace(" ","_").replace("-","").toLowerCase() + "_full.png";
    }

    //Url des imagees de leurs statistiques
    public String getName() {
        name = name.replace("npc_dota_hero_","").replace("_"," ");
        // un nom de heros n'est jamais compose de plus de deux parties
        char[] tab_name = name.toCharArray();
        tab_name[0] = Character.toUpperCase(tab_name[0]);
        int index = name.indexOf(" ") + 1;
        if (index != -1)
            tab_name[index] = Character.toUpperCase(tab_name[index]);
        return new String(tab_name);
    }
}
