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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import packetworldapp.dominio.AsociacionImp;
import packetworldapp.dominio.CatalogoImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.AsociarEnvio;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Envio;
import packetworldapp.utilidades.Constantes;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminAsociacionEnviosController implements Initializable, INotificador {

    @FXML
    private ImageView ivAtras;
    @FXML
    private ComboBox<Envio> cbEnvio;
    @FXML
    private ComboBox<Colaborador> cbConductor;
    @FXML
    private TableView<AsociarEnvio> tvAsociaciones;
    @FXML
    private TableColumn tcGuia;
    @FXML
    private TableColumn tcConductor;
    
    private ObservableList<AsociarEnvio> asignaciones;
    private ObservableList<AsociarEnvio> noAptos;
    private ObservableList<Colaborador> chofer;
    private ObservableList<Envio> envios;
    private Colaborador colaboradorSesion;
    private INotificador observador;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarEnviosValidos();
        cargarColaboradoresValidos();
        cargarAsignaciones();
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
    private void clicAsociar(ActionEvent event) {
        Colaborador colabSeleccion = cbConductor.getSelectionModel().getSelectedItem();
        Envio envioSeleccion = cbEnvio.getSelectionModel().getSelectedItem();

        AsociarEnvio asociacion = new AsociarEnvio();
        asociacion.setIdColaborador(colabSeleccion.getIdColaborador());
        asociacion.setIdEnvio(envioSeleccion.getIdEnvio());
        boolean asignacionActiva
                = asignaciones.stream().anyMatch(a
                        -> (a.getIdColaborador() == asociacion.getIdColaborador()
                || a.getIdEnvio() == asociacion.getIdEnvio()));
        if (!asignacionActiva) {
            asignarEnvio(asociacion);
        } else {
            Utilidades.mostrarAlertaSimple("Error al asignar Envio",
                    "Colaborador vinculados actualmente", Alert.AlertType.ERROR);
        }
    }
    
    private void configurarTabla(){
        tcGuia.setCellValueFactory(new PropertyValueFactory("guiaEnvio"));
        tcConductor.setCellValueFactory(new PropertyValueFactory("colaboradorNumero"));
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
        }
    }
    
    private void cargarAsignaciones(){
        HashMap<String, Object> respuesta = AsociacionImp.obtenerTodas();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<AsociarEnvio> asignacionesAPI = (List<AsociarEnvio>) respuesta.get("asociacion-envios");
            asignaciones = FXCollections.observableArrayList();
            asignaciones.addAll(asignacionesAPI);
            tvAsociaciones.setItems(asignaciones);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    
    private void cargarEnviosValidos(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerEnviosValidos();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Envio> listaEnvios =(List<Envio>)respuesta.get(Constantes.KEY_LISTA);
            envios = FXCollections.observableArrayList();
            envios.addAll(listaEnvios);
            cbEnvio.setItems(envios);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
        }
    }
    
    private void asignarEnvio(AsociarEnvio asociacion){
        Respuesta respuesta = AsociacionImp.asignarEnvio(asociacion);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Asignación Completa",
                    "El colaborador y el envio han sido asignados correctamente",
                    Alert.AlertType.INFORMATION);
            cargarAsignaciones();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    
    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarEnviosValidos();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
}
