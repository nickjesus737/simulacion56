package vista;

import java.util.ArrayList;
import logica.Metodos;

public class Main {

    public static void main(String arg[]) {
        
        Metodos me = new Metodos();
        
        ArrayList numerosAleatorios = me.numerosAleatorios(5, 127, 11, 100003);
        
        ArrayList generar_normales = me.numeros_normales(numerosAleatorios);
        
        ArrayList variables_normales = me.variables_normales(600, 100, generar_normales);
        
        System.out.println(variables_normales);
        
    }

}