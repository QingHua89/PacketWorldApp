package packetworldapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.PaqueteImp;
import packetworldapp.dominio.UnidadImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Paquete;
import packetworldapp.pojo.Unidad;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioPaquetesController implements Initializable {

    @FXML
    private TextField tfAlto;
    @FXML
    private TextField tfAncho;
    @FXML
    private TextField tfProfundidad;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextArea taDescripcion;
    
    private INotificador observador;
    private Colaborador colaboradorEdicion;
    private Paquete paqueteEdicion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void inicializarDatos(Paquete paqueteEdicion, INotificador observador) {
        this.paqueteEdicion = paqueteEdicion;
        this.observador = observador;
        if(paqueteEdicion != null){
            tfAlto.setText(String.valueOf(paqueteEdicion.getAlto()));
            tfAncho.setText(String.valueOf(paqueteEdicion.getAncho()));
            tfProfundidad.setText(String.valueOf(paqueteEdicion.getProfundidad()));     
            tfPeso.setText(String.valueOf(paqueteEdicion.getPeso()));
            taDescripcion.setText(paqueteEdicion.getDescripcion()); 
        }
    }

    @FXML
    private void ClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void ClicAceptar(ActionEvent event) {
        if(sonCamposValidos()){
            Paquete paquete = new Paquete();
            paquete.setAlto(Float.parseFloat(tfAlto.getText()));
            paquete.setAncho(Float.parseFloat(tfAncho.getText()));
            paquete.setProfundidad(Float.parseFloat(tfProfundidad.getText()));
            paquete.setPeso(Float.parseFloat(tfPeso.getText()));
            paquete.setDescripcion(taDescripcion.getText());
            
            if (paqueteEdicion == null){
                registrarPaquete(paquete);
            }else{
                paquete.setIdPaquete(paqueteEdicion.getIdPaquete());
                editarPaquete(paquete);
            }
            cerrarVentana();
        }
    }
    
    private void cerrarVentana(){
        ((Stage)taDescripcion.getScene().getWindow()).close();
    }
    
    private boolean sonCamposValidos() {
        String alto = tfAlto.getText();
        String ancho = tfAncho.getText();
        String profundidad = tfProfundidad.getText();
        String peso = tfPeso.getText();
        String descripcion = taDescripcion.getText();

        if (alto == null || alto.trim().isEmpty()
                || ancho == null || ancho.trim().isEmpty()
                || profundidad == null || profundidad.trim().isEmpty()
                || peso == null || peso.trim().isEmpty()
                || descripcion == null || descripcion.trim().isEmpty()) {

            Utilidades.mostrarAlertaSimple("Campos incompletos",
                    "Por favor llena todos los campos.",
                    Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }
    
    private void registrarPaquete(Paquete paquete){
        Respuesta respuesta = PaqueteImp.registrar(paquete);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Paquete Creado",
                    "La información de la unidadha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", paquete.getDescripcion());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarPaquete(Paquete paquete){
        Respuesta respuesta = PaqueteImp.editar(paquete);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Paquete modificado correctamente", 
                    "La información del paquete ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", paquete.getDescripcion());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
}
