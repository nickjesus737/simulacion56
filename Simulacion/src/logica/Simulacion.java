package logica;

import java.util.ArrayList;

public class Simulacion {

    Metodos metodos;
    ArrayList<Double> variablesNormales;
    ArrayList<Double> numerosNormales;
    ArrayList<Double> numerosAleatorios;

    double costoComponente;
    double costoDesconexion;

    double componente1, componente2, componente3, componente4;
    double costo_total;

    int posicionVariableAleatoria;
    int tiempoSimulacion;
    int numeroCorridas;

    public Simulacion(int numeroCorridas, int tiempoSimulacion, double media, double desviacion, int x, int a, int c, int m, double costoComponente, double costoDesconexion) {
        metodos = new Metodos();

        this.numerosAleatorios = metodos.numerosAleatorios(x, a, c, m);
        this.numerosNormales = metodos.numeros_normales(numerosAleatorios);
        this.variablesNormales = metodos.variables_normales(media, desviacion, this.numerosNormales);

        this.costoComponente = costoComponente;
        this.costoDesconexion = costoDesconexion;

        this.componente1 = 0;
        this.componente2 = 0;
        this.componente3 = 0;
        this.componente4 = 0;

        this.posicionVariableAleatoria = 0;
        this.tiempoSimulacion = tiempoSimulacion;
        this.numeroCorridas = numeroCorridas;
    }

    public void asignaNumeroHorasVida() {
        this.componente1 = variablesNormales.get(posicionVariableAleatoria);
        posicionVariableAleatoria++;

        this.componente2 = variablesNormales.get(posicionVariableAleatoria);
        posicionVariableAleatoria++;

        this.componente3 = variablesNormales.get(posicionVariableAleatoria);
        posicionVariableAleatoria++;

        this.componente4 = variablesNormales.get(posicionVariableAleatoria);
        posicionVariableAleatoria++;
    }

    public void reduceHorasVida() {
        this.componente1 -= 0.01;
        this.componente2 -= 0.01;
        this.componente3 -= 0.01;
        this.componente4 -= 0.01;
    }

    public double primeraPolitica() {

        int contCostos = 0;
        int contDesconexion = 0;

        //Se le asigna a cada componente un tiempo de vida
        asignaNumeroHorasVida();

        for (double i = 0; i < this.tiempoSimulacion; i += 0.01) {

            if (metodos.redondear(this.componente1, 2) == 0.00) {

                contCostos++;
                contDesconexion++;
                this.componente1 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;
                i++;
            } else {
                this.componente1 -= 0.01;
            }

            if (metodos.redondear(this.componente2, 2) == 0.00) {

                contCostos++;
                contDesconexion++;
                this.componente2 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;
                i++;

            } else {
                this.componente2 -= 0.01;
            }

            if (metodos.redondear(this.componente3, 2) == 0.00) {

                contCostos++;
                contDesconexion++;
                this.componente3 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;
                i++;
            } else {
                this.componente3 -= 0.01;
            }

            if (metodos.redondear(this.componente4, 2) == 0.00) {

                contCostos++;
                contDesconexion++;
                this.componente4 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;
                i++;
            } else {
                this.componente4 -= 0.01;
            }

        }

        return contCostos * this.costoComponente + contDesconexion * this.costoDesconexion;

    }

    public double segundaPolitica() {

        int contCostos = 0;
        int contDesconexion = 0;

        //Se le asigna a cada componente un tiempo de vida
        asignaNumeroHorasVida();

        for (double i = 0; i < this.tiempoSimulacion; i += 0.01) {

            if ((metodos.redondear(this.componente1, 2) == 0.00) || (metodos.redondear(this.componente2, 2) == 0.00) || (metodos.redondear(this.componente3, 2) == 0.00) || (metodos.redondear(this.componente4, 2) == 0.00)) {
                contCostos += 4;
                contDesconexion += 2;
                asignaNumeroHorasVida();
                i += 2;
            }

            reduceHorasVida();
        }

        return contCostos * this.costoComponente + contDesconexion * this.costoDesconexion;

    }

    public void limpiar() {
        this.componente1 = 0;
        this.componente2 = 0;
        this.componente3 = 0;
        this.componente4 = 0;
    }

    public void simular() {

        double[] costosPolitica1 = new double[this.numeroCorridas];
        double[] costosPolitica2 = new double[this.numeroCorridas];

        double promedio1 = 0;
        double promedio2 = 0;

        double mayor1 = 0;
        double mayor2 = 0;
        double iguales = 0;

        for (int i = 1; i <= this.numeroCorridas; i++) {

            System.out.println("\nCorrida " + i);
            costosPolitica1[i - 1] = primeraPolitica();
            System.out.println("Total costos Politica 1: $" + costosPolitica1[i - 1]);
            costosPolitica2[i - 1] = segundaPolitica();
            System.out.println("Total costos Politica 2: $" + costosPolitica2[i - 1]);

            promedio1 += costosPolitica1[i - 1];
            promedio2 += costosPolitica2[i - 1];

            if (costosPolitica1[i - 1] > costosPolitica2[i - 1]) {
                mayor1++;
            } else {
                if (costosPolitica1[i - 1] < costosPolitica2[i - 1]) {
                    mayor2++;
                } else {
                    if (costosPolitica1[i - 1] == costosPolitica2[i - 1]) {
                        iguales++;
                    }
                }
            }

            limpiar();
        }

        promedio1 /= this.numeroCorridas;
        promedio2 /= this.numeroCorridas;

        mayor1 = metodos.redondear((mayor1 / this.numeroCorridas) * 100, 2);
        mayor2 = metodos.redondear((mayor2 / this.numeroCorridas) * 100, 2);
        iguales = metodos.redondear((iguales / this.numeroCorridas) * 100, 2);

        System.out.println("\nConclusiones Generales");
        System.out.println("Costo promedio politica 1: $" + promedio1);
        System.out.println("Costo promedio politica 2: $" + promedio2);

        System.out.println("\nProbabilidad de que la politica 1 sea mas barata: " + mayor1 + "%");
        System.out.println("Probabilidad de que la politica 2 sea mas barata: " + mayor2 + "%");
        System.out.println("Probabilidad de que ambas politicas cuesten lo mismo: " + iguales + "%");

    }

}
