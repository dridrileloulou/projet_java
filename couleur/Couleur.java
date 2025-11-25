package couleur;

public class Couleur {
    private int r;
    private int g;
    private int b;

    public Couleur(){
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public Couleur(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static Couleur bleu(){
        return new Couleur(0,0,255);
    }

    public static  Couleur rouge(){
        return new Couleur(255,0,0);
    }

    public static Couleur vert(){
        return new Couleur(0,255,0);
    }

    public static Couleur jaune(){
        return new Couleur(255,255,0);
    }

    public static Couleur violet(){
        return new Couleur(238,130,238);
    }

    public static Couleur marron(){
        return new Couleur(137,81,41);
    }

    public static Couleur blanc(){
        return new Couleur(255,255,255);
    }

    public static Couleur noir(){
        return new Couleur(0,0,0);
    }

    public static Couleur au_hasard(){
        int r = (int)(Math.random() * 256);
        int g = (int)(Math.random() * 256);
        int b = (int)(Math.random() * 256);
        return new Couleur(r,g,b);
    }

    public String description(){
        return "Rouge : " + r + " | Vert : " + g + " | Bleu : " + b;
    }
}