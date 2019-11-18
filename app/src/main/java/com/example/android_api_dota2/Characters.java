package com.example.android_api_dota2;

public class Characters {
    String Name;
    String localized_name;
    String MainImageUrl;

    public String getName() {return Name;}

    //Url des images des personnages
    public String getImgUrl() {
        // lien de l'image avec le nom au bon format
        return "https://static1.millenium.org/article_old/images/contenu/actus/DotA2/Portrait_heros/dota2_heros_" + localized_name.replace(" ","_") + ".png";
        /*
        System.out.println(ThumbnailUrl);
        System.out.println(ThumbnailUrl.replace("3","")); // l'image de bayonetta par defaut est la version 3 et c'est une blague des contributeurs
        switch(this.getName()){
            case "Lucas":
                return "https://www.smashbros.com/wiiu-3ds/fr/images/dlc-fighter/image/character-dlc-fighter02.png";
            case "Bayonetta":
                return "https://www.smashbros.com/wiiu-3ds/fr/images/dlc-fighter/image/character-dlc-fighter06.png";
            default:
                return "http://kuroganehammer.com/images/smash4/character/character-bayonetta.png";
                //return ThumbnailUrl;
        }
            */
    }

    //Url des imagees de leurs statistiques
    public String getStatsUrl() {
        System.out.println("IIIIIIIIIICIIIIIIIIIII");
        System.out.println(MainImageUrl);
        return "https://www.smashbros.com/wiiu-3ds/fr/images/dlc-fighter/image/character-dlc-fighter06.png";
        //return MainImageUrl;
    }
}
