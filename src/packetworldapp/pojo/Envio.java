package packetworldapp.pojo;

public class Envio {
    private int idEnvio;
    private String guia;
    private int idCliente;
    private int idSucursal;
    private double costo;
    private int idEstadoEnvio;
    private String estado;
    
    private String nombreCliente;
    private String apPaternoCliente; 
    private String apMaternoCliente;
    private String calleCliente;
    private String cpCliente;
    
    private String sucursal;
    private String sucursalCalle;
    private String sucursalCP;

    public Envio() {
    }

    public Envio(int idEnvio, String guia, int idCliente, int idSucursal, double costo, int idEstadoEnvio, String estado, String nombreCliente, String apPaternoCliente, String apMaternoCliente, String calleCliente, String cpCliente, String sucursal, String sucursalCalle, String sucursalCP) {
        this.idEnvio = idEnvio;
        this.guia = guia;
        this.idCliente = idCliente;
        this.idSucursal = idSucursal;
        this.costo = costo;
        this.idEstadoEnvio = idEstadoEnvio;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.apPaternoCliente = apPaternoCliente;
        this.apMaternoCliente = apMaternoCliente;
        this.calleCliente = calleCliente;
        this.cpCliente = cpCliente;
        this.sucursal = sucursal;
        this.sucursalCalle = sucursalCalle;
        this.sucursalCP = sucursalCP;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(int idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApPaternoCliente() {
        return apPaternoCliente;
    }

    public void setApPaternoCliente(String apPaternoCliente) {
        this.apPaternoCliente = apPaternoCliente;
    }

    public String getApMaternoCliente() {
        return apMaternoCliente;
    }

    public void setApMaternoCliente(String apMaternoCliente) {
        this.apMaternoCliente = apMaternoCliente;
    }

    public String getCalleCliente() {
        return calleCliente;
    }

    public void setCalleCliente(String calleCliente) {
        this.calleCliente = calleCliente;
    }

    public String getCpCliente() {
        return cpCliente;
    }

    public void setCpCliente(String cpCliente) {
        this.cpCliente = cpCliente;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getSucursalCalle() {
        return sucursalCalle;
    }

    public void setSucursalCalle(String sucursalCalle) {
        this.sucursalCalle = sucursalCalle;
    }

    public String getSucursalCP() {
        return sucursalCP;
    }

    public void setSucursalCP(String sucursalCP) {
        this.sucursalCP = sucursalCP;
    }
    
}