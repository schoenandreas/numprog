
public class Gauss {

    /**
     * Diese Methode soll die Loesung x des LGS R*x=b durch
     * Rueckwaertssubstitution ermitteln. PARAMETER: R: Eine obere
     * Dreiecksmatrix der Groesse n x n b: Ein Vektor der Laenge n
     */
    public static double[] backSubst(double[][] R, double[] b) {

        //Groesse n x n
        int n = R.length;

        //Loesungsvektor initialisieren
        double[] x = new double[n];

        //bereits berechnete Zeilen
        int k = 0;

        //löst x von unten nach oben zeilenweise
        for (int i = n - 1; i >= 0; i--) {

            //Akkumulator
            double acc = 0.0;

            //summiert alle Summanden der Zeile mit bereits bekanntem x auf
            for (int j = n - 1; j > n - k; j--) {
                acc += x[j] * R[i][j];
            }

            x[i] = (b[i] - acc) / R[i][i];

            //naechste Zeile wurde berechnet
            k++;
        }

        return x;
    }

    /**
     * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination
     * mit Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert
     * werden. PARAMETER: A: Eine regulaere Matrix der Groesse n x n b: Ein
     * Vektor der Laenge n
     */
    public static double[] solve(double[][] A, double[] b) {

        //kopieren da A und b unverändert bleiben sollen
        double[][] Acopy = A.clone();
        double[] bcopy = b.clone();
        int length = bcopy.length;
        for (int n = 0; n < length; n++) {

            //größten Wert unter Diagonale in aktueller Spalte finden
            double biggestValue = Acopy[n][n];
            int biggestValueLine = n;
            for (int i = n; i < length; i++) {
                double a;
                if (Acopy[i][n] < 0) {
                    a = -1 * Acopy[i][n];
                } else {
                    a = Acopy[i][n];
                }

                if (a > biggestValue) {
                    biggestValue = a;
                    biggestValueLine = i;
                }
            }

            //tauschen falls Wert auf Diagonale nicht der größte ist
            if (biggestValueLine != n) {
                //Acopy
                double[] tmp = Acopy[n];
                Acopy[n] = Acopy[biggestValueLine];
                Acopy[biggestValueLine] = tmp;

                //bcopy
                double tmp2 = bcopy[n];
                bcopy[n] = bcopy[biggestValueLine];
                bcopy[biggestValueLine] = tmp2;
            }

            //Werte unter dem biggestValue auf 0 bringen
            for (int i = (n + 1); i < length; i++) {
                if (Acopy[i][n] != 0) {
                    double lineFactor = Acopy[i][n] / Acopy[n][n];

                    //Pivotzeile mit Faktor abziehen
                    for (int j = n; j < length; j++) {
                        Acopy[i][j] = Acopy[i][j] - (lineFactor * Acopy[n][j]);
                    }

                    bcopy[i] = bcopy[i] - (lineFactor * bcopy[n]);
                }
            }

        }

        return backSubst(Acopy, bcopy);
    }

    /**
     * Diese Methode soll eine Loesung p!=0 des LGS A*p=0 ermitteln. A ist dabei
     * eine nicht invertierbare Matrix. A soll dabei nicht veraendert werden.
     *
     * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): -Fuehren Sie
     * zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung solange durch,
     * bis in einem Schritt alle moeglichen Pivotelemente numerisch gleich 0
     * sind (d.h. <1E-10)
     * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
     *  loesen Sie Tx = -v durch Rueckwaertssubstitution
     * -Geben Sie den Vektor (x,1,0,...,0) zurueck
     *
     * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
     * In diesem Fall soll der 0-Vektor zurueckgegeben werden. PARAMETER: A:
     * Eine singulaere Matrix der Groesse n x n
     */
    public static double[] solveSing(double[][] A) {
        //kopieren da A unveraendert bleiben soll
        double[][] A_ = A.clone();
        double[] result;

        double pMin = 0.0000000001;
        boolean minReached = false;

        int n = A_.length;
        int rowReached = 0;

        //Gauss-Elimination mit Spalten-Pivotsuche
        for (int i = 0; i < n; i++) {

            //Suche betragsgroesstes Pivot-Element >= i (unserem Eliminationsschritt)
            int p = i;
            for (int j = i; j < n; j++){
                if (Math.abs(A_[j][i]) > Math.abs(A_[p][i])) p = j;
                
                if (Math.abs(A_[j][i]) < pMin) minReached |= true;
            }
            
            rowReached = i + 1;
            //Verlassen der Schleife, falls keine gueltigen Pivots gefunden werden koennen
            if(minReached) break;
            
            //vertausche die Zeilen
            if (p != i) swapRow(A_, p, i);
            
            //Elimination
            for (int j = i + 1; j < n; j++){
                
                //untere Dreiecksmatrix sollte tatsaechlich 0 sein
                A_[j][i] = 0.0;
                
                //Elimination der restlichen Elemente der Zeile
                for (int k = i + 1; k < n; k++){
                    double x = A_[k][i] / A_[i][i];
                    A[k][i] -= x;
                }
            }
        }
        
        /*
        Util.printMatrix(A);
        System.out.println();
        Util.printMatrix(A_);
        System.out.println();
        */
        
        if (rowReached == n) {
            double[] nullVector = new double[n];
            return nullVector;
        }
        
        //T und -v initialisieren
        double[][] T = new double[rowReached][rowReached];
        double[] _v = new double[rowReached];
        
        /*
        Util.printMatrix(T);
        System.out.println();
        Util.printVector(_v);
        System.out.println();
        */
        
        //befuellen
        for (int i = 0; i < rowReached; i++){
            
            _v[i] = A_[i][rowReached];
            for (int j = 0; j < rowReached; j++){
                T[i][j] = A_[i][j];
            }
        }
        
        return backSubst(T, _v);
    }

    //Zeile i wird mit j vertauscht
    private static void swapRow(double[][] A, int j, int k) {
        double tmp;
        for (int i = 0; i < A.length; i++) {
            tmp = A[j][i];
            A[j][i] = A[k][i];
            A[k][i] = tmp;
        }
    }

    /**
     * Diese Methode berechnet das Matrix-Vektor-Produkt A*x mit A einer nxm
     * Matrix und x einem Vektor der Laenge m. Sie eignet sich zum Testen der
     * Gauss-Loesung
     */
    public static double[] matrixVectorMult(double[][] A, double[] x) {
        int n = A.length;
        int m = x.length;

        double[] y = new double[n];

        for (int i = 0; i < n; i++) {
            y[i] = 0;
            for (int j = 0; j < m; j++) {
                y[i] += A[i][j] * x[j];
            }
        }

        return y;
    }
}
