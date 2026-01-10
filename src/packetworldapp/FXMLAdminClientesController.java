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
import packetworldapp.dominio.ClienteImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.pojo.Cliente;
import packetworldapp.pojo.Colaborador;
import packetworldapp.interfaz.INotificador;
import packetworldapp.utilidades.Utilidades;

public class FXMLAdminClientesController implements Initializable, INotificador {

    @FXML
    private ImageView ivAtras;
    @FXML
    private TextField tfBuscar;
    @FXML
    private Button btBuscar;
    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcCalle;
    @FXML
    private TableColumn tcNumero;
    @FXML
    private TableColumn tcColonia;
    @FXML
    private TableColumn tcCodigoPostal;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcCorreo;
    private ObservableList<Cliente> clientes;
    private Colaborador colaboradorSesion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacionClientes();
    }
    
    public void inicializarDatos(Colaborador colaborador) {
        this.colaboradorSesion = colaborador;
    }  

    @FXML
    private void ClicBuscarCliente(ActionEvent event) {
        cargarBusqueda(tfBuscar.getText());
    }

    @FXML
    private void ClicAgregarCliente(ActionEvent event) {
        irFormulario(null);
    }

    @FXML
    private void ClicEditarCliente(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if (cliente !=null){
            irFormulario(cliente);
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un cliente", "Para editar un cliente, seleccionalo de la tabla",
                    Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void ClicEliminarCliente(ActionEvent event) {
        Cliente cliente = tvClientes.getSelectionModel().getSelectedItem();
        if (cliente !=null){
            boolean confirmacion = Utilidades.mostrarAlertaConformacion("Eliminar",
                    "¿Desea eliminar de forma permanente al cliente "+cliente.getNombre()+"?"
                    +"\n Esta información no se podra recuperar despues.");
            if(confirmacion){
                eliminarCliente(cliente);
            }
        }else{
            Utilidades.mostrarAlertaSimple("Selecciona un cliente",
                    "Para eliminar un cliente, seleccionalo de la tabla",
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
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcCalle.setCellValueFactory(new PropertyValueFactory("calle"));
        tcNumero.setCellValueFactory(new PropertyValueFactory("numero"));
        tcColonia.setCellValueFactory(new PropertyValueFactory("colonia"));
        tcCodigoPostal.setCellValueFactory(new PropertyValueFactory("codigoPostal"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        tcCorreo.setCellValueFactory(new PropertyValueFactory("correo"));
    }
    
    private void cargarInformacionClientes(){
        HashMap<String, Object> respuesta = ClienteImp.obtenerTodos();
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Cliente> clientesAPI = (List<Cliente>) respuesta.get("clientes");
            clientes = FXCollections.observableArrayList();
            clientes.addAll(clientesAPI);
            tvClientes.setItems(clientes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
        
    }
    
    private void irFormulario(Cliente cliente){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLFormularioClientes.fxml"));
            Parent vista = cargador.load();
            FXMLFormularioClientesController controlador = cargador.getController();
            controlador.inicializarDatos(cliente, this);
            
            Scene escena = new Scene(vista);
            Stage escenario = new Stage();
            escenario.setScene(escena);
            escenario.setTitle("Formulario Cliente");
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.showAndWait();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private void eliminarCliente(Cliente cliente){
        Respuesta respuesta = ClienteImp.eliminar(cliente.getIdCliente());
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Informacion Eliminada", 
                    "La información del cliente "+cliente.getNombre()+" fue eliminada de manera exitosa.", 
                    Alert.AlertType.INFORMATION);
            cargarInformacionClientes();
        }else{
            Utilidades.mostrarAlertaSimple("Error al eliminar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void cargarBusqueda(String busqueda){
        HashMap<String, Object> respuesta = ClienteImp.busqueda(busqueda);
        boolean esError = (boolean) respuesta.get("error");
            if(!esError){
            List<Cliente> clientesAPI = (List<Cliente>) respuesta.get("clientes");
            clientes = FXCollections.observableArrayList();
            clientes.addAll(clientesAPI);
            tvClientes.setItems(clientes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar",
                ""+respuesta.get("mensaje"), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void notificarOperacionExitosa(String operacion, String nombre) {
        cargarInformacionClientes();
        System.err.println("Operacion: "+operacion+" , nombre: "+nombre);
    }

}
