package packetworldapp.pojo;

public class Colaborador {

    private int idColaborador;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String numeroPersonal;
    private String password;
    private String curp;
    private String correo;
    private String numeroLicencia;
    private int idRol;
    private String rol;
    private int idSucursal;
    private String sucursalNombre;
    private byte[] fotografia;
    private String fotoBase64;

    public Colaborador() {
    }

    public Colaborador(int idColaborador, String nombre, String apellidoPaterno, String apellidoMaterno, String numeroPersonal, String password, String curp, String correo, String numeroLicencia, int idRol, String rol, int idSucursal, String sucursalNombre, byte[] fotografia, String fotoBase64) {
        this.idColaborador = idColaborador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.numeroPersonal = numeroPersonal;
        this.password = password;
        this.curp = curp;
        this.correo = correo;
        this.numeroLicencia = numeroLicencia;
        this.idRol = idRol;
        this.rol = rol;
        this.idSucursal = idSucursal;
        this.sucursalNombre = sucursalNombre;
        this.fotografia = fotografia;
        this.fotoBase64 = fotoBase64;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNumeroPersonal() {
        return numeroPersonal;
    }

    public void setNumeroPersonal(String numeroPersonal) {
        this.numeroPersonal = numeroPersonal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getSucursalNombre() {
        return sucursalNombre;
    }

    public void setSucursalNombre(String sucursalNombre) {
        this.sucursalNombre = sucursalNombre;
    }

    public byte[] getFotografia() {
        return fotografia;
    }

    public void setFotografia(byte[] fotografia) {
        this.fotografia = fotografia;
    }

    public String getFotoBase64() {
        return fotoBase64;
    }

    public void setFotoBase64(String fotoBase64) {
        this.fotoBase64 = fotoBase64;
    }
    
    @Override
    public String toString() {
        return "-N.P:"+this.numeroPersonal+" Nombre:"+this.nombre+" "+this.apellidoPaterno+" "+this.apellidoMaterno;
    }
}
