package packetworldapp.pojo;

public class RespuestaHTTP {
    
    private int codigo;
    private String Contenido;

    public RespuestaHTTP() {
    }

    public RespuestaHTTP(int codigo, String Contenido) {
        this.codigo = codigo;
        this.Contenido = Contenido;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getContenido() {
        return Contenido;
    }

    public void setContenido(String Contenido) {
        this.Contenido = Contenido;
    }
    
}