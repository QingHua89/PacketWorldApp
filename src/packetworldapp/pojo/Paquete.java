package packetworldapp.pojo;

public class Paquete {
    private int idPaquete;
    private int idEnvio;
    private String descripcion;
    private float peso;
    private float alto;
    private float ancho;
    private float profundidad;
    private String envio;
    private String guia;

    public Paquete() {
    }

    public Paquete(int idPaquete, int idEnvio, String descripcion, float peso, float alto, float ancho, float profundidad, String envio, String guia) {
        this.idPaquete = idPaquete;
        this.idEnvio = idEnvio;
        this.descripcion = descripcion;
        this.peso = peso;
        this.alto = alto;
        this.ancho = ancho;
        this.profundidad = profundidad;
        this.envio = envio;
        this.guia = guia;
    }

    public int getIdPaquete() {
        return idPaquete;
    }

    public void setIdPaquete(int idPaquete) {
        this.idPaquete = idPaquete;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(float profundidad) {
        this.profundidad = profundidad;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }
    
    @Override
    public String toString() {
        return "- Paquete:"+this.descripcion;
    }
}
