package com.example.android_api_dota2;

public class Heroes {
    protected String localized_name;
    protected String primary_attr;
    protected String attack_type;
    protected String[] roles;
    protected String img;

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
