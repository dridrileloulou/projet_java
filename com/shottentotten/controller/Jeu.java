package com.shottentotten.controller;

import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Paquet;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Section;
import java.util.Scanner;

public class Jeu {

    private Plateau plateau;
    private Joueur[] joueurs;
    private Paquet piocheClan;
    private int tourActuel;

    public Jeu() {
        this.plateau = new Plateau();
        this.joueurs = new Joueur[2];
        this.piocheClan = new Paquet();

        for (int i = 0; i < 2; i++) {
            joueurs[i] = new Joueur("Joueur" + (i + 1), i+1);
        }

        this.tourActuel = 0;
    }

    public void demarrerPartie() {
        this.piocheClan = new Paquet();  // recrée le paquet
        this.piocheClan.melanger();

        // Distribution des mains
        Carte[] main1 = new Carte[6];
        Carte[] main2 = new Carte[6];

        for (int i = 0; i < 6; i++) {
            main1[i] = piocheClan.piocher();
            main2[i] = piocheClan.piocher();
        }

        joueurs[0].setMain(main1);
        joueurs[1].setMain(main2);
    }

    public void verifierFinPartie() {
        int result = this.plateau.verifierControleSections();

        switch (result) {
            case 51:  // Joueur 1 gagne 5 points
                this.joueurs[0].ajouterScore(5);
                break;

            case 52:  // Joueur 2 gagne 5 points
                this.joueurs[1].ajouterScore(5);
                break;

            case 31:  // Joueur 1 gagne 3 points
                this.joueurs[0].ajouterScore(3);
                break;

            case 32:  // Joueur 2 gagne 3 points
                this.joueurs[1].ajouterScore(3);
                break;

            default:
                // rien, la partie continue
                break;
        }
    }

    public void jouerTour(Joueur j) {

        System.out.println("\n========== Tour de " + j.getNom() + " ==========");

        // 1. Afficher la main du joueur
        Carte[] main = j.getMain();
        System.out.println("Votre main :");
        for (int i = 0; i < main.length; i++) {
            System.out.println(i + " : " + main[i]);
        }

        Scanner scanner = new Scanner(System.in);

        // 2. Choisir une carte (avec validation)
        int indexCarte;

        while (true) {
            System.out.print("Choisissez la carte à jouer (index 0 à 5) : ");

            if (!scanner.hasNextInt()) {
                scanner.next(); // consume invalid input
                System.out.println("Veuillez entrer un nombre !");
                continue;
            }

            indexCarte = scanner.nextInt();

            if (indexCarte < 0 || indexCarte >= main.length) {
                System.out.println("Index invalide !");
                continue;
            }

            if (main[indexCarte] == null) {
                System.out.println("Cette case est vide !");
                continue;
            }

            break;
        }

        Carte carteChoisie = main[indexCarte];

        // 3. Choisir la section (avec validation)
        int indexSection;

        while (true) {
            System.out.print("Choisissez la section (index 0 à 8) : ");

            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Veuillez entrer un nombre !");
                continue;
            }

            indexSection = scanner.nextInt();

            if (indexSection < 0 || indexSection > 8) {
                System.out.println("Index invalide !");
                continue;
            }

            break;
        }

        Section sectionChoisie = plateau.get_section(indexSection);

        // 4. Le joueur joue la carte
        j.jouerCarte(carteChoisie, sectionChoisie);

        // 5. Pioche
        if (!piocheClan.estVide()) {
            j.piocherCarte(piocheClan);
        }

        // 6. Passer au joueur suivant
        tourActuel = (tourActuel + 1) % joueurs.length;
    }


}
