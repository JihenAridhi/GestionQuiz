package GestionnaireQuiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Etudiant
{
    Scanner s=new Scanner(System.in);

    private String nom,groupe;
    private CIN cin=new CIN();


    private ArrayList<QUIZ> nonPassee=new ArrayList<>(); // les quizs non passee d'un etudiant.
    private ArrayList<ArrayList> passee=new ArrayList<>(); // une liste contenant les quizs passee d'un etudiant: quiz, score, reponses[].
    private static ArrayList<ArrayList> data=new ArrayList<>(); // une liste contenant les quizs passee de tous les etudiants: etudiant, quiz, score, reponses[].
    private static ArrayList<Etudiant> etd=new ArrayList<>(); // la liste de tous les etudiants inscrits.

    public int menu(Etudiant e)
    {
        System.out.println( "voulez vous  :\n" +
                            "   1- Visualiser la liste des Quiz disponibles.\n" +
                            "   2- Passer un Quiz\n" +
                            "   3- Consulter le score\n" +
                            "   4- Consulter la correction\n" +
                            "   5- Retour");
        int choix=s.nextInt();
        switch (choix)
        {
            case 1: e.afficherQuiz(); break;
            case 2: e.passer(); break;
            case 3: e.consulterScore(); break;
            case 4: e.consulterCorrection(); break;
            case 5: return 0;
            default: System.out.println("choix invalide !!"); break;
        }
        return menu(e);
    }

    public void login()
    {
        System.out.println("    LOGIN \n");
        do
        {
            System.out.println("saisir votre cin : ");
            String cin=s.next();
            this.cin.setCin(cin);
        }
        while(!this.cin.isCin());
        int i=0;
        while(i<etd.size() && !this.equals(etd.get(i)))
            i++;
        // verifier si un etudiant est inscrit.
        if(i<etd.size())
            menu(etd.get(i));
        // inscrire un etudiant.
        else
        {
            int in;
            do
            {
                System.out.println("vous n'etes pas inscrit !! \n  voulez vous s'incrire ?\n    1-oui\n    2-non");
                in=s.nextInt();
            }
            while (in!=1 && in!=2);
            if(in==1)
            {
                saisir(this.cin);
                etd.add(this);
            }
        }
    }

    public void saisir(CIN cin)
    {
        System.out.println("entrer votre nom : ");
        s.nextLine();
        nom=s.nextLine();
        System.out.println("entrer votre groupe :");
        groupe=s.nextLine();
        this.cin=cin;
        nonPassee=Enseignant.getQuiz();
        menu(this);
    }

    public void afficherQuiz()
    {
        // afficher la listes des quizs dispoibles.
        System.out.println("  les quizs non passees : ");
        afficherNonPassee();
        System.out.println("  les quizs passees : ");
        afficherPassee();
    }

    public void afficherNonPassee()
    {
        if(nonPassee.size()==0)
            System.out.println("    vous avez passee tous les quizs disponibles !");
        for(int i=0;i<nonPassee.size();i++)
            System.out.println("    "+(i+1)+" - Quiz de "+nonPassee.get(i).getModule()+" par Mr/Mrs "+nonPassee.get(i).getAuteur());
    }

    public void afficherPassee()
    {
        if(passee.size()==0)
            System.out.println("    vous n'avez passee au aucun quiz !");
        for(int i=0;i<passee.size();i++)
            System.out.println("    "+(i+1)+" - Quiz de "+((QUIZ)passee.get(i).get(1)).getModule()+" par Mr/Mrs "+((QUIZ)passee.get(i).get(1)).getAuteur());
    }

    public void passer()
    {
        // afficher seulement les quizs non passee.
        afficherNonPassee();
        if(!nonPassee.isEmpty())
        {
            int choix;
            // choisir un quiz à passer
            do
            {
                System.out.println("quel quiz vouler vous passer ?");
                choix=s.nextInt();
            }
            while (choix>nonPassee.size() ||choix<1);
            QUIZ q=nonPassee.get(choix-1);
            String reponse,correct="";
            float[] r=new float[q.getQcm().length]; // un tableau contenant le score pour chaque question.
            int coefQuiz=0,coefQuestion; // le total des options correctes dans un quiz. // le total des options correctes dans une question.
            float scoreQuiz=0,scoreQuestion; // score de un quiz. // score d'une question.
            for(int i=0;i<q.getQcm().length;i++)
            {
                scoreQuestion=0;
                // remplir "correct" par les indices des options correctes de la question i.
                for(int j=0;j<q.getQcm()[i].getOpt().length;j++)
                    if(q.getQcm()[i].getOpt()[j].isValide())
                        correct+=q.getQcm()[i].getOpt()[j].getI();

                    coefQuiz+=correct.length(); // remplir la coeffincient de chaque question.
                    coefQuestion=correct.length(); // remplir la coeffincient de la question i.

                System.out.println("SAISIR L'INDICE DE LA/LES REPONSE(S) CORRECTE(S) SANS ESPACES !!");
                // affichage de la question i.
                q.getQcm()[i].afficher();
                // lire la reponse.
                do
                {
                    System.out.println("votre reponse : ");
                    reponse=s.next();
                }
                while (reponse.length()>q.getQcm()[i].getOpt().length);
                int j=0;
                // verifier si la reponse est correcte, caratere par caractere.
                while (j<reponse.length())
                {
                    int k=0;
                    while (k<correct.length() && Character.toUpperCase(reponse.charAt(j))!=Character.toUpperCase(correct.charAt(k)))
                        k++;
                    // ajouter un point au score pour chaque indice correct.
                    if(k<correct.length())
                    {
                        scoreQuiz++;
                        scoreQuestion++;
                    }
                    j++;
                }
                correct="";
                scoreQuestion*=(100/coefQuestion); // calculer le score de question.
                r[i]=scoreQuestion; // remplir la case i par le score obtenu dans la question i.
            }
            scoreQuiz*=(100.0 /coefQuiz); // calculer le score de quiz.
            System.out.println("    votre score = "+scoreQuiz+"%");

            ArrayList a=new ArrayList();
            a.add(0,this);
            a.add(1,nonPassee.get(choix-1));
            a.add(2,scoreQuiz);
            a.add(3,r);
            passee.add(a); // ajouter le quiz avec ses rponses à la liste des quizs passee.
            nonPassee.remove(choix-1); // supprimer le quiz de la liste ds quizs on passee.
            data.add(a); // ajouter les resultats à la listes des tous les qizs passee.
        }
    }

    public void consulterScore()
    {
        afficherPassee();
        if(passee.size()!=0)
        {
            int choix;
            do
            {
                System.out.println("quel quiz voulez vous consulter ? :");
                choix=s.nextInt();
            }
            while(choix<1 && choix>passee.size());
            System.out.println("    votre score est : "+passee.get(choix-1).get(2)+"%");
        }
    }

    public void consulterCorrection()
    {
        afficherPassee();
        if(passee.size()!=0)
        {
            int choix;
            do
            {
                System.out.println("quel quiz voulez vous consulter ? :");
                choix=s.nextInt();
            }
            while(choix<1 && choix>passee.size());
            QUIZ q=(QUIZ)passee.get(choix-1).get(1);
            q.afficher();
        }
    }

    public static void afficherListEtd(QUIZ q)
    {
        // afficher la liste des etudiants qui ont passee le quiz q.

        int j=0;
        for(int i=0;i<data.size();i++)
        {

            if(data.get(i).get(1)==q)
            {
                j++;
                System.out.println("    "+(1+i)+" - "+((Etudiant)data.get(i).get(0)).nom+" | groupe :"+((Etudiant)data.get(i).get(0)).groupe+" | scrore : "+data.get(i).get(2)+"%");
            }
        }
        if(j==0)
            System.out.println("    aucun etudiant a passee ce quiz !!");
    }

    public static ArrayList<ArrayList> getData(QUIZ q)
    {
        // retourne la liste des etudiants qui ont passee le quiz q.

        ArrayList<ArrayList> d=new ArrayList<>();
        for(int i=0;i<data.size();i++)
        {
            if(data.get(i).get(1)==q)
                d.add(data.get(i));
        }
        return d;
    }

    public boolean equals(Object o)
    {
        Etudiant e=(Etudiant) o;
        return this.cin.equals(e.cin);
    }

    public static ArrayList<ArrayList> getData() {return data;}

    public static ArrayList<Etudiant> getEtd() {return etd;}
}