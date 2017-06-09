

public class Gauss {

	/**
	 * Diese Methode soll die Loesung x des LGS R*x=b durch
	 * Rueckwaertssubstitution ermitteln.
	 * PARAMETER: 
	 * R: Eine obere Dreiecksmatrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] backSubst(double[][] R, double[] b) {
		
                int n = b.length -1;
                final int m = b.length -1;   
                double[] x = new double[n+1];
                 
                //löst x von unten nach oben zeilenweise
                for( n = b.length -1 ; n >=0 ; n-- ){
                     
                    int l = n + 1;
                    double acc = 0;
                    //summiert alle Summanden der Zeile mit bereits bekanntem x auf
                    while(l<=m){
                        acc += (R[n][l] * x[l] );                       
                        l++;
                    }
                     
                     
                    x[n] = (-1) * ( acc - b[n] ) / R[n][n];
                     
                }
                return x;
	}
        
        
	/**
	 * Diese Methode soll die Loesung x des LGS A*x=b durch Gauss-Elimination mit
	 * Spaltenpivotisierung ermitteln. A und b sollen dabei nicht veraendert werden. 
	 * PARAMETER: A:
	 * Eine regulaere Matrix der Groesse n x n 
	 * b: Ein Vektor der Laenge n
	 */
	public static double[] solve(double[][] A, double[] b) {

                //kopieren da A und b unverändert bleiben sollen
                double[][] Acopy = A.clone();
                double[] bcopy = b.clone();
                int length = bcopy.length;
                for(int n = 0 ; n < length ; n++){
                    //größten Wert unter Diagonale in aktueller Spalte finden
                    double biggestValue = Acopy[n][n];
                    int biggestValueLine = n;
                    for(int i = n ; i<length ; i++){
                        double a;
                        if(Acopy[i][n] < 0){
                            a = -1 * Acopy[i][n];
                        }
                        else {
                            a = Acopy[i][n];
                        }
                        
                        if( a > biggestValue) {
                            biggestValue = a;
                            biggestValueLine = i;
                        }
                    }
                    //tauschen falls Wert auf Diagonale nicht der größte ist
                    if(biggestValueLine != n){
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
                    for (int i = (n+1); i < length; i++) {
                        if(Acopy[i][n] != 0){
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
	 * Gehen Sie dazu folgendermassen vor (vgl.Aufgabenblatt): 
	 * -Fuehren Sie zunaechst den Gauss-Algorithmus mit Spaltenpivotisierung 
	 *  solange durch, bis in einem Schritt alle moeglichen Pivotelemente
	 *  numerisch gleich 0 sind (d.h. <1E-10) 
	 * -Betrachten Sie die bis jetzt entstandene obere Dreiecksmatrix T und
	 *  loesen Sie Tx = -v durch Rueckwaertssubstitution 
	 * -Geben Sie den Vektor (x,1,0,...,0) zurueck
	 * 
	 * Sollte A doch intvertierbar sein, kann immer ein Pivot-Element gefunden werden(>=1E-10).
	 * In diesem Fall soll der 0-Vektor zurueckgegeben werden. 
	 * PARAMETER: 
	 * A: Eine singulaere Matrix der Groesse n x n 
	 */
	public static double[] solveSing(double[][] A) {
		//kopieren da A  unverändert bleiben soll
                double[][] Acopy = A.clone();
                int length = Acopy.length;
                for(int n = 0 ; n < length ; n++){
                    //größten Wert unter Diagonale in aktueller Spalte finden
                    double biggestValue = 0;
                    int biggestValueLine = -1;
                    for(int i = n ; i<length ; i++){
                        double a;
                        if(Acopy[i][n] < 0){
                            a = -1 * Acopy[i][n];
                        }
                        else {
                            a = Acopy[i][n];
                        }
                        
                        if( a > biggestValue) {
                            biggestValue = a;
                            biggestValueLine = i;
                        }
                    }
                    //kein Pivotelement gefunden
                    if(biggestValue < 0.0000000001){
                        //T aus Acopy rauskopieren
                        double[][] T = new double[n-1][n-1];
                        for(int i = 0; i< T.length; i++) {
                            for (int j = 0; j< T.length; j++) {
                                T[i][j] = Acopy[i][j];
                            }
                        }
                        //v aus Acopy rauskopieren und negieren
                        double[] v = new double[n-1];
                        for (int i= 0; i< v.length; i++) {
                            v[i] = (-1) * Acopy[i][n];
                        }
                        //x berechnen
                        double[] x = backSubst(T , v);
                        
                        //p zusammenstellen
                        double[] p = new double[Acopy.length];
                        for (int i= 0; i< p.length; i++) {
                            if( i  < x.length){
                                p[i] = x[i];
                            }else if( i == x.length){
                                p[i] = 1;
                            }else{
                                p[i] = 0;
                            }
                        }
                        
                        return p;
                    }
                    
                    
                    
                    //tauschen falls Wert auf Diagonale nicht der größte ist
                    if(biggestValueLine != n){
                        //Acopy
                        double[] tmp = Acopy[n];
                        Acopy[n] = Acopy[biggestValueLine];
                        Acopy[biggestValueLine] = tmp;
                        
                    }
                    
                    //Werte unter dem biggestValue auf 0 bringen
                    for (int i = (n+1); i < length; i++) {
                        if(Acopy[i][n] != 0){
                            double lineFactor = Acopy[i][n] / Acopy[n][n];
                            //Pivotzeile mit Faktor abziehen
                            for (int j = n; j < length; j++) {
                                Acopy[i][j] = Acopy[i][j] - (lineFactor * Acopy[n][j]);
                            }
                        }
                    }
                    
                }
                
                //falls regulär, und immer Pivot gefunden wird Nullvektor zurückgeben
                double[] nullVektor = new double[length];
                for (int i = 0; i < length; i ++) {
                    nullVektor[i] = 0;
                }
		return nullVektor;
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
