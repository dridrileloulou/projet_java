package com.shottentotten.controller;

import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Paquet;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Section;
import com.shottentotten.ai.Ai;
import com.shottentotten.ai.Ai.Difficulte;
import java.util.Scanner;
import java.util.Arrays;

public class Jeu {

    private Plateau plateau;
    private Joueur[] joueurs;
    private Paquet piocheClan;
    private int tourActuel;
    private boolean joueur1IA; // true si le joueur 1 est IA
    private boolean joueur2IA; // true si le joueur 2 est IA
    private Difficulte difficulte1; // Difficulté de l'IA 1
    private Difficulte difficulte2; // Difficulté de l'IA 2

    public Jeu() {
        this(false, false); // Par défaut, mode 1v1
    }

    public Jeu(boolean modeIA) {
        this(false, modeIA); // Ancien constructeur pour compatibilité
    }

    public Jeu(boolean joueur1IA, boolean joueur2IA) {
        this(joueur1IA, joueur2IA, Difficulte.MOYEN, Difficulte.MOYEN);
    }

    public Jeu(boolean joueur1IA, boolean joueur2IA, Difficulte difficulte1, Difficulte difficulte2) {
        this.plateau = new Plateau();
        this.joueurs = new Joueur[2];
        this.piocheClan = new Paquet();
        this.joueur1IA = joueur1IA;
        this.joueur2IA = joueur2IA;
        this.difficulte1 = difficulte1;
        this.difficulte2 = difficulte2;
        
        // Créer les joueurs avec les bons noms
        joueurs[0] = new Joueur(joueur1IA ? "IA 1" : "Joueur 1", 2);  // Joueur humain/IA 1 = Joueur 2
        joueurs[1] = new Joueur(joueur2IA ? "IA 2" : "Joueur 2", 1);  // Joueur humain/IA 2 = Joueur 1
        
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
        
        // Annoncer le vainqueur
        this.annoncerVainqueur(resultat);
    }

    public int verifierFinPartie() {
        int result = this.plateau.verifierControleSections();
        switch (result) {
            case 51: this.joueurs[0].ajouterScore(5); return 15; // 1 = joueur 1, 5 = points
            case 52: this.joueurs[1].ajouterScore(5); return 25; // 2 = joueur 2, 5 = points
            case 31: this.joueurs[0].ajouterScore(3); return 13; // 1 = joueur 1, 3 = points
            case 32: this.joueurs[1].ajouterScore(3); return 23; // 2 = joueur 2, 3 = points
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

        int indexCarte;
        int indexSection;
        
        // Vérifier si le joueur actuel est une IA
        boolean estIA = (j.getNum_joueur() == 1 && joueur1IA) || (j.getNum_joueur() == 2 && joueur2IA);
        
        if (estIA) {
            System.out.println("\n[" + j.getNom() + " réfléchit...]");
            try { Thread.sleep(1000); } catch (InterruptedException e) {} // Pause pour lisibilité
            
            // Choisir la difficulté selon le joueur
            Difficulte difficulteJoueur = (j.getNum_joueur() == 1) ? difficulte1 : difficulte2;
            
            indexCarte = Ai.choisirCarte(j, difficulteJoueur);
            indexSection = Ai.choisirSection(this.plateau, difficulteJoueur, j.getNum_joueur());
            
            // Vérifier que la carte n'est pas null
            while (main[indexCarte] == null) {
                indexCarte = Ai.choisirCarte(j, difficulteJoueur);
            }
            
            System.out.println("[" + j.getNom() + " joue la carte " + indexCarte + " en section " + indexSection + "]");
        } else {
            // Sinon, demander au joueur humain
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
        System.out.println("\n\n╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     🎉 FIN DE LA PARTIE 🎉                      ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝\n");
        
        int numVainqueur = resultat / 10; // Première décimale = numéro du joueur
        int pointsGagnes = resultat % 10; // Deuxième décimale = points gagnés
        
        String nomVainqueur = joueurs[numVainqueur - 1].getNom();
        
        System.out.println("🏆 VAINQUEUR : " + nomVainqueur);
        System.out.println("\n📊 Détails de la victoire :");
        
        if (pointsGagnes == 5) {
            System.out.println("   ✓ A pris le contrôle de 5 sections !");
            System.out.println("   ✓ Points gagnés : +5");
        } else if (pointsGagnes == 3) {
            System.out.println("   ✓ A pris le contrôle de 3 sections consécutives !");
            System.out.println("   ✓ Points gagnés : +3");
        }
        
        System.out.println("\n📈 Scores finaux :");
        System.out.println("   " + joueurs[0].getNom() + " : " + joueurs[0].getScore() + " points");
        System.out.println("   " + joueurs[1].getNom() + " : " + joueurs[1].getScore() + " points");
        
        System.out.println("\n══════════════════════════════════════════════════════════════════\n");
    }
}
