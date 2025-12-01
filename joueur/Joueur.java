package joueur;
import carte.Carte;
import paquet.Paquet;
import section.Section;

public class Joueur {
    private int score;
    private String nom;
    private Carte main[] = new Carte[6];
    private int num_joueur;

    public Joueur(String nom) {
        this.nom = nom;
        this.score = 0;
        this.main = new Carte[6];
        this.num_joueur = 0;
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

    public void piocherCarte(Paquet pioche[]) {
        Carte cartePiochee = pioche[0].piocherCarte();
        for (int i = 0; i < main.length; i++) {
            if (main[i] == null) {
                main[i] = cartePiochee;
                break;
            }
        }    
    }

    public void jouer_carte(Carte carteJouee, Section s){
        for (int i = 0; i < main.length; i++) {
            if (main[i] == carteJouee) {
                s.ajouterCarte(carteJouee);
                main[i] = null;
                break;
            }
        }


    }

    public void ajouter_score(int point){
        this.score += point;
    }

    



}