package com.example.android_api_dota2;

public class Heroes {
    String localized_name;
    String primary_attr;
    String attack_type;
    String[] roles;
    String img;

    // Url des images des images heros
    public String getImgUrl() {
        // lien de l'image avec le nom au bon format
        return "https://api.opendota.com" + img;
    }

    public String getAttackType() {
        if (attack_type.equals("Melee"))
            return "Mêlée";
        else return "Distance";
    }

    // Roles des heros dans une partie (ex: carry nuker)
    public String getRoles() {
        String strRoles = "";
        for (String role : roles)
            strRoles += role + " ";
        return strRoles;
    }
}
