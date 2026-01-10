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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import packetworldapp.dominio.AsignarImp;
import packetworldapp.dominio.CatalogoImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.AsignarUnidad;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Unidad;
import packetworldapp.utilidades.Constantes;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminAsignacionController implements Initializable, INotificador {

    @FXML
    private ImageView ivAtras;
    @FXML
    private TableView<AsignarUnidad> tvAsignaciones;
    @FXML
    private TableColumn tcColaborador;
    @FXML
    private TableColumn tcUnidad;
    @FXML
    private TableColumn tcAsignacion;
    @FXML
    private TableColumn tcRetiro;
    @FXML
    private ComboBox<Colaborador> cbConductor;
    @FXML
    private ComboBox<Unidad> cbUnidad;
    @FXML
    private DatePicker dpFechaAsignacion;
    @FXML
    private DatePicker dpFechaRetiro;
    private ObservableList<AsignarUnidad> asignaciones;
    private ObservableList<Colaborador> chofer;
    private ObservableList<Unidad> unidades;
    private Colaborador colaboradorSesion;
    private INotificador observador;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAsignaciones();
        cargarColaboradoresValidos();
        configurarTabla();
        cargarUnidades();
    }
    
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }

    @FXML
    private void clicAtras(MouseEvent event) {
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
    private void clicAsignar(ActionEvent event) {
        AsignarUnidad asignacion = new AsignarUnidad();
        asignacion.setFechaAsignacion(dpFechaAsignacion.getValue().toString());
        Colaborador colabseleccion = cbConductor.getSelectionModel().getSelectedItem();
        asignacion.setIdColaborador(colabseleccion.getIdColaborador());
        Unidad unidadSeleccion = cbUnidad.getSelectionModel().getSelectedItem();
        asignacion.setIdUnidad(unidadSeleccion.getIdUnidad());
        boolean asignacionActiva = 
                asignaciones.stream().anyMatch(a -> 
                        (a.getIdColaborador() == asignacion.getIdColaborador() || 
                        a.getIdUnidad() == asignacion.getIdUnidad()) && a.getFechaDesasignacion() == null);
        if (!asignacionActiva) {
            asignarUnidad(asignacion);
        } else {
            Utilidades.mostrarAlertaSimple("Error al asignar unidad",
                    "Unidad o Colaborador ya vinculados actualmente", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void clicRetirar(ActionEvent event) {
        AsignarUnidad seleccion = tvAsignaciones.getSelectionModel().getSelectedItem();
        if (seleccion != null && seleccion.getFechaDesasignacion() == null) {
            seleccion.setFechaDesasignacion(dpFechaRetiro.getValue().toString());
            Respuesta respuesta = AsignarImp.retirarUnidad(seleccion);
            if (!respuesta.isError()) {
                Utilidades.mostrarAlertaSimple("Retiro Completo",
                        "El colaborador ha sido desasignado correctamente",
                        Alert.AlertType.INFORMATION);
                cargarAsignaciones();
            } else {
                Utilidades.mostrarAlertaSimple("Error al retirar", respuesta.getMensaje(), Alert.AlertType.ERROR);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección inválida",
                    "Debe seleccionar una asignación activa para retirar",
                    Alert.AlertType.WARNING);
        }
    }

    
    private void configurarTabla(){
        tcColaborador.setCellValueFactory(new PropertyValueFactory("colaboradorNombre"));
        tcUnidad.setCellValueFactory(new PropertyValueFactory("unidadNII"));
        tcAsignacion.setCellValueFactory(new PropertyValueFactory("fechaAsignacion"));
        tcRetiro.setCellValueFactory(new PropertyValueFactory("fechaDesasignacion"));
    }
    private void cargarAsignaciones(){
        HashMap<String, Object> respuesta = AsignarImp.obtenerTodas();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<AsignarUnidad> asignacionesAPI = (List<AsignarUnidad>) respuesta.get("asignacion");
            asignaciones = FXCollections.observableArrayList();
            asignaciones.addAll(asignacionesAPI);
            tvAsignaciones.setItems(asignaciones);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    private void cargarColaboradoresValidos(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerConductoresSistema();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Colaborador> listaColaboradores =(List<Colaborador>)respuesta.get(Constantes.KEY_LISTA);
            chofer = FXCollections.observableArrayList();
            chofer.addAll(listaColaboradores);
            cbConductor.setItems(chofer);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    private void cerrarVentana(){
        ((Stage)ivAtras.getScene().getWindow()).close();
    }
    private void cargarUnidades(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerUnidadesSistema();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Unidad> listaUnidades =(List<Unidad>)respuesta.get(Constantes.KEY_LISTA);
            unidades = FXCollections.observableArrayList();
            unidades.addAll(listaUnidades);
            cbUnidad.setItems(unidades);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    
    private void asignarUnidad(AsignarUnidad asignacion){
        Respuesta respuesta = AsignarImp.asignarUnidad(asignacion);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Asignación Completa",
                    "El colaborador y la unidad han sido asignados correctamente",
                    Alert.AlertType.INFORMATION);
            cargarAsignaciones();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarAsignaciones();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
    
}