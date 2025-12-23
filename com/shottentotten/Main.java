package com.shottentotten;

import com.shottentotten.controller.Jeu;
import com.shottentotten.ai.Ai.Difficulte;
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
        Difficulte difficulte1 = Difficulte.MOYEN;
        Difficulte difficulte2 = Difficulte.MOYEN;
        
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
                    difficulte2 = choisirDifficulte(scanner, "l'IA");
                    break;
                } else if (choix == 3) {
                    joueur1IA = true;
                    joueur2IA = true;
                    System.out.println("\nMode IAvsIA sélectionné !");
                    difficulte1 = choisirDifficulte(scanner, "IA 1");
                    difficulte2 = choisirDifficulte(scanner, "IA 2");
                    break;
                } else {
                    System.out.print("Veuillez entrer 1, 2 ou 3 : ");
                }
            } else {
                scanner.next();
                System.out.print("Entrée invalide ! Veuillez entrer 1, 2 ou 3 : ");
            }
        }
        
        Jeu jeu = new Jeu(joueur1IA, joueur2IA, difficulte1, difficulte2);
        jeu.demarrerPartie();
    }
    
    /**
     * Demande à l'utilisateur de choisir le niveau de difficulté
     */
    public static Difficulte choisirDifficulte(Scanner scanner, String nomIA) {
        System.out.println("\nChoisissez la difficulté pour " + nomIA + " :");
        System.out.println("1 - Facile (cartes aléatoires)");
        System.out.println("2 - Moyen (cartes faibles)");
        System.out.println("3 - Difficile (cartes fortes)");
        System.out.print("Votre choix (1, 2 ou 3) : ");
        
        int difficulteChoix = 0;
        while (true) {
            if (scanner.hasNextInt()) {
                difficulteChoix = scanner.nextInt();
                if (difficulteChoix == 1) {
                    System.out.println("Difficulté FACILE sélectionnée pour " + nomIA);
                    return Difficulte.FACILE;
                } else if (difficulteChoix == 2) {
                    System.out.println("Difficulté MOYEN sélectionnée pour " + nomIA);
                    return Difficulte.MOYEN;
                } else if (difficulteChoix == 3) {
                    System.out.println("Difficulté DIFFICILE sélectionnée pour " + nomIA);
                    return Difficulte.DIFFICILE;
                } else {
                    System.out.print("Veuillez entrer 1, 2 ou 3 : ");
                }
            } else {
                scanner.next();
                System.out.print("Entrée invalide ! Veuillez entrer 1, 2 ou 3 : ");
            }
        }
    }
}
