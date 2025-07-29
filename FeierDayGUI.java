import java.awt.*;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;

public class FeierDayGUI {

    static class Feiertag {
        int tag;
        int monat;
        String name;

        Feiertag(int tag, int monat, String name) {
            this.tag = tag;
            this.monat = monat;
            this.name = name;
        }

        String format() {
            return String.format("%02d.%02d - %s", tag, monat, name);
        }
    }

    public static void main(String[] args) {
      JFrame frame = new JFrame("Feiertagsrechner Augsburg");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 650);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE);

    
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));


        ImageIcon originalIcon = new ImageIcon("logo.jpg");
        Image scaledImage = originalIcon.getImage().getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel bildLabel = new JLabel(scaledIcon);
        bildLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    
        JPanel inputPanel = new JPanel();
        JLabel label = new JLabel("Jahr eingeben:");
        JTextField jahrEingabe = new JTextField(10);
        JButton berechnenButton = new JButton("Feiertage berechnen");
        inputPanel.add(label);
        inputPanel.add(jahrEingabe);
        inputPanel.add(berechnenButton);

    
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(bildLabel);
        topPanel.add(Box.createVerticalStrut(10));
        topPanel.add(inputPanel);
        topPanel.setBackground(Color.WHITE);

        JTextArea ausgabeArea = new JTextArea();
        ausgabeArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ausgabeArea);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        berechnenButton.addActionListener(e -> {
            String userInput = jahrEingabe.getText();
            try {
                int jahr = Integer.parseInt(userInput);
                ArrayList<Feiertag> feiertage = berechneFeiertage(jahr);

                int[] ostersonntag = osterSonntagRechner(jahr);
                int[] ostermontag = ostermontagRechner(ostersonntag);
                int[] karfreitag = karfreitagRechner(ostersonntag);
                int[] christiHimmelfahrt = christiHimmelfahrtRechner(ostersonntag, jahr);
                int[] pfingstsonntag = pfingstsonntagRechner(ostersonntag, jahr);
                int[] pfingstmontag = pfingstmontagRechner(pfingstsonntag, jahr);
                int[] fronleichnam = fronleichnamRechner(ostersonntag, jahr);

                StringBuilder ausgabe = new StringBuilder();
                ausgabe.append("Feste Feiertage in Augsburg im Jahr ").append(jahr).append(":\n");
                ausgabe.append(feiertage.stream()
                        .map(Feiertag::format)
                        .collect(Collectors.joining("\n")));

                ausgabe.append("\n\nBerechnete Feiertage:\n");
                ausgabe.append(String.format("%02d.%02d - Karfreitag\n", karfreitag[0], karfreitag[1]));
                ausgabe.append(String.format("%02d.%02d - Ostersonntag\n", ostersonntag[0], ostersonntag[1]));
                ausgabe.append(String.format("%02d.%02d - Ostermontag\n", ostermontag[0], ostermontag[1]));
                ausgabe.append(String.format("%02d.%02d - Christi Himmelfahrt\n", christiHimmelfahrt[0], christiHimmelfahrt[1]));
                ausgabe.append(String.format("%02d.%02d - Pfingstsonntag\n", pfingstsonntag[0], pfingstsonntag[1]));
                ausgabe.append(String.format("%02d.%02d - Pfingstmontag\n", pfingstmontag[0], pfingstmontag[1]));
                ausgabe.append(String.format("%02d.%02d - Fronleichnam\n", fronleichnam[0], fronleichnam[1]));
                ausgabeArea.setText(ausgabe.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Bitte ein gültiges Jahr eingeben!", "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.setVisible(true);
    }

    static ArrayList<Feiertag> berechneFeiertage(int jahr) {
        ArrayList<Feiertag> feiertage = new ArrayList<>();
        feiertage.add(new Feiertag(1, 1, "Neujahr"));
        feiertage.add(new Feiertag(6, 1, "Heilige Drei Könige"));
        feiertage.add(new Feiertag(1, 5, "Tag der Arbeit"));
        feiertage.add(new Feiertag(8, 8, "Augsburger Friedensfest"));
        feiertage.add(new Feiertag(15, 8, "Mariä Himmelfahrt"));
        feiertage.add(new Feiertag(3, 10, "Tag der Deutschen Einheit"));
        feiertage.add(new Feiertag(1, 11, "Allerheiligen"));
        feiertage.add(new Feiertag(25, 12, "1. Weihnachtsfeiertag"));
        feiertage.add(new Feiertag(26, 12, "2. Weihnachtsfeiertag"));
        feiertage.add(new Feiertag(31, 12, "Silvester"));
        return feiertage;
    }

    public static int[] osterSonntagRechner(int jahr) {
        int mondJahr = jahr % 19;
        int schaltJahr = jahr % 4;
        int wochenTagMond = jahr % 7;

        int osterperiode = (8 * (jahr / 100) + 13) / 25 - 2;
        int schaltjahrZyklus = jahr / 100 - jahr / 400 - 2;
        osterperiode = (15 + schaltjahrZyklus - osterperiode) % 30;
        int vollmondWochentag = (6 + schaltjahrZyklus) % 7;
        int frühlingsVollmond = (osterperiode + 19 * mondJahr) % 30;

        if (frühlingsVollmond == 29) {
            frühlingsVollmond = 28;
        } else if (frühlingsVollmond == 28 && mondJahr >= 11) {
            frühlingsVollmond = 27;
        }

        int frühlingsVollmondWochentag = (2 * schaltJahr + 4 * wochenTagMond + 6 * frühlingsVollmond + vollmondWochentag) % 7;
        int tag = 21 + frühlingsVollmond + frühlingsVollmondWochentag + 1;
        int monat = 3;

        if (tag > 31) {
            tag = tag % 31;
            monat = 4;
        }
        return new int[]{tag, monat};
    }

    public static boolean schaltjahrCheck(int jahr) {
        if (jahr % 400 == 0) return true;
        if (jahr % 100 == 0) return false;
        return (jahr % 4 == 0);
    }

    public static int[] karfreitagRechner(int[] ostersonntag) {
        int tag = ostersonntag[0] - 2;
        int monat = ostersonntag[1];
        if (tag <= 0) {
            if (monat == 4) {
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

    public static int[] pfingstsonntagRechner(int[] ostersonntag, int jahr) {
        int zusatzTage = schaltjahrCheck(jahr) ? 49 : 50;
        int tag = ostersonntag[0] + zusatzTage;
        int monat = ostersonntag[1];
        if (tag > 31 && tag < 63) {
            tag = tag - 30;
            monat += 1;
        } else if (tag > 62) {
            tag = tag - 61;
            monat += 2;
        }
        return new int[]{tag, monat};
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

    public static int[] christiHimmelfahrtRechner(int[] ostersonntag, int jahr) {
        int zusatzTage = schaltjahrCheck(jahr) ? 39 : 40;
        int tag = ostersonntag[0] + zusatzTage;
        int monat = ostersonntag[1];
        if (tag > 31 && tag < 63) {
            tag = tag - 30;
            monat += 1;
        } else if (tag > 62) {
            tag = tag - 61;
            monat += 2;
        }
        return new int[]{tag, monat};
    }
}