package packetworldapp.utilidades;

import java.security.InvalidAlgorithmParameterException;
import java.util.Map;
import java.util.TreeMap;

public class Calculador {
    public static TreeMap<Integer, Double> rangoCostoPorKilometro = new TreeMap<>();

    static {
        rangoCostoPorKilometro.put(1, 4.00);
        rangoCostoPorKilometro.put(201, 3.00);
        rangoCostoPorKilometro.put(501, 2.00);
        rangoCostoPorKilometro.put(1001, 1.00);
        rangoCostoPorKilometro.put(2001, 0.50);
    }

    public static Double calcularCostosDeEnvio(double distancia, int conteoPaquetes) throws Exception {
        double costoResultado = 0.0;
        double costosAdicionales = 0.0;

        switch (conteoPaquetes) {
            case 0: throw new Exception("Debe existir por lo menos un paquete para hacer un envío");
            case 1: costosAdicionales = 0; break;
            case 2: costosAdicionales = 50; break;
            case 3: costosAdicionales = 80; break;
            case 4: costosAdicionales = 110; break;
            default: costosAdicionales = 150; break;
        }

        costoResultado = (distancia * rangoCostoPorKilometro.floorEntry((int) distancia).getValue()) + costosAdicionales;
        return costoResultado;
    }
}