package com.shottentotten.controller;

import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Paquet;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Section;
import com.shottentotten.ai.Ai;
import com.shottentotten.view.view;
import java.util.Scanner;
import java.util.Arrays;

public class Jeu {

    private Plateau plateau;
    private Joueur[] joueurs;
    private Paquet piocheClan;
    private int tourActuel;
    private boolean joueur1IA; // true si le joueur 1 est IA
    private boolean joueur2IA; // true si le joueur 2 est IA

    public Jeu() {
        this(false, false); 
    }

    public Jeu(boolean joueur1IA, boolean joueur2IA) {
        this.plateau = new Plateau();
        this.joueurs = new Joueur[2];
        this.piocheClan = new Paquet();
        this.joueur1IA = joueur1IA;
        this.joueur2IA = joueur2IA;
        
        joueurs[0] = new Joueur(joueur1IA ? "IA 1" : "Joueur 1", 1);  // Joueur humain/IA 1 = Joueur 1
        joueurs[1] = new Joueur(joueur2IA ? "IA 2" : "Joueur 2", 2);  // Joueur humain/IA 2 = Joueur 2
        
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

        int resultat = 0;
        while ((resultat = this.verifierFinPartie()) == 0) {
            this.jouerTour(this.joueurs[tourActuel]);
        }
        this.annoncerVainqueur(resultat);
    }

    public int verifierFinPartie() {
        int result = this.plateau.verifierControleSections();
        switch (result) {
            case 51: this.joueurs[0].ajouterScore(5); return 15; // 1 = joueur 1, 5 = points
            case 52: this.joueurs[1].ajouterScore(5); return 25; // 2 = joueur 2, 5 = points
            case 31: this.joueurs[0].ajouterScore(3); return 13; // 1 = joueur 1, 3 = points
            case 32: this.joueurs[1].ajouterScore(3); return 23; // 2 = joueur 2, 3 = points
            default:
                boolean mainsVides = true;
                for (Joueur pj : this.joueurs) {
                    Carte[] m = pj.getMain();
                    for (Carte c : m) { if (c != null) { mainsVides = false; break; } }
                    if (!mainsVides) break;
                }

                if (this.piocheClan.estVide() && mainsVides) {
                    if (result >= 10 && result < 20) {
                        int nb = result - 10;
                        this.joueurs[0].ajouterScore(nb);
                        return result;
                    } else if (result >= 20 && result < 30) {
                        int nb = result - 20;
                        this.joueurs[1].ajouterScore(nb);
                        return result;
                    } else {
                        // Match nul
                        return 30;
                    }
                }

                return 0;
        }
    }

    public void jouerTour(Joueur j) {
        view.afficherTourHeader(j.getNom());
        view.afficherPlateau(this.plateau);
        view.afficherMainTable(j);
        Carte[] main = j.getMain();

        int indexCarte;
        int indexSection;
        
        // Vérifier si le joueur actuel est une IA
        boolean estIA = (j.getNum_joueur() == 1 && joueur1IA) || (j.getNum_joueur() == 2 && joueur2IA);
        
        if (estIA) {
            System.out.println("\n[" + j.getNom() + " réfléchit...]");
            try { Thread.sleep(1000); } catch (InterruptedException e) {} //Pause pour mieux lire 

            indexCarte = Ai.choisirCarte(j);
            indexSection = Ai.choisirSection(this.plateau, j.getNum_joueur());

            // Vérifier que la carte n'est pas null
            while (main[indexCarte] == null) {
                indexCarte = Ai.choisirCarte(j);
            }

            System.out.println("[" + j.getNom() + " joue la carte " + indexCarte + " en section " + indexSection + "]");
        } else {
            Scanner scanner = new Scanner(System.in);
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

            while (true) {
                System.out.print("Choisissez la section (index 0 à 8) : ");
                if (!scanner.hasNextInt()) { scanner.next(); System.out.println("Veuillez entrer un nombre !"); continue; }
                indexSection = scanner.nextInt();
                if (indexSection < 0 || indexSection > 8) { System.out.println("Index invalide !"); continue; }
                
                // Vérifier qu'il y a de la place dans la section
                Section sectionChoisie = plateau.get_section(indexSection);
                Carte[] tempCartes_j1 = sectionChoisie.getCarte_j1();
                Carte[] tempCartes_j2 = sectionChoisie.getCarte_j2();
                
                int nbCartes_j1 = 0;
                int nbCartes_j2 = 0;
                for (Carte c : tempCartes_j1) {
                    if (c != null) nbCartes_j1++;
                }
                for (Carte c : tempCartes_j2) {
                    if (c != null) nbCartes_j2++;
                }
                
                // Vérifier selon le numéro du joueur
                if (j.getNum_joueur() == 1 && nbCartes_j1 >= 3) {
                    System.out.println("Cette section est pleine pour votre côté ! Choisissez une autre section.");
                    continue;
                }
                if (j.getNum_joueur() == 2 && nbCartes_j2 >= 3) {
                    System.out.println("Cette section est pleine pour votre côté ! Choisissez une autre section.");
                    continue;
                }
                
                break;
            }
        }

        Carte carteChoisie = main[indexCarte];
        Section sectionChoisie = plateau.get_section(indexSection);
        j.jouerCarte(carteChoisie, sectionChoisie);

        if (!piocheClan.estVide()) { j.piocherCarte(piocheClan); }

        tourActuel = (tourActuel + 1) % joueurs.length;
    }

    public void annoncerVainqueur(int resultat) {
        view.annoncerVainqueur(resultat, this.joueurs, this.plateau);
    }
    
}
