package packetworldapp.pojo;

public class Unidad {
    private int idUnidad;
    private String marca;
    private String modelo;
    private int anio;
    private String vin;
    private String tipo;
    private String numeroInterno;
    private String estatus;
    private String motivoBaja;

    public Unidad() {
    }

    public Unidad(int idUnidad, String marca, String modelo, int anio, String vin, String tipo, String numeroInterno, String estatus, String motivoBaja) {
        this.idUnidad = idUnidad;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.vin = vin;
        this.tipo = tipo;
        this.numeroInterno = numeroInterno;
        this.estatus = estatus;
        this.motivoBaja = motivoBaja;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroInterno() {
        return numeroInterno;
    }

    public void setNumeroInterno(String numeroInterno) {
        this.numeroInterno = numeroInterno;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMotivoBaja() {
        return motivoBaja;
    }

    public void setMotivoBaja(String motivoBaja) {
        this.motivoBaja = motivoBaja;
    }
    
    @Override
    public String toString() {
        return "-NII:"+this.numeroInterno+" Marca:"+this.marca+" Modelo:"+this.modelo;
    }
}
