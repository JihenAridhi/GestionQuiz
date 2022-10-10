package GestionnaireQuiz;

import java.util.Scanner;

public class QCM
{
    Scanner s=new Scanner(System.in);

    private String question;
    private OPTION[] opt;
    private int i;

    public void saisir(int i)
    {
        this.i=i;
        System.out.println("question "+this.i+" : ");
        question=s.nextLine();
        boolean valide;
        int n;
        // verifier qu'il a au moin 2 options.
        do
        {
            System.out.println("combiens d'options ?");
            n=s.nextInt();
            if(n<=1)
                System.out.println("if faut que chaque question admet au moin 2 options !!\n    ressayer");
        }
        while (n<=1);
        opt=new OPTION[n];
        // verifier s'il y a au moin 1 option valide.
        valide=false;
        do
        {
            for (int j=0;j< opt.length;j++)
            {
                opt[j]=new OPTION();
                opt[j].saisir(j);
                valide=(valide||opt[j].isValide());
            }
            if(!valide)
                System.out.println("il faut que chaque question admet au moin une option valide !!\n  ressayer");
        }
        while (!valide);
    }

    public void afficherCorrection()
    {
        // utilisee pour l'enseignant, et l'etudiant si il a passee le quiz.
        System.out.println(i+" - "+question);
        for(int i=0;i< opt.length;i++)
            opt[i].afficherCorrection();
    }

    public void afficher()
    {
        // utilisee pour l'etudiant lors de passage de quiz.
        System.out.println(i+" - "+question);
        for(int i=0;i< opt.length;i++)
            opt[i].afficher();
    }

   public void supprimer()
    {
        if(opt.length==2)
            System.out.println("il faut que chaque question admet au moin 2 option !!\n  pas de supprission !!");
        else
        {
            int i;
            do
            {
                System.out.println("quelle option voulez vous supprimer");
                i=s.nextInt();
            }
            while (i<1 && i> opt.length);
            OPTION[] tab=new OPTION[opt.length-1];
            for(int j=0;j< opt.length-1;j++)
            {
                if(j<i)
                    tab[j]=opt[j];
                else
                    tab[j]=opt[j+1];
            }
            opt=tab;
        }
    }


    public int modifier()
    {
        System.out.println(" voulez vous modifier :\n    1-la question\n    2-les options\n    3-retour");
        int choix=s.nextInt();

        switch (choix)
        {
            case 1: System.out.println("saisir la nouvelle question");
                    s.nextLine();
                    String question=s.nextLine();
                    setQuestion(question);
                    break;
            case 2: char i;  boolean valide=false;
                    do
                    {
                        do
                        {
                            System.out.println("quelle option ?");
                            i=s.next().charAt(0);
                        }
                        while (i<'a' || i>= opt.length+'a');
                        opt[(int)i-'a'].modifier();
                        for(int j=0;j< opt.length;j++)
                            valide=valide||opt[j].isValide();
                        if(!valide)
                            System.out.println("il faut que chaque question admet au moin une option valide !!\n  ressayer");
                    }
                    while (!valide);
                    break;
            case 3: return 0;
            default: System.out.println("choix invalide !!");
        }
        return modifier();
    }

    public OPTION[] getOpt() {return opt;}



    public void setQuestion(String question) {this.question = question;}
}