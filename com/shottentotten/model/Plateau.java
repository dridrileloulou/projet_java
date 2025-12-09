package com.shottentotten.model;
import com.shottentotten.model.Section;
import com.shottentotten.model.Joueur;

public class Plateau{
    private Section[] sections;
    
    public Plateau(){
        sections = new Section[9];
        for(int i=0; i<9; i++){
            sections[i] = new Section();
        }
    }

    public Section get_section(int index){
        return sections[index];
    }

    public int verifierControleSections(){
        int tab_section[9] = new int[9];
        for(int i = 0; i < 9; i++){
            tab_section[i] = sections[i].calculer_force();
        }
        int compteur_j1_1 = 0;
        int compteur_j2_1 = 0;
        for(int i = 0; i<9;i++){
            if(tab_section[i] == 1)
                compteur_j1_1 += 1;
            if(tab_section[i] == 2)
                compteur_j2_1 += 1;
        }
        if(compteur_j1_1 >= 5 )
            return 51;
        if(compteur_j2_1 >= 5 )
            return 52;

        int compteur_j1_2 = 0;
        int compteur_j2_2 = 0;
        for(int i = 0; i<9;i++){
            if(compteur_j1_2 == 3)
                return 31;
            if(compteur_j2_2 == 3)
                return 32;
            switch (tab_section[i]){
                case 0:
                    compteur_j1_2 = 0;
                    compteur_j2_2 = 0;
                    break;
                case 1:
                    compteur_j1_1 +=1;
                    compteur_j2_2 = 0;
                    break;
                case 2:
                    compteur_j1_2 = 0;
                    compteur_j2_2 += 1;
                    break;
                default : 
                    compteur_j1_2 = 0;
                    compteur_j2_2 = 0;
            }
        }
        return 0;

    }
}