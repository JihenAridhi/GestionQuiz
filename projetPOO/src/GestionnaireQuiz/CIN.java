package GestionnaireQuiz;

public class CIN
{
    private String cin;

    // methode recurcive: verifier si une chaine de character est numerique.
    public boolean isNumber(int i)
    {
        if(i==cin.length())
            return true;
        if(cin.charAt(i)>='0' && cin.charAt(i)<='9' && i<cin.length())
            return isNumber(i+1);
        else
            return false;
    }

    // verifier si une chaine de charactere est cin.
    public boolean isCin() {return (cin.length()==8 && this.isNumber(0));}

    public void setCin(String cin) {this.cin = cin;}

    public boolean equals(Object o)
    {
        CIN cin=(CIN) o;
        return this.cin.equals(cin.cin);
    }
}