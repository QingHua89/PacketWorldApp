package packetworldapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.SucursalImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioSucursalesController implements Initializable {

    @FXML
    private TextField tfCodSucursal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfEstatus;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfCiudad;
    @FXML
    private TextField tfEstado;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btCancelar;
    private INotificador observador;
    private Sucursal sucursalEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarDatos(Sucursal sucursalEdicion, INotificador observador) {
        this.sucursalEdicion = sucursalEdicion;
        this.observador = observador;
        if (sucursalEdicion != null) {
            tfCodSucursal.setText(sucursalEdicion.getCodigoSucursal());
            tfNombre.setText(sucursalEdicion.getSucursalNombre());
            tfEstatus.setText(sucursalEdicion.getEstatus());
            tfCalle.setText(sucursalEdicion.getCalle());
            tfNumero.setText(sucursalEdicion.getNumero());
            tfColonia.setText(sucursalEdicion.getColonia());
            tfCodigoPostal.setText(sucursalEdicion.getCodigoPostal());
            tfCiudad.setText(sucursalEdicion.getCiudad());
            tfEstado.setText(sucursalEdicion.getEstado());
            tfCodSucursal.setDisable(true);
            tfEstatus.setDisable(true);
        }
    }    

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if(sonCamposValidos()){
            Sucursal sucursal = new Sucursal();
            sucursal.setCodigoSucursal(tfCodSucursal.getText());
            sucursal.setSucursalNombre(tfNombre.getText());
            sucursal.setEstatus(tfEstatus.getText());
            sucursal.setCalle(tfCalle.getText());
            sucursal.setNumero(tfNumero.getText());
            sucursal.setColonia(tfColonia.getText());
            sucursal.setCodigoPostal(tfCodigoPostal.getText());
            sucursal.setCiudad(tfCiudad.getText());
            sucursal.setEstado(tfEstado.getText());
            
            if (sucursalEdicion == null){
                registrarSucursal(sucursal);
            }else{
                sucursal.setIdSucursal(sucursalEdicion.getIdSucursal());
                editarSucursal(sucursal);
            }
            cerrarVentana();
        }
        
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        ((Stage)btCancelar.getScene().getWindow()).close();
    }
       
    private boolean sonCamposValidos() {
        String codSucursal = tfCodSucursal.getText();
        String nombre = tfNombre.getText();
        String estatus = tfEstatus.getText();
        String calle = tfCalle.getText();
        String numero = tfNumero.getText();
        String colonia = tfColonia.getText();
        String codigoPostal = tfCodigoPostal.getText();
        String ciudad = tfCiudad.getText();
        String estado = tfEstado.getText();

        if (codSucursal == null || codSucursal.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || estatus == null || estatus.trim().isEmpty()
                || calle == null || calle.trim().isEmpty()
                || numero == null || numero.trim().isEmpty()
                || colonia == null || colonia.trim().isEmpty()
                || codigoPostal == null || codigoPostal.trim().isEmpty()
                || ciudad == null || ciudad.trim().isEmpty()
                || estado == null || estado.trim().isEmpty()) {

            Utilidades.mostrarAlertaSimple("Campos incompletos",
                    "Por favor llena todos los campos obligatorios.",
                    Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private void registrarSucursal(Sucursal sucursal){
        Respuesta respuesta = SucursalImp.registrar(sucursal);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Sucursal registrada",
                    "La información de la sucural "+sucursal.getSucursalNombre()+" ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", sucursal.getSucursalNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarSucursal(Sucursal sucursal){
        Respuesta respuesta = SucursalImp.editar(sucursal);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Sucursal modificada correctamente", 
                    "La información de la sucursal "+sucursal.getSucursalNombre()+" ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", sucursal.getSucursalNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    
}
