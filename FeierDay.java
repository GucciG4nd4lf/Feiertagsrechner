import java.util.ArrayList;
import javax.swing.JOptionPane;

public class FeierDay {
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
    public static void main (String[] args) {
        String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
        int jahreseingabe = Integer.parseInt(UserInput);
        ArrayList<Feiertag> feiertage = new ArrayList<>();
        feiertage.add(new Feiertag(1, 1, "Neujahr"));
        feiertage.add(new Feiertag(6, 1, "Heilige Drei Koenige"));
        feiertage.add(new Feiertag(1, 5, "Tag der Arbeit"));
        feiertage.add(new Feiertag(8, 8, "Augsburger Friedensfest"));
        feiertage.add(new Feiertag(15, 8, "Mariae Himmelfahrt"));
        feiertage.add(new Feiertag(3, 10, "Tag der deutschen Einheit"));
        feiertage.add(new Feiertag(1, 11, "Allerheiligen"));
        feiertage.add(new Feiertag(25, 12, "1. Weihnachtsfeiertag"));
        feiertage.add(new Feiertag(26, 12, "2. Weihnachtsfeiertag"));
        feiertage.add(new Feiertag(31, 12, "Silvester"));

        System.out.println("\nFeste Feiertage in Augsburg im Jahr " + jahreseingabe + ":");
        for (Feiertag f : feiertage) {
            System.out.println(f.format());
        }
        int[] ostersonntag = osterSonntagRechner(jahreseingabe);
        int[] ostermontag = ostermontagRechner(ostersonntag);
        int[] karfreitag = karfreitagRechner(ostersonntag);
        int[] christiHimmelfahrt = christiHimmelfahrtRechner(ostersonntag, jahreseingabe);
        int[] pfingstsonntag = pfingstsonntagRechner(ostersonntag, jahreseingabe);
        int[] pfingstmontag = pfingstmontagRechner(pfingstsonntag, jahreseingabe);
        int[] fronleichnam = fronleichnamRechner(ostersonntag, jahreseingabe);

        System.out.println("Berechnete Feiertage in Augsburg im Jahr " + jahreseingabe +":");
        System.out.println(String.format("%02d.%02d", karfreitag[0], karfreitag[1]) + " - Karfreitag");
        System.out.println(String.format("%02d.%02d", ostersonntag[0], ostersonntag[1]) + " - Ostersonntag");
        System.out.println(String.format("%02d.%02d", ostermontag[0], ostermontag[1]) + " - Ostermontag");     
        System.out.println(String.format("%02d.%02d", christiHimmelfahrt[0], christiHimmelfahrt[1]) + " - Christi Himmelfahrt");
        System.out.println(String.format("%02d.%02d", pfingstsonntag[0], pfingstsonntag[1]) + " - Pfingstsonntag");     
        System.out.println(String.format("%02d.%02d", pfingstmontag[0], pfingstmontag[1]) + " - Pfingstmontag");   
        System.out.println(String.format("%02d.%02d", fronleichnam[0], fronleichnam[1]) + " - Fronleichnam");
    }    
    public static int[] osterSonntagRechner(int jahr){
        int mondJahr = jahr % 19;
        int schaltJahr = jahr % 4;
        int wochenTagMond = jahr % 7;
        int monat;

        int osterperiode = (8*(jahr/100)+13)/25-2;        
        int schaltjahrZyklus = jahr/100-jahr/400-2;
        osterperiode = (15 + schaltjahrZyklus - osterperiode) % 30;
        int vollmondWochentag = (6 + schaltjahrZyklus) % 7;
        int frühlingsVollmond = (osterperiode + 19 * mondJahr) % 30;

        if (frühlingsVollmond == 29){
            frühlingsVollmond = 28;
        } else if (frühlingsVollmond == 28 && mondJahr >=11){
            frühlingsVollmond = 27;
        }
        int frühlingsVollmondWochentag = (2 * schaltJahr + 4 * wochenTagMond + 6 * frühlingsVollmond + vollmondWochentag) % 7;        
        int tag = 21 + frühlingsVollmond + frühlingsVollmondWochentag + 1;
        monat = 3;

        if (tag > 31){
            tag = tag % 31;
            monat = 4;
        }
        return new int[] {tag, monat};
    }
    public static boolean schaltjahrCheck(int jahr){
        if(jahr % 400 == 0) return true;
        if(jahr % 100 == 0) return false;
        return (jahr % 4 == 0);
    }
    public static int[] karfreitagRechner(int[]ostersonntag){
        int tag = ostersonntag[0]-2;
        int monat = ostersonntag[1];
        if (tag<=0){
            if(monat == 4){
                monat = 3;
                tag = 31 + tag;
            }
        }
        return new int[]{tag, monat};
    }
    public static int[] ostermontagRechner(int[] ostersonntag) {
        int tag = ostersonntag[0] + 1;
        int monat = ostersonntag[1];
        if(tag > 31) {
            tag = tag - 31;
            monat +=1;
        }
        return new int[]{tag, monat};
    }

    public static int[] pfingstsonntagRechner(int[]ostersonntag, int jahr){
        int zusatzTage = schaltjahrCheck(jahr) ? 50 : 49;
        int tag = ostersonntag[0]+zusatzTage;
        int monat = ostersonntag[1];
        if(tag > 31 && tag < 63){
            tag = tag-30;
            monat +=1;
        }else if(tag > 62){
            tag = tag-61;
            monat += 2;
        }
        return new int[] {tag, monat};
    }
    public static int[] pfingstmontagRechner(int[] pfingstsonntag, int jahr){
        int tag = pfingstsonntag[0] + 1;
        int monat = pfingstsonntag[1];
        if(tag > 31 && tag < 63) {
            tag = tag - 30;
            monat +=1;
        } else if (tag > 62) {
            tag = tag - 61;
            monat +=2;
        }
        return new int[]{tag, monat};
    }
    public static int[] fronleichnamRechner(int[] ostersonntag, int jahr){
        int zusatzTage = schaltjahrCheck(jahr) ? 60 : 61;
        int tag = ostersonntag[0] + zusatzTage;
        int monat = ostersonntag[1];
        if(tag > 31 && tag < 63) {
            tag = tag - 30;
            monat +=1;
        } else if (tag > 62) {
            tag = tag - 61;
            monat +=2;
        }
        return new int[]{tag, monat};
    }
    public static int[] christiHimmelfahrtRechner(int[]ostersonntag, int jahr){
        int zusatzTage = schaltjahrCheck(jahr) ? 40 : 39;
        int tag = ostersonntag[0]+zusatzTage;
        int monat = ostersonntag[1];
        if(tag > 31 && tag < 63){
            tag = tag-30;
            monat +=1;
        }else if(tag > 62){
            tag = tag-61;
            monat +=2;
        }
        return new int[] {tag, monat};
    }
}