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

    public int verifierControleSections() {

        int[] tab = new int[9];
        for (int i = 0; i < 9; i++) {
            tab[i] = sections[i].calculer_force();
        }

        // --- Victoire 5 sections ---
        int j1 = 0, j2 = 0;
        for (int r : tab) {
            if (r == 1) j1++;
            if (r == 2) j2++;
        }

        if (j1 >= 5) return 51;
        if (j2 >= 5) return 52;

        // --- Victoire 3 alignées ---
        int alignJ1 = 0, alignJ2 = 0;

        for (int r : tab) {
            if (r == 1) {
                alignJ1++;
                alignJ2 = 0;
            } else if (r == 2) {
                alignJ2++;
                alignJ1 = 0;
            } else {
                alignJ1 = 0;
                alignJ2 = 0;
            }

            if (alignJ1 == 3) return 31;
            if (alignJ2 == 3) return 32;
        }

        return 0;
    }

}