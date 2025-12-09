package com.shottentotten.model;

public class Carte{

    private int valeur;
    private int type;
    private String couleur;

    public Carte(){
        this.valeur = 0;
        this.type = 0;
        this.couleur = " ";
    }

    public Carte(int valeur, int type, String couleur){
        this.valeur = valeur;
        this.type = type;
        this.couleur = couleur;
    }

    public int get_valeur(){
        return valeur;
    }

    public String get_couleur(){
        return couleur;
    }

    @Override
    public String toString() {
        return valeur + " " + couleur + " (" + type + ")";
    }
}