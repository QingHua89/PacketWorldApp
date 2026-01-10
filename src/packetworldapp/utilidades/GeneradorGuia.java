package packetworldapp.utilidades;

import java.security.SecureRandom;

public class GeneradorGuia {

    private static final String prefijo = "PW-";
    private static final String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final int tamTotal = 13;
    private static final SecureRandom random = new SecureRandom();

    public static String generarGuiaEnvio() {
        int longitudAleatoria = tamTotal - prefijo.length();
        StringBuilder guia = new StringBuilder(tamTotal);
        guia.append(prefijo);

        for (int i = 0; i < longitudAleatoria; i++) {
            guia.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return guia.toString();
    }

}
