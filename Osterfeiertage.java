import javax.swing.JOptionPane;
public class Osterfeiertage {
    public static void main (String[] args) {
        String UserInput = JOptionPane.showInputDialog("Geben sie ein Jahr ein");
        int jahreseingabe = Integer.parseInt(UserInput);
        int[] ostersonntag = osterSonntagRechner(jahreseingabe);
        int[] karfreitag = karfreitagRechner(ostersonntag);
        int[] christiHimmelfahrt = christiHimmelfahrtRechner(ostersonntag, jahreseingabe);
        int[] pfingstsonntag = pfingstsonntagRechner(ostersonntag, jahreseingabe);
        System.out.println("Ostersonntag im Jahr " + jahreseingabe + " ist am: " + ostersonntag[0] + "." + ostersonntag[1]);
        System.out.println("Karfreitag im Jahr " + jahreseingabe + " ist am: " + karfreitag[0] + "." + karfreitag[1]);
        System.out.println("Christi Himmelfahrt im Jahr " + jahreseingabe + " ist am: " + christiHimmelfahrt[0] + "." + christiHimmelfahrt[1]);
        System.out.println("Pfingstsonntag im Jahr " + jahreseingabe + " ist am: " + pfingstsonntag[0] + "." + pfingstsonntag[1]);
        
    }    
    public static int[] osterSonntagRechner(int jahr){
        int mondJahr = jahr % 19;
        int schaltJahr = jahr % 4;
        int wochenTagMond = jahr % 7;
        int monat = 0;
        
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