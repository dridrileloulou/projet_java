package com.shottentotten.ai;

import com.shottentotten.model.Carte;
import com.shottentotten.model.Plateau;
import com.shottentotten.model.Joueur;
import com.shottentotten.model.Section;
import java.util.Random;

public class Ai {
    
    // Énumération pour les niveaux de difficulté
    public enum Difficulte {
        FACILE,    // Joue des cartes aléatoires
        MOYEN,     // Joue la plus faible carte
        DIFFICILE  // Joue la meilleure stratégie
    }
    
    /**
     * Choisit la carte en fonction de la difficulté
     * @param joueur Le joueur IA
     * @param difficulte Le niveau de difficulté
     * @return L'index de la carte à jouer
     */
    public static int choisirCarte(Joueur joueur, Difficulte difficulte) {
        switch (difficulte) {
            case FACILE:
                return choisirCarteFacile(joueur);
            case MOYEN:
                return choisirCarteMoyen(joueur);
            case DIFFICILE:
                return choisirCarteDifficile(joueur);
            default:
                return choisirCarteMoyen(joueur);
        }
    }
    
    /**
     * Difficulté FACILE : Choisit une carte aléatoire
     */
    public static int choisirCarteFacile(Joueur joueur) {
        Carte[] main = joueur.getMain();
        Random rand = new Random();
        int indexCarte;
        
        // Trouver une carte non-null aléatoire
        do {
            indexCarte = rand.nextInt(main.length);
        } while (main[indexCarte] == null);
        
        return indexCarte;
    }
    
    /**
     * Difficulté MOYEN : Choisit la plus faible valeur
     */
    public static int choisirCarteMoyen(Joueur joueur) {
        Carte[] main = joueur.getMain();
        int indexCarteMin = -1;
        int valeurMin = Integer.MAX_VALUE;
        
        // Trouver la carte avec la plus faible valeur
        for (int i = 0; i < main.length; i++) {
            if (main[i] != null && main[i].get_valeur() < valeurMin) {
                valeurMin = main[i].get_valeur();
                indexCarteMin = i;
            }
        }
        
        if (indexCarteMin == -1) {
            for (int i = 0; i < main.length; i++) {
                if (main[i] != null) {
                    return i;
                }
            }
            return 0;
        }
        
        return indexCarteMin;
    }
    
    /**
     * Difficulté DIFFICILE : Choisit la plus haute valeur pour dominer
     */
    public static int choisirCarteDifficile(Joueur joueur) {
        Carte[] main = joueur.getMain();
        int indexCarteMax = -1;
        int valeurMax = Integer.MIN_VALUE;
        
        // Trouver la carte avec la plus haute valeur
        for (int i = 0; i < main.length; i++) {
            if (main[i] != null && main[i].get_valeur() > valeurMax) {
                valeurMax = main[i].get_valeur();
                indexCarteMax = i;
            }
        }
        
        if (indexCarteMax == -1) {
            for (int i = 0; i < main.length; i++) {
                if (main[i] != null) {
                    return i;
                }
            }
            return 0;
        }
        
        return indexCarteMax;
    }
    
    /**
     * Choisit la section en fonction de la difficulté
     * @param plateau Le plateau de jeu
     * @param difficulte Le niveau de difficulté
     * @param numJoueur Le numéro du joueur (1 ou 2)
     * @return L'index de la section à jouer
     */
    public static int choisirSection(Plateau plateau, Difficulte difficulte, int numJoueur) {
        switch (difficulte) {
            case FACILE:
                return choisirSectionFacile(plateau, numJoueur);
            case MOYEN:
                return choisirSectionMoyen(plateau, numJoueur);
            case DIFFICILE:
                return choisirSectionDifficile(plateau, numJoueur);
            default:
                return choisirSectionMoyen(plateau, numJoueur);
        }
    }
    
    /**
     * Vérifie si une section a de la place pour une nouvelle carte du joueur spécifié
     */
    private static boolean sectionDisponible(Section section, int numJoueur) {
        if (numJoueur == 1) {
            Carte[] cartes_j1 = section.getCarte_j1();
            int nbCartes_j1 = 0;
            for (Carte c : cartes_j1) {
                if (c != null) nbCartes_j1++;
            }
            return nbCartes_j1 < 3;
        } else {
            Carte[] cartes_j2 = section.getCarte_j2();
            int nbCartes_j2 = 0;
            for (Carte c : cartes_j2) {
                if (c != null) nbCartes_j2++;
            }
            return nbCartes_j2 < 3;
        }
    }
    
    /**
     * Compte le nombre de cartes du joueur dans une section
     */
    private static int compterCartesSection(Section section, int numJoueur) {
        if (numJoueur == 1) {
            Carte[] cartes_j1 = section.getCarte_j1();
            int nbCartes = 0;
            for (Carte c : cartes_j1) {
                if (c != null) nbCartes++;
            }
            return nbCartes;
        } else {
            Carte[] cartes_j2 = section.getCarte_j2();
            int nbCartes = 0;
            for (Carte c : cartes_j2) {
                if (c != null) nbCartes++;
            }
            return nbCartes;
        }
    }

    /**
     * Difficulté FACILE : Choisit une section aléatoire disponible
     */
    public static int choisirSectionFacile(Plateau plateau, int numJoueur) {
        Random rand = new Random();
        int indexSection;
        int tentatives = 0;
        
        // Essayer de trouver une section disponible
        do {
            indexSection = rand.nextInt(9);
            tentatives++;
        } while (!sectionDisponible(plateau.get_section(indexSection), numJoueur) && tentatives < 20);
        
        // Si aucune section disponible trouvée aléatoirement, chercher la première disponible
        if (tentatives >= 20) {
            for (int i = 0; i < 9; i++) {
                if (sectionDisponible(plateau.get_section(i), numJoueur)) {
                    return i;
                }
            }
        }
        
        return indexSection;
    }

    /**
     * Difficulté MOYEN : Choisit la section disponible avec le moins de cartes
     */
    public static int choisirSectionMoyen(Plateau plateau, int numJoueur) {
        int indexSectionMin = -1;
        int minCartes = Integer.MAX_VALUE;
        
        for (int i = 0; i < 9; i++) {
            Section section = plateau.get_section(i);
            
            // Vérifier que la section est disponible
            if (sectionDisponible(section, numJoueur)) {
                int nbCartes = compterCartesSection(section, numJoueur);
                
                if (nbCartes < minCartes) {
                    minCartes = nbCartes;
                    indexSectionMin = i;
                }
            }
        }
        
        // Si aucune section n'est disponible, retourner 0 (ne devrait pas arriver)
        return indexSectionMin >= 0 ? indexSectionMin : 0;
    }

    /**
     * Difficulté DIFFICILE : Choisit la section disponible avec le plus de cartes
     */
    public static int choisirSectionDifficile(Plateau plateau, int numJoueur) {
        int indexSectionMax = -1;
        int maxCartes = Integer.MIN_VALUE;
        
        for (int i = 0; i < 9; i++) {
            Section section = plateau.get_section(i);
            
            // Vérifier que la section est disponible
            if (sectionDisponible(section, numJoueur)) {
                int nbCartes = compterCartesSection(section, numJoueur);
                
                if (nbCartes > maxCartes) {
                    maxCartes = nbCartes;
                    indexSectionMax = i;
                }
            }
        }
        
        // Si aucune section n'est disponible, retourner 0 (ne devrait pas arriver)
        return indexSectionMax >= 0 ? indexSectionMax : 0;
    }
}
