package vista;

import logica.Simulacion;

public class Main {

    public static void main(String arg[]) {
        
        Simulacion simulacion = new Simulacion(5, 20000, 600, 100, 5, 127, 11, 100003, 200, 100);
        
        simulacion.simular();
    }

}