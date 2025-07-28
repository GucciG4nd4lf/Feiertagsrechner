import javax.swing.JOptionPane;
public class Feiertage {
    int jahr = jahreseingabe();
    public int jahreseingabe(){
        int jahr;
        String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
        return jahr = Integer.parseInt(UserInput);
    }
    public void Osterrechner(){
        int K = jahr/100;
        int M = 15+((3*K+3)/4)-((8*K+13)/25);
        int c = jahreszahl % 7;
        int monat = 0;
    }
    public static void main (String[] args) {

    }
}
    