package com.shottentotten.model;
import com.shottentotten.model.Carte;
import java.util.Random;

public class Paquet {
    private Carte[] cartes;
    private static final Random random = new Random();


    public Paquet(){
        this.cartes = new Carte[54];
        String[] couleurs = {
            "Rouge",
            "Bleu",
            "Vert",
            "Jaune",
            "Orange",
            "Violet"
        };
        for (int i=0; i<6; i++){
            for (int j=0; j<9; j++){
                cartes[9*i + j] = new Carte(j+1, 0, couleurs[i]);
            }
        }
        this.melanger();
    }
    
    public Carte[] getCartes() {
        return cartes;
    }

    public void setCartes(Carte[] cartes) {
        this.cartes = cartes;
    }

    private int taille_pioche(){
        for(int i = 0; i<this.cartes.length;i++){
            if(this.cartes[i] == null )
                return i;
        }
        return this.cartes.length;
    }

    public void melanger() {
        int t = this.taille_pioche();
        if (t == 0 || t == 1){
            return;
        }
        for (int i = this.taille_pioche()- 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            
            Carte temp = this.cartes[i];
            this.cartes[i] = this.cartes[j];
            this.cartes[j] = temp;
        }
    }



    public Carte piocher() {
        int taille = this.taille_pioche();
        if (taille == 0) return null; // pas de cartes à piocher
        Carte carte_piochee = this.cartes[taille-1];
        cartes[taille-1] = null;
        return carte_piochee;
    }

    public boolean estVide() {
        return this.taille_pioche() == 0;
    }

}