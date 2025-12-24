package com.shottentotten.ai;

import com.shottentotten.model.Carte;
import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Section;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ai {

    private static final Random RAND = new Random();

    public static int choisirCarte(Joueur joueur) {
        Carte[] main = joueur.getMain();
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < main.length; i++) {
            if (main[i] != null) indices.add(i);
        }
        if (indices.isEmpty()) return 0;
        return indices.get(RAND.nextInt(indices.size()));
    }

    public static int choisirSection(Plateau plateau, int numJoueur) {
        List<Integer> disponibles = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            Section s = plateau.get_section(i);
            if (sectionDisponible(s, numJoueur)) disponibles.add(i);
        }
        if (disponibles.isEmpty()) return 0;
        return disponibles.get(RAND.nextInt(disponibles.size()));
    }

    private static boolean sectionDisponible(Section section, int numJoueur) {
        if (section == null) return false;
        if (numJoueur == 1) {
            Carte[] cartes = section.getCarte_j1();
            int count = 0;
            for (Carte c : cartes) if (c != null) count++;
            return count < 3;
        } else {
            Carte[] cartes = section.getCarte_j2();
            int count = 0;
            for (Carte c : cartes) if (c != null) count++;
            return count < 3;
        }
    }
}
