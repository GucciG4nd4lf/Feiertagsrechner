import javax.swing.JOptionPane;
public class Osterfeiertage {
    int jahr = jahreseingabe();
    public int jahreseingabe(){
        int jahr;
        String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
        return jahr = Integer.parseInt(UserInput);
    }
    public void Osterrechner(){
        int a = jahr % 19;
        int b = jahr % 4;
        
    }
    public static void main (String[] args) {

    }
}