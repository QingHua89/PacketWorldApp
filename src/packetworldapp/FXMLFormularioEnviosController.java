package packetworldapp;

import java.io.UnsupportedEncodingException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.CatalogoImp;
import packetworldapp.utilidades.GeneradorGuia;
import packetworldapp.dominio.EnvioImp;
import packetworldapp.dominio.PaqueteImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Cliente;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Envio;
import packetworldapp.pojo.EstadoEnvio;
import packetworldapp.pojo.Paquete;
import packetworldapp.pojo.SucursalNombres;
import packetworldapp.utilidades.Constantes;
import packetworldapp.utilidades.Utilidades;

public class FXMLFormularioEnviosController implements Initializable {

    @FXML
    private Label lbCanPaquetes;
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
    private ObservableList<Paquete> paquetes;
    
    private ObservableList<Paquete> contarPaquetes = FXCollections.observableArrayList();
    
    @FXML
    private ComboBox<Paquete> cbPaquetes;
    @FXML
    private Button btAgregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarSucursales();
        cargarEstadoEnvio();
        cargarClientes();
        cargarPaquetes();
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
            cargarPaquetesPorGuia(envioEdicion.getGuia());
        }else {
            tfGuia.setText(GeneradorGuia.generarGuiaEnvio());
        }
    }
        
    @FXML
    private void ClicCancelar(ActionEvent event) {
        cerrarVentana();
    }

    @FXML
    private void ClicAceptar(ActionEvent event) throws UnsupportedEncodingException {
        if (sonCamposValidos()) {
            Envio envio = new Envio();
            envio.setGuia(tfGuia.getText());

            Cliente clienteSeleccion = cbCliente.getSelectionModel().getSelectedItem();
            envio.setIdCliente(clienteSeleccion.getIdCliente());

            SucursalNombres sucursalSeleccion = cbSucursal.getSelectionModel().getSelectedItem();
            envio.setIdSucursal(sucursalSeleccion.getIdSucursal());

            EstadoEnvio estadoSeleccion = cbEstado.getSelectionModel().getSelectedItem();
            envio.setIdEstadoEnvio(estadoSeleccion.getIdEstadoenvio());

            envio.setCosto(Double.parseDouble(tfCostoTotal.getText()));

            if (envioEdicion == null) {
                registrarEnvio(envio);
            } else {
                envio.setIdEnvio(envioEdicion.getIdEnvio());
                editarEnvio(envio);
            }
        }
    }
    
    @FXML
    private void ClicAgregar(ActionEvent event) {
        Paquete paqueteSeleccion = cbPaquetes.getSelectionModel().getSelectedItem();

        if (paqueteSeleccion != null) {
            if (!contarPaquetes.contains(paqueteSeleccion)) {
                paqueteSeleccion.setGuia(tfGuia.getText());

                contarPaquetes.add(paqueteSeleccion);
                lbCanPaquetes.setText("X "+String.valueOf(contarPaquetes.size())+" paquetes");
                cbPaquetes.getSelectionModel().clearSelection();
            } else {
                Utilidades.mostrarAlertaSimple("Paquete duplicado",
                        "Este paquete ya ha sido agregado a la lista.", Alert.AlertType.WARNING);
            }
        } else {
            Utilidades.mostrarAlertaSimple("Selección requerida",
                    "Seleccione un paquete antes de agregar.", Alert.AlertType.WARNING);
        }
    }
    
    private void cerrarVentana(){
        ((Stage)tfGuia.getScene().getWindow()).close();
    }
    
    private boolean sonCamposValidos() {
        if (contarPaquetes.isEmpty()) {
            Utilidades.mostrarAlertaSimple("Sin paquetes",
                    "Debes agregar al menos un paquete al envío.", Alert.AlertType.WARNING);
            return false;
        }
        if (cbCliente.getSelectionModel().getSelectedItem() == null || 
                cbSucursal.getSelectionModel().getSelectedItem() == null) {
            return false;
        }
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
    
    private void cargarPaquetes(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerPaquete();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Paquete> listaPaquetes =(List<Paquete>)respuesta.get(Constantes.KEY_LISTA);
            paquetes = FXCollections.observableArrayList();
            paquetes.addAll(listaPaquetes);
            cbPaquetes.setItems(paquetes);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
    }
    
    private void registrarEnvio(Envio envio) throws UnsupportedEncodingException {
        Respuesta respuesta = EnvioImp.registrar(envio);
        if (!respuesta.isError()) {
            for (int i = 0; i < contarPaquetes.size(); i++) {
                actualizarGuiaPaquete(contarPaquetes.get(i));
            }
            Utilidades.mostrarAlertaSimple("Envío registrado",
                    "La información del envío " + envio.getGuia() + " ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", envio.getGuia());
            cerrarVentana();
        } else {
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }

    private void editarEnvio(Envio envio) throws UnsupportedEncodingException {
        Respuesta respuesta = EnvioImp.editar(envio);
        if (!respuesta.isError()) {
            for (int i = 0; i < contarPaquetes.size(); i++) {
                actualizarGuiaPaquete(contarPaquetes.get(i));
            }
            Utilidades.mostrarAlertaSimple("Envío modificado",
                    "La información del envío " + envio.getGuia() + " ha sido modificada correctamente.",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", envio.getGuia());
            cerrarVentana();
        } else {
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
    
    private int obtenerPosicionPaquete(int idPaquete){
        for (int i=0; i<paquetes.size(); i++){
            if (paquetes.get(i).getIdPaquete()==idPaquete)
                return i;
        }
        return -1;
    }
    
    private void cargarPaquetesPorGuia(String guia) {
        HashMap<String, Object> respuesta = PaqueteImp.busqueda(guia);
        boolean esError = (boolean) respuesta.get("error");
        if (!esError) {
            List<Paquete> listaRecibida = (List<Paquete>) respuesta.get("paquetes");
            if (listaRecibida != null) {
                contarPaquetes.clear();
                contarPaquetes.addAll(listaRecibida);
                lbCanPaquetes.setText(String.valueOf(contarPaquetes.size()));
            }
        } else {
            String mensajeError = (String) respuesta.get("mensaje");
            Utilidades.mostrarAlertaSimple("Error de carga", mensajeError, Alert.AlertType.ERROR);
        }
    }
    
    private void actualizarGuiaPaquete(Paquete paquete) throws UnsupportedEncodingException {
        Respuesta respuesta = PaqueteImp.agregarGuia(paquete);
        if (respuesta.isError()) {
            Utilidades.mostrarAlertaSimple("Error al vincular paquete",
                    "No se pudo asignar la guía al paquete",Alert.AlertType.ERROR);
        }
    }
    
    private void calcularCostoTotal() {
        double costoBase = 0.0;
        try {
            if (!tfCostoEnvio.getText().isEmpty()) {
                costoBase = Double.parseDouble(tfCostoEnvio.getText());
            }
        } catch (NumberFormatException e) {
            costoBase = 0.0;
        }

        int numPaquetes = contarPaquetes.size();
        double costoAdicional = 0.0;

        switch (numPaquetes) {
            case 0:
            case 1:
                costoAdicional = 0.0;
                break;
            case 2:
                costoAdicional = 50.0;
                break;
            case 3:
                costoAdicional = 80.0;
                break;
            case 4:
                costoAdicional = 110.0;
                break;
            default:
                costoAdicional = 150.0;
                break;
        }

        tfPaquetes.setText(String.format("%.2f", costoAdicional));
        double total = costoBase + costoAdicional;
        tfCostoTotal.setText(String.format("%.2f", total));
    }
}
