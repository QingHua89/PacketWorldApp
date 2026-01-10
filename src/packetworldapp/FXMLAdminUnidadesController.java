package packetworldapp;

import java.io.IOException;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldapp.dominio.UnidadImp;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Unidad;
import packetworldapp.utilidades.Utilidades;

/**
 * FXML Controller class
 *
 * @author Windows
 */
public class FXMLAdminUnidadesController implements Initializable, INotificador {

    @FXML
    private TextField tfBuscar;
    @FXML
    private TableView<Unidad> tvUnidades;
    @FXML
    private TableColumn tcMarca;
    @FXML
    private TableColumn tcModelo;
    @FXML
    private TableColumn tcAnio;
    @FXML
    private TableColumn tcVin;
    @FXML
    private TableColumn tcTipo;
    @FXML
    private TableColumn tcNII;
    @FXML
    private TableColumn tcEstatus;
    @FXML
    private TableColumn tcBaja;
    @FXML
    private ImageView ivAtras;
    private Colaborador colaboradorSesion;
    private ObservableList<Unidad> unidades;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionUnidades();
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
        realizarBusqueda(tfBuscar.getText());
    }

    @FXML
    private void ClicNuevo(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void ClicModificar(ActionEvent event) {
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();
        if ("Inactiva".equalsIgnoreCase(unidad.getEstatus())) {
            Utilidades.mostrarAlertaSimple("Unidad inactiva","Esta unidad esta inactiva, no se puede modificar.",
                    Alert.AlertType.INFORMATION);
            return;
        }
        if (unidad !=null){
            irFormulario(unidad);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona una unidad", "Para editar una unidad, seleccionala de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicDarBaja(ActionEvent event) {
        Unidad unidad = tvUnidades.getSelectionModel().getSelectedItem();

        if (unidad == null) {
            Utilidades.mostrarAlertaSimple("Selecciona una unidad","Para dar de baja una unidad, seleccionala de la tabla",
                    Alert.AlertType.WARNING);
            return;
        }
        if ("Inactiva".equalsIgnoreCase(unidad.getEstatus())) {
            Utilidades.mostrarAlertaSimple("Unidad inactiva","Esta unidad ya fue dada de baja",
                    Alert.AlertType.INFORMATION);
            return;
        }
        irBaja(unidad);
    }
    
    private void configurarTabla (){
        tcMarca.setCellValueFactory(new PropertyValueFactory("marca"));
        tcModelo.setCellValueFactory(new PropertyValueFactory("modelo"));
        tcAnio.setCellValueFactory(new PropertyValueFactory("anio"));
        tcVin.setCellValueFactory(new PropertyValueFactory("vin"));
        tcTipo.setCellValueFactory(new PropertyValueFactory("tipo"));
        tcNII.setCellValueFactory(new PropertyValueFactory("numeroInterno"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        tcBaja.setCellValueFactory(new PropertyValueFactory("motivoBaja"));
    }
    
    private void cargarInformacionUnidades(){
        HashMap<String, Object> respuesta = UnidadImp.obtenerTodas();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Unidad> unidadesAPI = (List<Unidad>) respuesta.get("unidades");
            unidades = FXCollections.observableArrayList();
            unidades.addAll(unidadesAPI);
            tvUnidades.setItems(unidades);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(Unidad unidad){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioUnidades.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioUnidadesController controlador = cargador.getController();
            controlador.inicializarDatos(unidad, this);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Unidades");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void irBaja(Unidad unidad){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLMotivoBajaUnidad.fxml"));
            Parent vista = cargador.load();
            FXMLMotivoBajaUnidadController controlador = cargador.getController();
            controlador.inicializarDatos(unidad, this);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Motivo de Baja");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void realizarBusqueda(String busqueda){
        HashMap<String, Object> respuesta = UnidadImp.busqueda(busqueda);
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Unidad> unidadesAPI = (List<Unidad>) respuesta.get("unidades");
            unidades = FXCollections.observableArrayList();
            unidades.addAll(unidadesAPI);
            tvUnidades.setItems(unidades);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionUnidades();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
}
