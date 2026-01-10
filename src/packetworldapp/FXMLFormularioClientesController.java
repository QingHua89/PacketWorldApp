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
import packetworldapp.dominio.ClienteImp;
import packetworldapp.dominio.SucursalImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Cliente;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioClientesController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfCalle;
    @FXML
    private TextField tfNumero;
    @FXML
    private TextField tfColonia;
    @FXML
    private TextField tfCodigoPostal;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfCorreo;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btCancelar;
    private INotificador observador;
    private Cliente clienteEdicion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarDatos(Cliente clienteEdicion, INotificador observador) {
        this.clienteEdicion = clienteEdicion;
        this.observador = observador;
        if (clienteEdicion != null) {
            tfNombre.setText(clienteEdicion.getNombre());
            tfApellidoPaterno.setText(clienteEdicion.getApellidoPaterno());
            tfApellidoMaterno.setText(clienteEdicion.getApellidoMaterno());
            tfCalle.setText(clienteEdicion.getCalle());
            tfNumero.setText(clienteEdicion.getNumero());
            tfColonia.setText(clienteEdicion.getColonia());
            tfCodigoPostal.setText(clienteEdicion.getCodigoPostal());
            tfTelefono.setText(clienteEdicion.getTelefono());
            tfCorreo.setText(clienteEdicion.getCorreo());
        }
    } 

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if (sonCamposValidos()) {
            Cliente cliente = new Cliente();
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidoPaterno(tfApellidoPaterno.getText());
            cliente.setApellidoMaterno(tfApellidoMaterno.getText());
            cliente.setCalle(tfCalle.getText());
            cliente.setNumero(tfNumero.getText());
            cliente.setColonia(tfColonia.getText());
            cliente.setCodigoPostal(tfCodigoPostal.getText());
            cliente.setTelefono(tfTelefono.getText());
            cliente.setCorreo(tfCorreo.getText());

            if (clienteEdicion == null) {
                registrarCliente(cliente);
            } else {
                cliente.setIdCliente(clienteEdicion.getIdCliente());
                editarCliente(cliente);
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
        String nombre = tfNombre.getText();
        String apePaterno = tfApellidoPaterno.getText();
        String apeMaterno = tfApellidoMaterno.getText();
        String calle = tfCalle.getText();
        String numero = tfNumero.getText();
        String colonia = tfColonia.getText();
        String cPostal = tfCodigoPostal.getText();
        String telefono = tfTelefono.getText();
        String correo = tfCorreo.getText();

        if (nombre == null || nombre.trim().isEmpty()
                || apePaterno == null || apePaterno.trim().isEmpty()
                || apeMaterno == null || apeMaterno.trim().isEmpty()
                || calle == null || calle.trim().isEmpty()
                || numero == null || numero.trim().isEmpty()
                || colonia == null || colonia.trim().isEmpty()
                || cPostal == null || cPostal.trim().isEmpty()
                || telefono == null || colonia.trim().isEmpty()
                || correo == null || colonia.trim().isEmpty()) {

            Utilidades.mostrarAlertaSimple("Campos incompletos",
                    "Por favor llena todos los campos.",
                    Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private void registrarCliente(Cliente cliente){
        Respuesta respuesta = ClienteImp.registrar(cliente);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Cliente Registrado",
                    "La información del cliente "+cliente.getNombre()+" ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", cliente.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarCliente(Cliente cliente){
        Respuesta respuesta = ClienteImp.editar(cliente);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Cliente modificado correctamente", 
                    "La información del cliente "+cliente.getNombre()+" ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", cliente.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
}
