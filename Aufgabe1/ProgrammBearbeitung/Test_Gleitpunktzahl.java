package Aufgabe1;

import java.util.Arrays;

public class Test_Gleitpunktzahl {

    /**
     * Testklasse, ob alles funktioniert!
     */

    public static void main(String[] argv) {

        test_Gleitpunktzahl();

    }

    public static void test_Gleitpunktzahl() {

        /**
         * *******************************
         */
        /* Test der Klasse Gleitpunktzahl */
        /**
         * *******************************
         */
        System.out.println("-----------------------------------------");
        System.out.println("Test der Klasse Gleitpunktzahl");

        /*
		 * Verglichen werden die BitFelder fuer Mantisse und Exponent und das
		 * Vorzeichen
         */
        Gleitpunktzahl.setSizeMantisse(8);
        Gleitpunktzahl.setSizeExponent(8);

        Double d;
        Gleitpunktzahl x;
        Gleitpunktzahl y;
        Gleitpunktzahl gleitref = new Gleitpunktzahl();
        Gleitpunktzahl gleiterg;

        /* Test von setDouble */
        System.out.println("Test von setDouble");
        try {
            // Test: setDouble

            d = 0.5;
            x = new Gleitpunktzahl(0.5);

            // Referenzwerte setzen
            gleitref = new Gleitpunktzahl(0.5);

            // Test, ob Ergebnis korrekt
            if (x.compareAbsTo(gleitref) != 0
                    || x.vorzeichen != gleitref.vorzeichen) {
                printErg("" + x.toDouble(), "" + gleitref.toDouble());
            } else {
                System.out.println("Richtiges Ergebnis\n");
            }

            /**
             * ***********
             * Eigene Tests einfuegen
             */
            System.out.println("\nEIGENE TESTS\n");

            x = new Gleitpunktzahl(d);

            print_for_setDouble(d, x);

            d = 3.0;
            x = new Gleitpunktzahl(d);

            print_for_setDouble(d, x);

            d = 0.;
            x = new Gleitpunktzahl(d);

            print_for_setDouble(d, x);

            d = Double.NEGATIVE_INFINITY;
            x = new Gleitpunktzahl(d);

            print_for_setDouble(d, x);

            System.out.println("\nEnde Test von setDouble\n");

        } catch (Exception e) {
            System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
        }

        /* Addition */
        System.out.println("Test der Addition mit Gleitpunktzahl");
        try {
            // Test: Addition
            System.out.println("Test: Addition  x + y");
            x = new Gleitpunktzahl(3.25);
            y = new Gleitpunktzahl(0.5);

            // Referenzwerte setzen
            gleitref = new Gleitpunktzahl(3.25 + 0.5);

            // Berechnung
            gleiterg = x.add(y);

            // Test, ob Ergebnis korrekt
            if (gleiterg.compareAbsTo(gleitref) != 0
                    || gleiterg.vorzeichen != gleitref.vorzeichen) {
                printAdd(x.toString(), y.toString());
                printErg(gleiterg.toString(), gleitref.toString());
            } else {
                System.out.println("    Richtiges Ergebnis\n");
            }

            /**
             * ***********
             * Eigene Tests einfuegen
             */
            System.out.println("\nEIGENE TESTS\n");

            x = new Gleitpunktzahl(30.0);
            y = new Gleitpunktzahl(0.25);
            gleiterg = x.add(y);
            gleitref = new Gleitpunktzahl(30.0 + 0.25);

            print_for_op(gleiterg, gleitref);

        } catch (Exception e) {
            System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
        }

        /* Subtraktion */
        try {
            System.out.println("\nTest der Subtraktion mit Gleitpunktzahl");

            // Test: Addition
            System.out.println("Test: Subtraktion  x - y");
            x = new Gleitpunktzahl(3.25);
            y = new Gleitpunktzahl(2.75);

            // Referenzwerte setzen
            gleitref = new Gleitpunktzahl((3.25 - 2.75));

            // Berechnung
            gleiterg = x.sub(y);

            // Test, ob Ergebnis korrekt
            if (gleiterg.compareAbsTo(gleitref) != 0
                    || gleiterg.vorzeichen != gleitref.vorzeichen) {
                printSub(x.toString(), y.toString());
                printErg(gleiterg.toString(), gleitref.toString());
            } else {
                System.out.println("    Richtiges Ergebnis\n");
            }

            /**
             * ***********
             * Eigene Tests einfuegen
             */
            System.out.println("\nEIGENE TESTS\n");
            
            x = new Gleitpunktzahl(3.0);
            y = new Gleitpunktzahl(0.25);
            gleiterg = x.sub(y);
            gleitref = new Gleitpunktzahl(3.0 - 0.25);

            print_for_op(gleiterg, gleitref);
            
            x = new Gleitpunktzahl(-3.0);
            y = new Gleitpunktzahl(-0.25);
            gleiterg = x.sub(y);
            gleitref = new Gleitpunktzahl(-3.0 + 0.25);

            print_for_op(gleiterg, gleitref);
            
            x.setInfinite(true);
            y.setInfinite(false);
            gleiterg = x.sub(y);
            gleitref.setInfinite(true);
            
            print_for_op(gleiterg, gleitref);
            
            

        } catch (Exception e) {
            System.out.print("Exception bei der Auswertung des Ergebnis!!\n");
        }

        /* Sonderfaelle */
        System.out.println("\nTest der Sonderfaelle mit Gleitpunktzahl");

        try {
            // Test: Sonderfaelle
            // 0 - inf
            System.out.println("Test: Sonderfaelle");
            x = new Gleitpunktzahl(0.0);
            y = new Gleitpunktzahl(1.0 / 0.0);

            // Referenzwerte setzen
            gleitref.setInfinite(true);

            // Berechnung mit der Methode des Studenten durchfuehren
            gleiterg = x.sub(y);

            // Test, ob Ergebnis korrekt
            if (gleiterg.compareAbsTo(gleitref) != 0
                    || gleiterg.vorzeichen != gleitref.vorzeichen) {
                printSub(x.toString(), y.toString());
                printErg(gleiterg.toString(), gleitref.toString());
            } else {
                System.out.println("    Richtiges Ergebnis\n");
            }

            /**
             * ***********
             * Eigene Tests einfuegen
             */
            System.out.println("\nEIGENE TESTS\n");
            
            

        } catch (Exception e) {
            System.out
                    .print("Exception bei der Auswertung des Ergebnis in der Klasse Gleitpunktzahl!!\n");
        }

    }

    private static void printAdd(String x, String y) {
        System.out.println("    Fehler!\n      Es wurde gerechnet:            "
                + x + " + " + y);
    }

    private static void printSub(String x, String y) {
        System.out.println("    Fehler!\n      Es wurde gerechnet:            "
                + x + " - " + y + " = \n");
    }

    private static void printErg(String erg, String checkref) {
        System.out.println("      Ihr Ergebnis lautet:           " + erg
                + "\n      Das Korrekte Ergebnis lautet:  " + checkref + "\n");
    }

    private static void print_for_setDouble(double d, Gleitpunktzahl x) {
        System.out.println("\nDouble: " + Double.toString(d)
                + "\nGleitpunktzahl: " + x.toString()
                + "\nback to double: " + Double.toString(x.toDouble()));
    }

    private static void print_for_op(Gleitpunktzahl erg, Gleitpunktzahl ref) {
        System.out.println("\nReferenz: " + ref.toString()
                + "\nErgebnis: " + erg.toString());
    }

}
