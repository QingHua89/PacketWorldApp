package packetworldapp.pojo;

public class HistorialCambios {
    private int idHistorial;
    private String guia;
    private int idEstadoEnvio;
    private int idColaborador;
    private String motivo;
    private String fechaHoraCambio;

    public HistorialCambios() {
    }

    public HistorialCambios(int idHistorial, String guia, int idEstadoEnvio, int idColaborador, String motivo, String fechaHoraCambio) {
        this.idHistorial = idHistorial;
        this.guia = guia;
        this.idEstadoEnvio = idEstadoEnvio;
        this.idColaborador = idColaborador;
        this.motivo = motivo;
        this.fechaHoraCambio = fechaHoraCambio;
    }

    public int getIdHistorial() {
        return idHistorial;
    }

    public void setIdHistorial(int idHistorial) {
        this.idHistorial = idHistorial;
    }

    public String getGuia() {
        return guia;
    }

    public void setGuia(String guia) {
        this.guia = guia;
    }

    public int getIdEstadoEnvio() {
        return idEstadoEnvio;
    }

    public void setIdEstadoEnvio(int idEstadoEnvio) {
        this.idEstadoEnvio = idEstadoEnvio;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFechaHoraCambio() {
        return fechaHoraCambio;
    }

    public void setFechaHoraCambio(String fechaHoraCambio) {
        this.fechaHoraCambio = fechaHoraCambio;
    }
    
}
