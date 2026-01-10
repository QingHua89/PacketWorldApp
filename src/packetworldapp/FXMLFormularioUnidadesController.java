package packetworldapp;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.UnidadImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Unidad;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioUnidadesController implements Initializable {

    @FXML
    private TextField tfMarca;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfAnio;
    @FXML
    private TextField tfVin;
    @FXML
    private TextField tfNII;
    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<String> cbTipo;
    private INotificador observador;
    @FXML
    private Button btCancelar;
    
    private Unidad unidadEdicion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarTipos();
    }
    
    public void inicializarDatos(Unidad unidadEdicion, INotificador observador) {
        this.unidadEdicion = unidadEdicion;
        this.observador = observador;
        if (unidadEdicion != null) {
            tfMarca.setText(unidadEdicion.getMarca());
            tfModelo.setText(unidadEdicion.getModelo());
            tfAnio.setText(String.valueOf(unidadEdicion.getAnio()));     
            tfVin.setText(unidadEdicion.getVin());       
            tfNII.setText(unidadEdicion.getNumeroInterno());             
            tfEstatus.setText(unidadEdicion.getEstatus());
            cbTipo.setValue(unidadEdicion.getTipo());
            tfVin.setDisable(true);
        }
    }    

    @FXML
    private void ClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void ClicGuardar(ActionEvent event) {
        if(sonCamposValidos()){
            Unidad unidad = new Unidad();
            unidad.setMarca(tfMarca.getText());
            unidad.setModelo(tfModelo.getText());
            unidad.setAnio(Integer.parseInt(tfAnio.getText()));
            unidad.setVin(tfVin.getText());
            unidad.setNumeroInterno(tfNII.getText());
            unidad.setEstatus(tfEstatus.getText());
            unidad.setTipo(cbTipo.getValue());
            
            if (unidadEdicion == null){
                registrarUnidad(unidad);
            }else{
                unidad.setIdUnidad(unidadEdicion.getIdUnidad());
                editarUnidad(unidad);
            }
            cerrarVentana();
        }
        
    }
    private void cerrarVentana(){
        ((Stage)btCancelar.getScene().getWindow()).close();
    }

    private boolean sonCamposValidos() {
        String marca = tfMarca.getText();
        String modelo = tfModelo.getText();
        String anioStr = tfAnio.getText();
        String vin = tfVin.getText();
        String nii = tfNII.getText();
        String estatus = tfEstatus.getText();
        String tipo = cbTipo.getValue();

        if (marca == null || marca.trim().isEmpty()
                || modelo == null || modelo.trim().isEmpty()
                || anioStr == null || anioStr.trim().isEmpty()
                || vin == null || vin.trim().isEmpty()
                || nii == null || nii.trim().isEmpty()
                || estatus == null || estatus.trim().isEmpty()
                || tipo == null || tipo.trim().isEmpty()) {

            Utilidades.mostrarAlertaSimple("Campos incompletos",
                    "Por favor llena todos los campos obligatorios.",
                    Alert.AlertType.WARNING);
            return false;
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (NumberFormatException e) {
            Utilidades.mostrarAlertaSimple("Año inválido",
                    "El año debe ser un número entero.",
                    Alert.AlertType.WARNING);
            return false;
        }

        if (vin.length() != 17) {
            Utilidades.mostrarAlertaSimple("VIN inválido",
                    "El VIN debe tener exactamente 17 caracteres.",
                    Alert.AlertType.WARNING);
            return false;
        }

        String niiEsperado = anio + vin.substring(0, 4);
        if (!nii.equals(niiEsperado)) {
            Utilidades.mostrarAlertaSimple("Número interno inválido",
                    "El número de identificación interno debe ser: " + niiEsperado,
                    Alert.AlertType.WARNING);
            return false;
        }

        return true;
    }

    private void registrarUnidad(Unidad unidad){
        Respuesta respuesta = UnidadImp.registrar(unidad);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Undiad registrada",
                    "La información de la unidad "+unidad.getMarca()+" ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", unidad.getMarca());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarUnidad(Unidad unidad){
        Respuesta respuesta = UnidadImp.editar(unidad);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Unidad modificada correctamente", 
                    "La información de la unidad "+unidad.getMarca()+" ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", unidad.getMarca());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarTipos() {
        cbTipo.getItems().setAll(
                "Gasolina",
                "Diesel",
                "Eléctrica",
                "Hibrida"
        );
    }

}
