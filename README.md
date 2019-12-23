# Application Mobile pour l'API de DOTA 2
Application pour connaître les informations (à jour) des personnages du jeu DOTA 2

#Explications techniques
Le projet met en place une architecture MVC. Le modèle est DotaAPI.java, le contrôleur est ControllerAPI.java et le reste gère les vues.

Le code est fait de manière à être le plus possible facile à réutiliser dans d'autres classes.

Les MDC utilisés sont un Toolbar, un NavigationView et un MenuItem.

Les données sont stockées en cache et tout fonctionne avec des fragments, le mainActivity les ajoute/enlève.

La barre de filtres du fragment contenant un recyclerview est lui un fragment fils de celui-ci.

# Aperçu

<img src="https://image.noelshack.com/fichiers/2019/52/2/1577142005-screenshot-20191223-235749.jpg" width="40%">

<img src="https://image.noelshack.com/fichiers/2019/52/2/1577142001-screenshot-20191223-235754.jpg" width="40%">

<img src="https://image.noelshack.com/fichiers/2019/52/1/1577141999-screenshot-20191223-235803.jpg" width="40%">