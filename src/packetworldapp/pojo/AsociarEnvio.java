package packetworldapp.pojo;

public class AsociarEnvio {
    private int idAsociar;
    private int idEnvio;
    private int idColaborador;
    private String guiaEnvio;
    private String colaboradorNumero;

    public AsociarEnvio() {
    }

    public AsociarEnvio(int idAsociar, int idEnvio, int idColaborador, String guiaEnvio, String colaboradorNumero) {
        this.idAsociar = idAsociar;
        this.idEnvio = idEnvio;
        this.idColaborador = idColaborador;
        this.guiaEnvio = guiaEnvio;
        this.colaboradorNumero = colaboradorNumero;
    }

    public int getIdAsociar() {
        return idAsociar;
    }

    public void setIdAsociar(int idAsociar) {
        this.idAsociar = idAsociar;
    }

    public int getIdEnvio() {
        return idEnvio;
    }

    public void setIdEnvio(int idEnvio) {
        this.idEnvio = idEnvio;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getGuiaEnvio() {
        return guiaEnvio;
    }

    public void setGuiaEnvio(String guiaEnvio) {
        this.guiaEnvio = guiaEnvio;
    }

    public String getColaboradorNumero() {
        return colaboradorNumero;
    }

    public void setColaboradorNumero(String colaboradorNumero) {
        this.colaboradorNumero = colaboradorNumero;
    }
    
}
