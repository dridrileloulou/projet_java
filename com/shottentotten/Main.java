package com.shottentotten;

import com.shottentotten.controller.Jeu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans ShottenTotten !");
        System.out.println("\nChoisissez le mode de jeu :");
        System.out.println("1 - Joueur vs Joueur (1v1)");
        System.out.println("2 - Joueur vs IA (1vIA)");
        System.out.println("3 - IA vs IA (IAvsIA)");
        System.out.print("Votre choix (1, 2 ou 3) : ");
        
        Scanner scanner = new Scanner(System.in);
        int choix = 0;
        boolean joueur1IA = false;
        boolean joueur2IA = false;
        
        while (true) {
            if (scanner.hasNextInt()) {
                choix = scanner.nextInt();
                if (choix == 1) {
                    joueur1IA = false;
                    joueur2IA = false;
                    System.out.println("\nMode 1v1 sélectionné !");
                    break;
                } else if (choix == 2) {
                    joueur1IA = false;
                    joueur2IA = true;
                    System.out.println("\nMode 1vIA sélectionné !");
                    break;
                } else if (choix == 3) {
                    joueur1IA = true;
                    joueur2IA = true;
                    System.out.println("\nMode IAvsIA sélectionné !");
                    break;
                } else {
                    System.out.print("Veuillez entrer 1, 2 ou 3 : ");
                }
            } else {
                scanner.next();
                System.out.print("Entrée invalide ! Veuillez entrer 1, 2 ou 3 : ");
            }
        }
        
        Jeu jeu = new Jeu(joueur1IA, joueur2IA);
        jeu.demarrerPartie();
    }
    
}
