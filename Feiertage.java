import javax.swing.JOptionPane;
public static void main (String[] args) {
    int jahr;
    String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
    jahr = Integer.parseInt(UserInput);

}

class Feiertag {
    int tag;
    int monat;
    String name;

    Feiertag (int tag, int monat, String name) {
        this.tag = tag;
        this.monat = monat;
        this.name = name;
    }

    String format() {
        return String.format("%02d.%02d - %s", tag, monat, name);
    }
}