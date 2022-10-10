package GestionnaireQuiz;

import java.util.ArrayList;
import java.util.Scanner;

public class Enseignant
{
    Scanner s=new Scanner(System.in);

    private ArrayList<QUIZ> ensgQuiz=new ArrayList<>(); // les quizs cree par un enseignant.
    private static ArrayList<QUIZ> quiz=new ArrayList<>(); // les quizs cree par tous les enseignant.
    private static ArrayList<Enseignant> ensg=new ArrayList<>(); //les enseignants inscrits.


    private String nom;
    private CIN cin=new CIN();

    public int menu(Enseignant e)
    {
        // un menu recurcive.
        System.out.println( "  voulez vous  :\n" +
                            "   1- créer un nouveau Quiz.\n" +
                            "   2- visualiser Un Quiz\n" +
                            "   3- supprimer un Quiz \n" +
                            "   4- modifier un Quiz\n" +
                            "   5- Visualiser la liste des étudiants\n" +
                            "   6- Visualiser les taux des reponses d'un module donné\n" +
                            "   7- Retour");
        int choix=s.nextInt();
        switch (choix)
        {
            case 1: e.creer(); break;
            case 2: e.afficher(); break;
            case 3: e.supprimer();break;
            case 4: e.modifier(); break;
            case 5: e.listEtd();break;
            case 6: e.taux();break;
            case 7: return 0;
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
        while(i<ensg.size() && !this.equals(ensg.get(i)))
            i++;
        // si l'enseignant est inscrit.
        if(i<ensg.size())
            menu(ensg.get(i));
        // si l'enseignant n'est pas inscrit.
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
                ensg.add(this);
            }
        }
    }

    public void saisir(CIN cin)
    {
        System.out.println("entrer votre nom : ");
        s.nextLine();
        nom=s.nextLine();
        this.cin=cin;
        menu(this);
    }

    public void creer()
    {
        QUIZ q=new QUIZ();
        q.saisir(nom);
        quiz.add(q);
        ensgQuiz.add(q);
    }

    public void afficherList()
    {
        // afficher la list des quizs de cet enseignant.
        if(ensgQuiz.isEmpty())
            System.out.println("il n'y a aucun quiz !!");
        for (int i=0;i<ensgQuiz.size();i++)
            System.out.println("    "+(i+1)+" - Quiz de "+ensgQuiz.get(i).getModule());
    }

    public void afficher()
    {
        afficherList();
        if(!ensgQuiz.isEmpty())
        {
            int i;
            do
            {
                System.out.println("quel Quiz voulez vous afficher");
                i=s.nextInt();
            }
            while (i<1 && i>ensgQuiz.size());
            ensgQuiz.get(i-1).afficher();
        }
    }

    public void supprimer()
    {
        afficherList();
        if(!ensgQuiz.isEmpty())
        {
            int i;
            do
            {
                System.out.println("de quel Quiz voulez vous supprimer");
                i=s.nextInt();
            }
            while (i<1 && i>ensgQuiz.size());
            ensgQuiz.get(i-1).afficher();
            int j;
            do
            {
                System.out.println("voulez vous spprimer :\n    1- le quiz\n    2-une question \n   3-retour");
                j=s.nextInt();
            }
            while(j!=1 && j!=2 && j!=3);
            switch (j)
            {
                case 1: ensgQuiz.remove(i-1); break;
                case 2: ensgQuiz.get(i-1).supprimer(); break;
                case 3: return;
            }
        }
    }

    public void modifier()
    {
        afficherList();
        if(!ensgQuiz.isEmpty())
        {
            int i;
            do
            {
                System.out.println("quel Quiz voulez vous modifier");
                i=s.nextInt();
            }
            while (i<1 && i>ensgQuiz.size());
            ensgQuiz.get(i-1).modifier();
        }
    }

    public void listEtd()
    {
        // afficher la list des etudiants qui ont passee un quiz donnee.
        afficherList();
        int i;
        do
        {
            System.out.println("quel Quiz voulez vous consulter");
            i=s.nextInt();
        }
        while (i<1 && i>ensgQuiz.size());
        Etudiant.afficherListEtd(ensgQuiz.get(i-1));
    }

    public void taux()
    {
        afficherList();
        int i;
        do
        {
            System.out.println("quel Quiz voulez vous consulter");
            i=s.nextInt();
        }
        while (i<1 && i>ensgQuiz.size());
        QUIZ q=ensgQuiz.get(i-1);
        if(Etudiant.getData(q).isEmpty())
            System.out.println("aucun etudiant a passé ce quiz !!");
        else
        {
            for(int j=0;j<q.getQcm().length;j++)
            {
                float taux=0;
                for(int k=0;k<Etudiant.getData(q).size();k++)
                {
                    // calculer le taux des reponses correcte de la question j.
                    float[] r =(float[]) Etudiant.getData(q).get(k).get(3);
                    taux+=(r[j] /Etudiant.getData(q).size());
                }
                System.out.println("le taux de reponses justes pour la question "+(j+1)+": "+taux+"%");
            }
        }
    }

    public boolean equals(Object o)
    {
        Enseignant e=(Enseignant) o;
        return this.cin.equals(e.cin);
    }

    public static ArrayList<QUIZ> getQuiz() {return quiz;}
}