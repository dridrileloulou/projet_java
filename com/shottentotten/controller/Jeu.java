package com.shottentotten.controller;
import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Paquet;

public class Jeu{
    private Plateau plateau;
    private Joueur[] joueurs;
    private int TourActuel;

    public Jeu(){
        this.plateau = new Plateau();
        this.joueurs = new Joueur[2];
        pioche_clan = new Paquet();
        for (int i=0; i<2; i++){
            joueurs[i] = new Joueur("Joueur " + (i+1), i);
        }
        this.TourActuel = 0;

        main1 = new Carte[6];
        main2 = new Carte[6];
        for (int i=0; i<6; i++){
            main1[i] = pioche_clan.piocher();
            main2[i] = pioche_clan.piocher();
        }
        joueurs[0].setMain(main1);
        joueurs[1].setMain(main2);
    }
}