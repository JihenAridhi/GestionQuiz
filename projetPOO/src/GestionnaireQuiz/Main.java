package GestionnaireQuiz;

import java.util.Scanner;

public class Main
{
    public static int menu()
    {
        // methode recursive
        Scanner s=new Scanner(System.in);
        System.out.println("BIENVENUE\n  vous etes : \n    1-Enseignant\n    2-Etudiant\n    3-Quitter");
        int choix=s.nextInt();
        switch (choix)
        {
            // creer un enseignant et login.
            case 1:
            {
                Enseignant e=new Enseignant();
                e.login();
                break;
            }
            // creer un etudiant et login.
            case 2:
            {
                Etudiant e=new Etudiant();
                e.login();
                break;
            }
            //quitter le programme.
            case 3: System.out.println("bye bye !");return 0;
            default: System.out.println("choix invalide !!"); break;
        }
        return menu();
    }

    public static void main(String[] args)
    {
        // afficher un menu permettant de choisir : eseignant , etudiant , quitter.
        menu();
    }
}