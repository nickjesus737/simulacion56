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

    public Simulacion(int tiempoSimulacion, double media, double desviacion, int x, int a, int c, int m, double costoComponente, double costoDesconexion) {
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

        this.costo_total = 0;
        this.posicionVariableAleatoria = 0;
        this.tiempoSimulacion = tiempoSimulacion;
    }

    public double primeraPolitica() {

        int contCostos = 0;
        int contDesconexion = 0;

        //Se le asigna a cada componente un tiempo de vida
        asignaNumeroHorasVida();

        for (double i = 0; i < this.tiempoSimulacion; i += 0.01) {

            if (metodos.redondear(this.componente1, 2) == 0) {

                contCostos++;
                contDesconexion++;
                this.componente1 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;

            } else {
                this.componente1 -= 0.01;
            }

            if (metodos.redondear(this.componente2, 2) == 0) {

                contCostos++;
                contDesconexion++;
                this.componente2 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;

            } else {
                this.componente2 -= 0.01;
            }

            if (metodos.redondear(this.componente3, 2) == 0) {

                contCostos++;
                contDesconexion++;
                this.componente3 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;

            } else {
                this.componente3 -= 0.01;
            }

            if (metodos.redondear(this.componente4, 2) == 0) {

                contCostos++;
                contDesconexion++;
                this.componente4 = variablesNormales.get(posicionVariableAleatoria);
                posicionVariableAleatoria++;

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

            if ((metodos.redondear(this.componente1, 2) == 0) || (metodos.redondear(this.componente2, 2) == 0) || (metodos.redondear(this.componente3, 2) == 0) || (metodos.redondear(this.componente4, 2) == 0)) {
                contCostos += 4;
                contDesconexion += 2;
                asignaNumeroHorasVida();
            }

            reduceHorasVida();
        }

        return contCostos * this.costoComponente + contDesconexion * this.costoDesconexion;

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

}
