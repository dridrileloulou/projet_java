package com.shottentotten.model;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Paquet;
import com.shottentotten.model.Section;

public class Joueur {
    private int score;
    private String nom;
    private Carte[] main;
    private int num_joueur;

    public Joueur(String nom, int num) {
        this.nom = nom;
        this.score = 0;
        this.main = new Carte[6];
        this.num_joueur = num;
    }
    
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom; 
    }

    public Carte[] getMain() {
        return main;
    }
    public void setMain(Carte[] main) {
        this.main = main;
    }

    public int getNum_joueur() {
        return num_joueur;
    }
    public void setNum_joueur(int num_joueur) {
        this.num_joueur = num_joueur;
    }   

    public void piocherCarte(Paquet pioche) {
        Carte cartePiochee = pioche.piocher();
        for (int i = 0; i < main.length; i++) {
            if (main[i] == null) {
                main[i] = cartePiochee;
                break;
            }
        }    
    }

    public void jouerCarte(Carte carteJouee, Section s){
        for (int i = 0; i < main.length; i++) {
            if (main[i] == carteJouee) {
                s.ajouterCarte(carteJouee, this);
                main[i] = null;
                break;
            }
        }


    }

    public void ajouterScore(int point){
        this.score += point;
    }

    



}