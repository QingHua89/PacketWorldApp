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
import packetworldapp.dominio.EnvioImp;
import packetworldapp.dominio.SucursalImp;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Envio;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminEnviosController implements Initializable, INotificador {

    @FXML
    private TableView<Envio> tvEnvios;
    @FXML
    private ImageView ivAtras;
    @FXML
    private TableColumn tcGuia;
    @FXML
    private TableColumn tcNombreCli;
    @FXML
    private TableColumn tcPaterno;
    @FXML
    private TableColumn tcMaterno;
    @FXML
    private TableColumn tcCalleCliente;
    @FXML
    private TableColumn tcCPDestino;
    @FXML
    private TableColumn tcSucursal;
    @FXML
    private TableColumn tcCalleSuc;
    @FXML
    private TableColumn tcCPSuc;
    @FXML
    private TableColumn tcCosto;
    @FXML
    private TableColumn tcEstado;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btBuscar;
    private Colaborador colaboradorSesion;
    private ObservableList<Envio> envios;   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionEnvios();
    }
    
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }

    @FXML
    private void ClicBuscar(ActionEvent event) {
    }
    
    @FXML
    private void ClicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void ClicEditar(ActionEvent event) {
        Envio envio = tvEnvios.getSelectionModel().getSelectedItem();
        if (envio !=null){
            irFormulario(envio);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un envio", "Para editar un envio, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicActuEstatus(ActionEvent event) {
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
    
    private void configurarTabla(){
        tcGuia.setCellValueFactory(new PropertyValueFactory("guia"));
        tcNombreCli.setCellValueFactory(new PropertyValueFactory("nombreCliente"));
        tcPaterno.setCellValueFactory(new PropertyValueFactory("apPaternoCliente"));
        tcMaterno.setCellValueFactory(new PropertyValueFactory("apMaternoCliente"));
        tcCalleCliente.setCellValueFactory(new PropertyValueFactory("calleCliente"));
        tcCPDestino.setCellValueFactory(new PropertyValueFactory("cpCliente"));
        tcSucursal.setCellValueFactory(new PropertyValueFactory("sucursal"));
        tcCalleSuc.setCellValueFactory(new PropertyValueFactory("sucursalCalle"));
        tcCPSuc.setCellValueFactory(new PropertyValueFactory("sucursalCP"));
        tcCosto.setCellValueFactory(new PropertyValueFactory("costo"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    private void irFormulario(Envio envio){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioEnvios.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioEnviosController controlador = cargador.getController();
            controlador.inicializarDatos(envio, this);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario de Envio");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void cargarInformacionEnvios(){
        HashMap<String, Object> respuesta = EnvioImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Envio> enviosAPI = (List<Envio>) respuesta.get("envios");
            envios = FXCollections.observableArrayList();
            envios.addAll(enviosAPI);
            tvEnvios.setItems(envios);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    private void calcularCosto(){
    }
    
    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionEnvios();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
}
