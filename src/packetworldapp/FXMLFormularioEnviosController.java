package packetworldapp;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.CatalogoImp;
import packetworldapp.utilidades.GeneradorGuia;
import packetworldapp.dominio.EnvioImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Cliente;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Envio;
import packetworldapp.pojo.EstadoEnvio;
import packetworldapp.pojo.SucursalNombres;
import packetworldapp.utilidades.Constantes;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioEnviosController implements Initializable {

    @FXML
    private Label lbInfoCliente;
    @FXML
    private Label lbInfoSucursal;
    @FXML
    private TextField tfCostoTotal;
    @FXML
    private TextField tfCostoEnvio;
    @FXML
    private TextField tfPaquetes;
    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ChoiceBox<SucursalNombres> cbSucursal;
    @FXML
    private ComboBox<EstadoEnvio> cbEstado;
    @FXML
    private TextField tfGuia;
    
    private INotificador observador;
    private Colaborador colaborador;
    private Envio envioEdicion;
    private ObservableList<SucursalNombres> sucursales;
    private ObservableList<EstadoEnvio> esEnvio;
    private ObservableList<Cliente> clientes;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSucursales();
        cargarEstadoEnvio();
        cargarClientes();
    }    
    
    public void inicializarDatos(Envio envioEdicion, INotificador observador){
        this.envioEdicion = envioEdicion;
        this.observador = observador;
        if (envioEdicion != null){
            tfGuia.setText(envioEdicion.getGuia());
            tfGuia.setDisable(true);
            //tfPaquetes.setText(envioEdicion);
            //tfCostoEnvio.setText(envioEdicion);
            tfCostoTotal.setText(String.valueOf(envioEdicion.getCosto()));
            int pos = obtenerPosicionCliente(envioEdicion.getIdCliente());
            cbCliente.getSelectionModel().select(pos);
            int posSC = obtenerPosicionSucursal(envioEdicion.getIdSucursal());
            cbSucursal.getSelectionModel().select(posSC);
            int posEE = obtenerPosicionEstadoEnvio(envioEdicion.getIdEstadoEnvio());
            cbEstado.getSelectionModel().select(posEE); 
        }else {
            tfGuia.setText(GeneradorGuia.generarGuiaEnvio());
            tfGuia.setDisable(true);
        }
    }
        
    @FXML
    private void ClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void ClicAceptar(ActionEvent event) {
        if(sonCamposValidos()){
            Envio envio = new Envio();
            envio.setGuia(tfGuia.getText());
            
            Cliente clienteSeleccion = cbCliente.getSelectionModel().getSelectedItem();
            envio.setIdCliente(clienteSeleccion.getIdCliente());
            
            SucursalNombres sucursalSeleccion = cbSucursal.getSelectionModel().getSelectedItem();
            envio.setIdSucursal(sucursalSeleccion.getIdSucursal());
            
            EstadoEnvio estadoSeleccion = cbEstado.getSelectionModel().getSelectedItem();
            envio.setIdEstadoEnvio(estadoSeleccion.getIdEstadoenvio());
            
            envio.setCosto(Double.parseDouble(tfCostoTotal.getText()));
            
            if (envioEdicion == null){
                registrarEnvio(envio);
            }else{
                envio.setIdEnvio(envioEdicion.getIdEnvio());
                editarEnvio(envio);
            }
            cerrarVentana();
        }
    }
    
    private void cerrarVentana(){
        ((Stage)lbInfoCliente.getScene().getWindow()).close();
    }
    
    private boolean sonCamposValidos(){
        return true;
    }
    private void cargarSucursales(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerSucursalesSistema();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<SucursalNombres> listaSucursales =(List<SucursalNombres>)respuesta.get(Constantes.KEY_LISTA);
            sucursales = FXCollections.observableArrayList();
            sucursales.addAll(listaSucursales);
            cbSucursal.setItems(sucursales);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    
    private void cargarEstadoEnvio(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerEstadoEnvio();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<EstadoEnvio> listaEstado =(List<EstadoEnvio>)respuesta.get(Constantes.KEY_LISTA);
            esEnvio = FXCollections.observableArrayList();
            esEnvio.addAll(listaEstado);
            cbEstado.setItems(esEnvio);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    
    private void cargarClientes(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerClienteSistema();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Cliente> listaClientes =(List<Cliente>)respuesta.get(Constantes.KEY_LISTA);
            clientes = FXCollections.observableArrayList();
            clientes.addAll(listaClientes);
            cbCliente.setItems(clientes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    private void registrarEnvio(Envio envio){
        Respuesta respuesta = EnvioImp.registrar(envio);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Envio registrado",
                    "La información del colaborador "+envio.getGuia()+" ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", envio.getGuia());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarEnvio(Envio envio){
        Respuesta respuesta = EnvioImp.editar(envio);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Colaborador(a) modificado correctamente", 
                    "La información del envio "+envio.getGuia()+" ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", envio.getGuia());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private int obtenerPosicionCliente(int idCliente){
        for (int i=0; i<clientes.size(); i++){
            if (clientes.get(i).getIdCliente()==idCliente)
                return i;
        }
        return -1;
    }
    
    private int obtenerPosicionSucursal(int idSucursal){
        for (int i=0; i<sucursales.size(); i++){
            if (sucursales.get(i).getIdSucursal()==idSucursal)
                return i;
        }
        return -1;
    }
    
    private int obtenerPosicionEstadoEnvio(int idEstadoEnvio){
        for (int i=0; i<esEnvio.size(); i++){
            if (esEnvio.get(i).getIdEstadoenvio()==idEstadoEnvio)
                return i;
        }
        return -1;
    }
}
