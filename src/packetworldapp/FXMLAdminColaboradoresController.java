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
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminColaboradoresController implements Initializable, INotificador {

    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btBuscar;
    @FXML
    private Button btNuevo;
    @FXML
    private Button btEditar;
    @FXML
    private Button btEliminar;
    @FXML
    private TableView<Colaborador> tvColaboradores;
    @FXML
    private TableColumn tcNumPersonal;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcCurp;
    @FXML
    private TableColumn tcCorreo;
    @FXML
    private TableColumn tcRol;
    @FXML
    private TableColumn tcSucursal;
    @FXML
    private TableColumn tcLicencia;
    private ObservableList<Colaborador> colaboradores;
    @FXML
    private ImageView ivAtras;
    private Colaborador colaboradorSesion;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionColaboradores();
    }    

    @FXML
    private void ClicBuscarColaborador(ActionEvent event) {
        cargarBusqueda(tfBuscar.getText());
    }

    @FXML
    private void ClicAgregarColab(ActionEvent event) {
        irFormulario(null);
    }
    
    @FXML
    private void ClicEditarColab(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador !=null){
            irFormulario(colaborador);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un colaborador", "Para editar un colaborador, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicEliminarColab(ActionEvent event) {
        Colaborador colaborador = tvColaboradores.getSelectionModel().getSelectedItem();
        if (colaborador !=null){
            boolean confirmacion = Utilidades.mostrarAlertaConformacion("Eliminar",
                    "¿Desea eliminar de forma permanente al colaborador/a "+colaborador.getNombre()+"?"
                    +"\n Esta información no se podra recuperar despues.");
            if(confirmacion){
                eliminarColaborador(colaborador);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un colaborador",
                    "Para eliminar un colaborador, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }
    
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }
    
    private void configurarTabla (){
        tcNumPersonal.setCellValueFactory(new PropertyValueFactory("numeroPersonal"));
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcCurp.setCellValueFactory(new PropertyValueFactory("curp"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
        tcRol.setCellValueFactory(new PropertyValueFactory("rol"));
        tcSucursal.setCellValueFactory(new PropertyValueFactory("sucursalNombre"));
        tcLicencia.setCellValueFactory(new PropertyValueFactory("numeroLicencia"));
    }
    
    private void cargarInformacionColaboradores(){
        HashMap<String, Object> respuesta = ColaboradorImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Colaborador> colaboradoresAPI = (List<Colaborador>) respuesta.get("colaboradores");
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);
            tvColaboradores.setItems(colaboradores);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
        
    }
    
    private void irFormulario(Colaborador colaborador) {
        try {
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioColaboradores.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioColaboradoresController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador, this);

            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Colaborador");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    private void eliminarColaborador(Colaborador colaborador){
        Respuesta respuesta = ColaboradorImp.eliminar(colaborador.getIdColaborador());
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Informacion Eliminada", 
                    "La información del profesor/a "+colaborador.getNombre()+" fue eliminada de manera exitosa.", 
                    Alert.AlertType.INFORMATION);
            cargarInformacionColaboradores();
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void ClicIrAtras(MouseEvent event) {
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
    private void cargarBusqueda(String busqueda){
        HashMap<String, Object> respuesta = ColaboradorImp.busqueda(busqueda);
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Colaborador> colaboradoresAPI = (List<Colaborador>) respuesta.get("colaboradores");
            colaboradores = FXCollections.observableArrayList();
            colaboradores.addAll(colaboradoresAPI);
            tvColaboradores.setItems(colaboradores);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionColaboradores();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
    
    
}
