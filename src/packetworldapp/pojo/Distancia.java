package packetworldapp.pojo;

public class Distancia {
    private double distanciaKM;
    private boolean error;
    private String mensaje;

    public Distancia() {
    }

    public Distancia(double distanciaKM, boolean error, String mensaje) {
        this.distanciaKM = distanciaKM;
        this.error = error;
        this.mensaje = mensaje;
    }

    public double getDistanciaKM() {
        return distanciaKM;
    }

    public void setDistanciaKM(double distanciaKM) {
        this.distanciaKM = distanciaKM;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
