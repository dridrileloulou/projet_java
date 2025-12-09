package com.shottentotten;

import com.shottentotten.controller.Jeu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bienvenue dans ShottenTotten !");
        Jeu jeu = new Jeu();
        jeu.demarrerPartie();
    }
}
