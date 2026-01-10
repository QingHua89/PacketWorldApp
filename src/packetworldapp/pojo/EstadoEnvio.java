package packetworldapp.pojo;

public class EstadoEnvio {
    private int idEstadoenvio;
    private String estado;

    public EstadoEnvio() {
    }

    public EstadoEnvio(int idEstadoenvio, String estado) {
        this.idEstadoenvio = idEstadoenvio;
        this.estado = estado;
    }

    public int getIdEstadoenvio() {
        return idEstadoenvio;
    }

    public void setIdEstadoenvio(int idEstadoenvio) {
        this.idEstadoenvio = idEstadoenvio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    @Override
    public String toString() {
        return "-"+this.estado;
    }
}
