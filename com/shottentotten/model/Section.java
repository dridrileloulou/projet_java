package com.shottentotten.model;
import java.util.Arrays;
import com.shottentotten.model.Carte;
import com.shottentotten.model.Joueur;

public class Section{
    private Boolean borne;
    private Carte[] cartes_j1;
    private Carte[] cartes_j2;

    public Section(){
        this.borne = true;
        this.cartes_j1 = new Carte[3];
        this.cartes_j2 = new Carte[3];
    }

    public Section(Boolean borne, Carte[] cartes_j1, Carte[] cartes_j2){
        this.borne = borne;
        this.cartes_j1 = cartes_j1;
        this.cartes_j2 = cartes_j2;
    }

    public void ajouterCarte(Carte c, Joueur j){
        if (j.getNum_joueur() == 1){
            for (int i = 0; i<3; i++){
                if (cartes_j1[i] == null){
                    cartes_j1[i] = c;
                    return;
                }
            }
            System.out.println("Plus de place");
        }
        else if (j.getNum_joueur() == 2){
            for (int i = 0; i<3; i++){
                if (cartes_j2[i] == null){
                    cartes_j2[i] = c;
                    return;
                }
            }
            System.out.println("Plus de place");
        }
        else {
            System.out.println("Joueur inconnu");
        }
    }

    public int calculer_force(){

        int[] v1 = new int[3];
        String[] c1 = new String[3];
        int[] v2 = new int[3];
        String[] c2 = new String[3];
        int score_j1 = 0;
        int score_j2 = 0;
         
        for (int i=0; i<3; i++){
            if (cartes_j1[i] == null){
                return 0;
            }
            v1[i] = cartes_j1[i].get_valeur();
            c1[i] = cartes_j1[i].get_couleur();
        }
        for (int i=0; i<3; i++){
            if (cartes_j2[i] == null){
                return 0;
            }
            v2[i] = cartes_j2[i].get_valeur();
            c2[i] = cartes_j2[i].get_couleur();
        }
        Arrays.sort(v1);
        Arrays.sort(v2);

        //vérifier les combos (joueur 1)
        //suite couleur
        if(c1[0].equalsIgnoreCase(c1[1]) &&  c1[0].equalsIgnoreCase(c1[2])){
            if(v1[1] == v1[0]+1 && v1[2] == v1[0]+2){
                score_j1 = 400;
            }
        }
        //brelan
        else if(v1[0] == v1[1] && v1[0] == v1[2]){
            score_j1 = 300;
        }
        //couleur
        else if(c1[0].equalsIgnoreCase(c1[1]) &&  c1[0].equalsIgnoreCase(c1[2])){
            score_j1 = 200;
        }
        //suite
        else if(v1[1] == v1[0]+1 && v1[2] == v1[0]+2){
            score_j1 = 100;
        }
        //somme
        score_j1 = score_j1 + v1[0] + v1[1] + v1[2];


        //vérifier les combos (joueur 2)
        //suite couleur
        if(c2[0].equalsIgnoreCase(c2[1]) &&  c2[0].equalsIgnoreCase(c2[2])){
            if(v2[1] == v2[0]+1 && v2[2] == v2[0]+2){
                score_j2 = 400;
            }
        }
        //brelan
        else if(v2[0] == v2[1] && v2[0] == v2[2]){
            score_j2 = 300;
        }
        //couleur
        else if(c2[0].equalsIgnoreCase(c2[1]) &&  c2[0].equalsIgnoreCase(c2[2])){
            score_j2 = 200;
        }
        //suite
        else if(v2[1] == v2[0]+1 && v2[2] == v2[0]+2){
            score_j2 = 100;
        }
        //somme
        score_j2 = score_j2 + v2[0] + v2[1] + v2[2];

        //regarder qui a gagné
        if(score_j1 > score_j2){
            this.placer_borne(1);
            return 1;
        }
        else if(score_j1 < score_j2){
            this.placer_borne(2);
            return 2;
        }
        else{
            return 0;
        }
    }

    public void placer_borne(int i){
        if (i == 1){
            this.borne = false;
        }
        else if (i == 2){
            this.borne = false;
        }
        else {
            System.out.println("Veuillez appeler la fonction placer_borne avec 1 ou 2 en argument");
        }
    }
}