package packetworldapp.pojo;

public class SucursalNombres {
    private int idSucursal;
    private String sucursalNombre;

    public SucursalNombres() {
    }

    public SucursalNombres(int idSucursal, String sucursalNombre) {
        this.idSucursal = idSucursal;
        this.sucursalNombre = sucursalNombre;
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
    
    @Override
    public String toString() {
        return "-"+this.sucursalNombre;
    }
}
