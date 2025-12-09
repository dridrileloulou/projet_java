package com.shottentotten.controller;

import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Paquet;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Section;
import java.util.Scanner;
import java.util.Arrays;

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
            joueurs[i] = new Joueur("Joueur" + (i + 1), i + 1);
        }
        this.tourActuel = 0;
    }

    public Joueur[] getJoueurs() {
        return this.joueurs;
    }

    public void demarrerPartie() {
        this.piocheClan = new Paquet();
        this.piocheClan.melanger();

        Carte[] main1 = new Carte[6];
        Carte[] main2 = new Carte[6];
        for (int i = 0; i < 6; i++) {
            main1[i] = piocheClan.piocher();
            main2[i] = piocheClan.piocher();
        }
        joueurs[0].setMain(main1);
        joueurs[1].setMain(main2);

        while (this.verifierFinPartie() == 0) {
            this.jouerTour(this.joueurs[tourActuel]);
        }
    }

    public int verifierFinPartie() {
        int result = this.plateau.verifierControleSections();
        switch (result) {
            case 51: this.joueurs[0].ajouterScore(5); return 1;
            case 52: this.joueurs[1].ajouterScore(5); return 2;
            case 31: this.joueurs[0].ajouterScore(3); return 1;
            case 32: this.joueurs[1].ajouterScore(3); return 2;
            default: return 0;
        }
    }

    public void jouerTour(Joueur j) {
        System.out.println("\n========== Tour de " + j.getNom() + " ==========");

        System.out.println("Plateau :");
        System.out.println("Cartes joueur 1    ||   Borne   ||   Cartes joueur 2");
        Carte[] cartes_j1 = new Carte[3];
        Carte[] cartes_j2 = new Carte[3];
        Boolean borne;
        for (int i=0; i<9; i++){
            cartes_j1 = this.plateau.get_section(i).getCarte_j1();
            cartes_j2 = this.plateau.get_section(i).getCarte_j2();
            borne = this.plateau.get_section(i).getBorne();
            System.out.println(Arrays.toString(cartes_j1) + " || " + borne + " || " + Arrays.toString(cartes_j2));
        }

        Carte[] main = j.getMain();
        System.out.println("Votre main :");
        for (int i = 0; i < main.length; i++) {
            System.out.println(i + " : " + main[i]);
        }

        Scanner scanner = new Scanner(System.in);
        int indexCarte;
        while (true) {
            System.out.print("Choisissez la carte à jouer (index 0 à 5) : ");
            if (!scanner.hasNextInt()) {
                scanner.next();
                System.out.println("Veuillez entrer un nombre !");
                continue;
            }
            indexCarte = scanner.nextInt();
            if (indexCarte < 0 || indexCarte >= main.length) { System.out.println("Index invalide !"); continue; }
            if (main[indexCarte] == null) { System.out.println("Cette case est vide !"); continue; }
            break;
        }

        Carte carteChoisie = main[indexCarte];
        int indexSection;
        while (true) {
            System.out.print("Choisissez la section (index 0 à 8) : ");
            if (!scanner.hasNextInt()) { scanner.next(); System.out.println("Veuillez entrer un nombre !"); continue; }
            indexSection = scanner.nextInt();
            if (indexSection < 0 || indexSection > 8) { System.out.println("Index invalide !"); continue; }
            break;
        }

        Section sectionChoisie = plateau.get_section(indexSection);
        j.jouerCarte(carteChoisie, sectionChoisie);

        if (!piocheClan.estVide()) { j.piocherCarte(piocheClan); }

        tourActuel = (tourActuel + 1) % joueurs.length;
    }
}
