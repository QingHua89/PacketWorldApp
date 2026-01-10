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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import packetworldapp.dominio.SucursalImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminSucursalController implements Initializable, INotificador {

    @FXML
    private TableView<Sucursal> tvSucursales;
    @FXML
    private TableColumn tcCodigoSucursal;
    @FXML
    private TableColumn tcSucursalNombre;
    @FXML
    private TableColumn tcEstatus;
    @FXML
    private TableColumn tcCalle;
    @FXML
    private TableColumn tcNumero;
    @FXML
    private TableColumn tcColonia;
    @FXML
    private TableColumn tcCodigoPostal;
    @FXML
    private TableColumn tcCiudad;
    @FXML
    private TableColumn tcEstado;
    @FXML
    private ImageView ivAtras;
    private Colaborador colaboradorSesion;
    private ObservableList<Sucursal> sucursales;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionSucursales();
    }
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }  

    @FXML
    private void ClicRegistrar(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void ClicModificar(ActionEvent event) {
        Sucursal sucursal = tvSucursales.getSelectionModel().getSelectedItem();
        if ("Inactiva".equalsIgnoreCase(sucursal.getEstatus())) {
            Utilidades.mostrarAlertaSimple("Sucursal inactiva","Esta sucursal esta inactiva, no se puede modificar.",
                    Alert.AlertType.INFORMATION);
            return;
        }
        if (sucursal !=null){
            irFormulario(sucursal);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona una sucursal", "Para editar una sucursal, seleccionala de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicBaja(ActionEvent event) throws UnsupportedEncodingException {
        Sucursal sucursal = tvSucursales.getSelectionModel().getSelectedItem();
        if (sucursal !=null){
            boolean confirmacion = Utilidades.mostrarAlertaConformacion("Baja",
                    "¿Desea dar de baja la sucursal "+sucursal.getSucursalNombre()+"?"
                    +"\n Esta sucursal no podra usarse en el futuro.");
            if(confirmacion){
                bajaSucursal(sucursal);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona una sucursal",
                    "Para dar de baja uan sucursal, seleccionala de la tabla",
                    Alert.AlertType.WARNING);
        }
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
    
    private void configurarTabla (){
        tcCodigoSucursal.setCellValueFactory(new PropertyValueFactory("codigoSucursal"));
        tcSucursalNombre.setCellValueFactory(new PropertyValueFactory("sucursalNombre"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory("estatus"));
        tcCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        tcNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        tcColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        tcCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        tcCiudad.setCellValueFactory(new PropertyValueFactory("ciudad"));
        tcEstado.setCellValueFactory(new PropertyValueFactory("estado"));
    }
    
    private void cargarInformacionSucursales(){
        HashMap<String, Object> respuesta = SucursalImp.obtenerTodas();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Sucursal> sucursalesAPI = (List<Sucursal>) respuesta.get("sucursales");
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(sucursalesAPI);
            tvSucursales.setItems(sucursales);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }
    
    private void irFormulario(Sucursal sucursal){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioSucursales.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioSucursalesController controlador = cargador.getController();
            controlador.inicializarDatos(sucursal, this);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Sucursales");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void bajaSucursal(Sucursal sucursal) throws UnsupportedEncodingException{
        Respuesta respuesta = SucursalImp.darBaja(sucursal);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Informacion dada de Baja", 
                    "La información de la sucursal fue dada de baja de manera exitosa.", 
                    Alert.AlertType.INFORMATION);
            cargarInformacionSucursales();
        }else{
            Utilidades.mostrarAlertaSimple("Error al dar de baja.", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionSucursales();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }
    
}
