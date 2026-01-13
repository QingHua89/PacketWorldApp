package packetworldapp.pojo;

public class SucursalNombres {
    private int idSucursal;
    private String sucursalNombre;
    private String codigoPostal;

    public SucursalNombres() {
    }

    public SucursalNombres(int idSucursal, String sucursalNombre, String codigoPostal) {
        this.idSucursal = idSucursal;
        this.sucursalNombre = sucursalNombre;
        this.codigoPostal = codigoPostal;
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
    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }
    @Override
    public String toString() {
        return "-"+this.sucursalNombre;
    }
}
