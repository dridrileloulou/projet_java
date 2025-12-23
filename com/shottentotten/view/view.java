package com.shottentotten.view;

import com.shottentotten.model.*;
import com.shottentotten.controller.Jeu;

public class view {
    public static void afficherMain(Joueur joueur) {
        System.out.println("Main de " + joueur.getNom() + " :");
        for (Carte carte : joueur.getMain()) {
            if (carte != null) {
                System.out.println(carte);
            }
        }
    }

    public static void afficherSection(Section section, int index) {
        System.out.println("Section " + index + " :");
        System.out.println("Cartes Joueur 1:");
        for (Carte carte : section.getCarte_j1()) {
            if (carte != null) {
                System.out.println(carte);
            }
        }
        System.out.println("Cartes Joueur 2:");
        for (Carte carte : section.getCarte_j2()) {
            if (carte != null) {
                System.out.println(carte);
            }
        }
    }

    public static void afficherPlateau(Plateau plateau) {
        for (int i = 0; i < 9; i++) {
            afficherSection(plateau.get_section(i), i);
        }
    }
}