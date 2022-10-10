package GestionnaireQuiz;

import java.util.Scanner;

public class OPTION
{
    Scanner s=new Scanner(System.in);

    private char i;
    private String option;
    private boolean valide;

    public void saisir(int i)
    {
        this.i=(char)(i+'a');
        System.out.println("option "+this.i+" : ");
        option=s.nextLine();
        int j;
        do{
            System.out.println("est cette option : 1-vrai ; 2- fausse ");
            j=s.nextInt();
        }
        while(j!=1 && j!=2);
        valide=j==1;
    }

    public void afficherCorrection() {System.out.println("    "+i+" - "+option+" : "+valide);}

    public void afficher()
    {
        System.out.println("    "+i+" - "+option);
    }

    public int modifier()
    {
        System.out.println(" voulez vous modifier :\n    1-l'option\n    2-la validité\n    3-retour");
        int choix=s.nextInt();

        switch (choix)
        {
            case 1: System.out.println("saisir la nouvelle option");
                s.nextLine();
                String option=s.nextLine();
                setOption(option);
                break;
            case 2: int valide;
                    do
                    {
                        System.out.println("1-vrai\n2-faux");
                        valide=s.nextInt();
                    }
                    while(valide!=1 && valide!=2);
                    setValide(valide==1);
                break;
            case 3: return 0;
            default: System.out.println("choix invalide !!");
        }
        return modifier();
    }

    public char getI() {return i;}

    public boolean isValide() {return valide;}

    public void setOption(String option) {this.option = option;}

    public void setValide(boolean valide) {this.valide = valide;}
}