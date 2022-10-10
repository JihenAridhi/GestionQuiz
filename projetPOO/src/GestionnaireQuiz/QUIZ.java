package GestionnaireQuiz;

import java.util.Scanner;

public class QUIZ
{
    Scanner s=new Scanner(System.in);

    private String auteur;
    private QCM[] qcm;
    private String module;

    public void saisir(String auteur)
    {
        this.auteur=auteur;
        System.out.println("pour quel module ?");
        module=s.nextLine();
        System.out.println("combiens de questions ?");
        int n=s.nextInt();
        qcm=new QCM[n];
        for(int i=0;i<n;i++)
        {
            qcm[i]=new QCM();
            qcm[i].saisir(i+1);
        }
    }

    public void afficher()
    {
        for(int i=0;i<qcm.length;i++)
            qcm[i].afficherCorrection();
    }

    public void supprimer()
    {
        int i;
        do
        {
            System.out.println("de quel quetion voulez vous supprimer");
            i=s.nextInt();
        }
        while (i<1 && i> qcm.length);
        int choix;
        do
        {
            System.out.println("vouler vous supprimer :\n   1-la question\n    2-une option\n   3-retour");
            choix=s.nextInt();
        }
        while(choix<1 || choix>3);
        switch (choix)
        {
            case 1:
                if(qcm.length==1)
                    System.out.println("chaque quiz doit admet au moin 1 question !!");
                else
                {
                    QCM[] tab=new QCM[qcm.length-1];
                    for(int j=0;j< qcm.length-1;j++)
                    {
                        if(j<i)
                            tab[j]=qcm[j];
                        else
                            tab[j]=qcm[j+1];
                    }
                    qcm=tab;
                }
                break;
            case 2: qcm[i-1].supprimer();
                break;
            case 3: break;
        }
    }

    public void modifier()
    {
        afficher();
        int i;
        do
        {
            System.out.println("qulle question vouler vous modifier ?");
            i=s.nextInt();
        }
        while(i<1 || i> qcm.length);

        qcm[i-1].modifier();
    }

    public String getAuteur() {return auteur;}

    public QCM[] getQcm() {return qcm;}

    public String getModule() {return module;}


}