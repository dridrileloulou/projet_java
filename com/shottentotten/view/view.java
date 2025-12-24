package com.shottentotten.view;

import com.shottentotten.model.*;

public class view {

    private static String getSymbole(String couleur) {
        if (couleur == null) return "?";
        switch (couleur.toLowerCase()) {
            case "rouge": return "♥";
            case "bleu": return "♠";
            case "vert": return "♣";
            case "jaune": return "♦";
            case "violet": return "★";
            case "orange": return "●";
            default: return "?";
        }
    }

    private static String formatterCartesDetaillees(Carte[] cartes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cartes.length; i++) {
            if (cartes[i] != null) {
                sb.append(cartes[i].get_valeur()).append(getSymbole(cartes[i].get_couleur()));
            } else {
                sb.append("-");
            }
            if (i < cartes.length - 1) sb.append(" ");
        }
        return sb.toString();
    }

    private static String afficherBorne(Boolean borne, int controle) {
        if (!Boolean.TRUE.equals(borne)) {
            if (controle == 1) return " J1[*] ";
            if (controle == 2) return " [*]J2 ";
            return "  ---  ";
        }
        return "  ---  ";
    }

    public static void afficherTourHeader(String nom) {
        System.out.println("\n" + "═".repeat(80));
        System.out.println("║ Tour de " + nom + " ".repeat(1) + "".repeat(0));
        System.out.println("═".repeat(80));
    }

    public static void afficherPlateau(Plateau plateau) {
        System.out.println("\n┌────┬──────────────────┬──────────┬──────────────────┐");
        System.out.println("│Sec │   Joueur 1       │   Borne  │   Joueur 2       │");
        System.out.println("├────┼──────────────────┼──────────┼──────────────────┤");

        for (int i = 0; i < 9; i++) {
            Section section = plateau.get_section(i);
            Carte[] cartes_j1 = section.getCarte_j1();
            Carte[] cartes_j2 = section.getCarte_j2();
            Boolean borne = section.getBorne();
            int controle = section.calculer_force();

            String cartes1Str = String.format("%-16s", formatterCartesDetaillees(cartes_j1));
            String cartes2Str = String.format("%-16s", formatterCartesDetaillees(cartes_j2));
            String borneStr = afficherBorne(borne, controle);

            System.out.printf("│ %d  │ %s │ %s  │ %s │\n", i, cartes1Str, borneStr, cartes2Str);
        }

        System.out.println("└────┴──────────────────┴──────────┴──────────────────┘");
    }

    public static void afficherMainTable(Joueur j) {
        Carte[] main = j.getMain();
        System.out.println("\nVotre main :");
        System.out.println("┌────────┬────────┬────────┬────────┬────────┬────────┐");
        System.out.println("│ Idx 0  │ Idx 1  │ Idx 2  │ Idx 3  │ Idx 4  │ Idx 5  │");
        System.out.println("├────────┼────────┼────────┼────────┼────────┼────────┤");
        StringBuilder ligne = new StringBuilder("│");
        for (int i = 0; i < main.length; i++) {
            if (main[i] != null) {
                String valeur = String.valueOf(main[i].get_valeur());
                String symbole = getSymbole(main[i].get_couleur());
                ligne.append(String.format(" %-5s  │", valeur + symbole));
            } else {
                ligne.append("   -   │");
            }
        }
        System.out.println(ligne.toString());
        System.out.println("└────────┴────────┴────────┴────────┴────────┴────────┘");
    }

    public static void annoncerVainqueur(int resultat, Joueur[] joueurs, Plateau plateau) {
        System.out.println("\n\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     FIN DE LA PARTIE                           ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");

        String nomVainqueur = "";
        String details = "";
        if (resultat == 15) {
            nomVainqueur = joueurs[0].getNom();
            details = "   ✓ A pris le contrôle de 5 sections !\n   ✓ Points gagnés : +5";
        } else if (resultat == 25) {
            nomVainqueur = joueurs[1].getNom();
            details = "   ✓ A pris le contrôle de 5 sections !\n   ✓ Points gagnés : +5";
        } else if (resultat == 13) {
            nomVainqueur = joueurs[0].getNom();
            details = "   ✓ A pris le contrôle de 3 sections consécutives !\n   ✓ Points gagnés : +3";
        } else if (resultat == 23) {
            nomVainqueur = joueurs[1].getNom();
            details = "   ✓ A pris le contrôle de 3 sections consécutives !\n   ✓ Points gagnés : +3";
        } else if (resultat >= 10 && resultat < 20) {
            nomVainqueur = joueurs[0].getNom();
            int nb = resultat - 10;
            details = "   ✓ A pris le contrôle de " + nb + " sections\n   ✓ Points gagnés : +" + nb;
        } else if (resultat >= 20 && resultat < 30) {
            nomVainqueur = joueurs[1].getNom();
            int nb = resultat - 20;
            details = "   ✓ A pris le contrôle de " + nb + " sections\n   ✓ Points gagnés : +" + nb;
        }

        System.out.println("VAINQUEUR : " + nomVainqueur);
        System.out.println("\nDétails de la victoire :");
        System.out.println(details);
        System.out.println("\nPLATEAU FINAL :");
        afficherPlateau(plateau);
        System.out.println("\nScores finaux :");
        System.out.println("   " + joueurs[0].getNom() + " : " + joueurs[0].getScore() + " points");
        System.out.println("   " + joueurs[1].getNom() + " : " + joueurs[1].getScore() + " points");

        System.out.println("\n══════════════════════════════════════════════════════════════════\n");
    }
}