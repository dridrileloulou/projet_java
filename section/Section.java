package section;
import carte.Carte;
import joueur.Joueur;

public class Section{
    Bool borne;
    Carte[] = cartes_j1;
    Carte[] = cartes_j2;

    public Section(){
        this.borne = true;
        this.cartes_j1 = Carte[1];
        this.cartes_j2 = Carte[1];
    }

    public Section(Bool borne, Carte[] cartes_j1, Carte[] cartes_j2){
        this.borne = borne;
        this.cartes_j1 = cartes_j1;
        this.cartes_j2 = cartes_j2;
    }

    public void ajouter_carte(Carte c, Joueur j){
        if (j.getNum_joueur() == 1){
            for (int i = 0; i<cartes_j1.length; i++){
                if (cartes_j1[i] == null){
                    cartes_j1[i] = c;
                    return;
                }
            }
            System.out.println("Plus de place");
        }
        else if (j.getNum_joueur() == 2){
            for (int i = 0; i<cartes_j2.length; i++){
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
        for (int i=0; i<cartes_j1.length; i++){
            if (cartes_j1[i] == null){
                return 0;
            }
            v1[i] = cartes_j1[i].get_valeur();
        }
    }
}