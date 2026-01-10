package packetworldapp.pojo;

public class AsignarUnidad {
    private int idAsignacion;
    private int idColaborador;
    private int idUnidad;
    private String colaboradorNombre;
    private String unidadNII;
    private String fechaAsignacion;
    private String fechaDesasignacion;

    public AsignarUnidad() {
    }

    public AsignarUnidad(int idAsignacion, int idColaborador, int idUnidad, String colaboradorNombre, String unidadNII, String fechaAsignacion, String fechaDesasignacion) {
        this.idAsignacion = idAsignacion;
        this.idColaborador = idColaborador;
        this.idUnidad = idUnidad;
        this.colaboradorNombre = colaboradorNombre;
        this.unidadNII = unidadNII;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaDesasignacion = fechaDesasignacion;
    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }

    public String getColaboradorNombre() {
        return colaboradorNombre;
    }

    public void setColaboradorNombre(String colaboradorNombre) {
        this.colaboradorNombre = colaboradorNombre;
    }

    public String getUnidadNII() {
        return unidadNII;
    }

    public void setUnidadNII(String unidadNII) {
        this.unidadNII = unidadNII;
    }

    public String getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(String fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getFechaDesasignacion() {
        return fechaDesasignacion;
    }

    public void setFechaDesasignacion(String fechaDesasignacion) {
        this.fechaDesasignacion = fechaDesasignacion;
    }
    
}
