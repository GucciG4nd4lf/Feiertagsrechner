import javax.swing.JOptionPane;
import java.util.ArrayList;

public class festeFeiertage {

public static void main (String[] args) {
    int jahr;
    String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
    jahr = Integer.parseInt(UserInput);

    ArrayList<Feiertag> feiertage = new ArrayList<>();

    // feste Feiertage
    feiertage.add(new Feiertag(1, 1, "Neujahr"));
    feiertage.add(new Feiertag(6, 1, "Heilige Drei Könige"));
    feiertage.add(new Feiertag(1, 5, "Tag der Arbeit"));
    feiertage.add(new Feiertag(8, 8, "Augsburger Friedensfest"));
    feiertage.add(new Feiertag(15, 8, "Mariä Himmelfahrt"));
    feiertage.add(new Feiertag(3, 10, "Tag der deutschen Einheit"));
    feiertage.add(new Feiertag(1, 11, "Allerheiligen"));
    feiertage.add(new Feiertag(25, 12, "1. Weihnachtsfeiertag"));
    feiertage.add(new Feiertag(26, 12, "2. Weihnachtsfeiertag"));
    feiertage.add(new Feiertag(31, 12, "Silvester"));

    System.out.println("\nFeiertage in Augsburg im Jahr " + jahr + ":");
    for (Feiertag f : feiertage) {
        System.out.println(f.format());
    }
}

static class Feiertag {
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
}
