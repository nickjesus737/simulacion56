package logica;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class Metodos {

    public Metodos() {
    }

    //generador de numeros aleatorios con el metodo congruencial mixto
    public ArrayList numerosAleatorios(int x, int a, int c, int m) {

        ArrayList<Double> numerosAleatorios = new ArrayList<>();

        double X = x;
        double aux;
        double pro;

        aux = a * X + c;
        X = aux % m;
        pro = X;

        numerosAleatorios.add(X / m);

        do {
            aux = a * X + c;
            X = aux % m;
            numerosAleatorios.add(X / m);
        } while (pro != X);

        return numerosAleatorios;
    }

    public ArrayList<Double> numeros_normales(ArrayList<Double> numerosAleatorios) {

        ArrayList<Double> numerosNormales = new ArrayList<>();

        for (int i = 0; i < numerosAleatorios.size(); i++) {

            if (i % 2 == 0) {
                numerosNormales.add(Math.sqrt(-2 * Math.log(numerosAleatorios.get(i))) * Math.cos(2 * Math.PI * numerosAleatorios.get(i + 1)));

            } else {
                numerosNormales.add(Math.sqrt(-2 * Math.log(numerosAleatorios.get(i - 1))) * Math.sin(2 * Math.PI * numerosAleatorios.get(i)));
            }
        }

        return numerosNormales;
    }

    public ArrayList<Double> variables_normales(double media, double desviacion, ArrayList<Double> numerosNormales) {

        ArrayList<Double> variableNormal = new ArrayList<>();

        for (int i = 0; i < numerosNormales.size(); i++) {
            variableNormal.add(redondear(media + desviacion * numerosNormales.get(i), 2));
        }

        return variableNormal;
    }

    //metodo para redondear numeros double a dos decimales
    public double redondear(double decimal, int escala) {

        BigDecimal bd = new BigDecimal(decimal).setScale(escala, RoundingMode.HALF_UP);

        return Double.parseDouble(bd.toString());
    }

}
