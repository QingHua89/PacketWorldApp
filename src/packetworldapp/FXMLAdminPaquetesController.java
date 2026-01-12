
package packetworldapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldapp.dominio.ColaboradorImp;
import packetworldapp.dominio.PaqueteImp;
import packetworldapp.dominio.SucursalImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Paquete;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminPaquetesController implements Initializable, INotificador {

    @FXML
    private ImageView ivAtras;
    @FXML
    private TableView<Paquete> tvPaquetes;
    @FXML
    private TableColumn tcGuia;
    @FXML
    private TableColumn tcDescripcion;
    @FXML
    private TableColumn tcPeso;
    @FXML
    private TableColumn tcAlto;
    @FXML
    private TableColumn tcAncho;
    @FXML
    private TableColumn tcProfundidad;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btBuscar;
    private Colaborador colaboradorSesion;
    private ObservableList<Paquete> paquetes;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla ();
        cargarInformacionPaquetes();
    }
    
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }

    @FXML
    private void ClicAtras(MouseEvent event) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLPrincipal.fxml"));
            Parent vista = cargador.load();

            FXMLPrincipalController controlador = cargador.getController();
            controlador.inicializarDatos(colaboradorSesion);

            Stage stage = (Stage) ivAtras.getScene().getWindow();
            Scene escena = new Scene(vista);
            stage.setScene(escena);
            stage.setTitle("Principal");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicBuscar(ActionEvent event) {
        cargarBusqueda(tfBuscar.getText());
    }

    @FXML
    private void ClicNuevo(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void ClicEditar(ActionEvent event) {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if (paquete !=null){
            irFormulario(paquete);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un paquete",
                    "Para editar un paquete, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicDesvingular(ActionEvent event) throws UnsupportedEncodingException {
        Paquete paquete = tvPaquetes.getSelectionModel().getSelectedItem();
        if (paquete !=null){
            boolean confirmacion = Utilidades.mostrarAlertaConformacion("Baja",
                    "¿Desea quitar este paquete del envio actual?");
            if(confirmacion){
                quitarPaquete(paquete);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un paquete",
                    "Para desvincular un paquete, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }
    
    private void configurarTabla (){
        tcGuia.setCellValueFactory(new PropertyValueFactory("guia"));
        tcDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
        tcPeso.setCellValueFactory(new PropertyValueFactory("peso"));
        tcAlto.setCellValueFactory(new PropertyValueFactory("alto"));
        tcAncho.setCellValueFactory(new PropertyValueFactory("ancho"));
        tcProfundidad.setCellValueFactory(new PropertyValueFactory("profundidad"));
    }
    private void cargarInformacionPaquetes(){
        HashMap<String, Object> respuesta = PaqueteImp.obtenerPaquetes();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Paquete> paquetesAPI = (List<Paquete>) respuesta.get("paquetes");
            paquetes = FXCollections.observableArrayList();
            paquetes.addAll(paquetesAPI);
            tvPaquetes.setItems(paquetes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    private void irFormulario(Paquete paquete) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioPaquetes.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioPaquetesController controlador = cargador.getController();
            controlador.inicializarDatos(paquete, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Paquete");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void cargarBusqueda(String busqueda){
        HashMap<String, Object> respuesta = PaqueteImp.busqueda(busqueda);
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Paquete> paquetesAPI = (List<Paquete>) respuesta.get("paquetes");
            paquetes = FXCollections.observableArrayList();
            paquetes.addAll(paquetesAPI);
            tvPaquetes.setItems(paquetes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void quitarPaquete(Paquete paquete) throws UnsupportedEncodingException{
        Respuesta respuesta = PaqueteImp.darBaja(paquete);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Paquete desvinvulado", 
                    "El paquete ya no pertenece a un envio", 
                    Alert.AlertType.INFORMATION);
            cargarInformacionPaquetes();
        }else{
            Utilidades.mostrarAlertaSimple("Error al dar de baja.", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionPaquetes();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
}
