package packetworldapp;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import packetworldapp.dominio.UnidadImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Unidad;
import packetworldapp.utilidades.Utilidades;


public class FXMLMotivoBajaUnidadController implements Initializable{
    private Unidad unidadBaja;
    private INotificador observador;

    @FXML
    private TextArea taMotivo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarDatos(Unidad unidadBaja, INotificador observador) {
        this.unidadBaja = unidadBaja;
        this.observador = observador;
    }

    @FXML
    private void ClicBaja(ActionEvent event) throws UnsupportedEncodingException{
        String motivo = taMotivo.getText().trim();

        if (motivo.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Motivo requerido",
                    "Debes escribir el motivo de la baja",Alert.AlertType.WARNING);
            return;
        }
        Respuesta respuesta = UnidadImp.darBaja(unidadBaja, motivo);
        if (!respuesta.isError()) {
            Utilidades.mostrarAlertaSimple(
                "Unidad dada de baja",
                respuesta.getMensaje(),
                Alert.AlertType.INFORMATION
            );
            observador.notificarOperacionExitosa("Baja", unidadBaja.getModelo());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error",respuesta.getMensaje(),Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void ClicCancelar(ActionEvent event) {
        cerrarVentana();
    }
    
    private void cerrarVentana(){
        ((Stage)taMotivo.getScene().getWindow()).close();
    }
    
}
