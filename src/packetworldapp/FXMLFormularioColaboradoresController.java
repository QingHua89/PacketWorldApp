package packetworldapp;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import packetworldapp.dominio.CatalogoImp;
import packetworldapp.dominio.ColaboradorImp;
import packetworldapp.dto.Respuesta;
import packetworldapp.interfaz.INotificador;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Rol;
import packetworldapp.pojo.SucursalNombres;
import packetworldapp.utilidades.Constantes;
import packetworldapp.utilidades.Utilidades;
import java.util.Base64;

/**
 * FXML Controller class
 *
 * @author Windows
 */
public class FXMLFormularioColaboradoresController implements Initializable {

    @FXML
    private TextField tfNoPersonal;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfPassword;
    @FXML
    private ComboBox<Rol> cbRol;
    @FXML
    private TextField tfCurp;
    @FXML
    private TextField tfCorreo;
    @FXML
    private ComboBox<SucursalNombres> cbSucursal;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private Button btGuardar;
    @FXML
    private Button btCancelar;
    @FXML
    private TextField tfNoLicencia;
    
    private ObservableList<Rol> roles;
    private INotificador observador;
    private Colaborador colaboradorEdicion;
    private ObservableList<SucursalNombres> sucursales;
    @FXML
    private ImageView ivPerfil;
    private File fotoPerfil;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarRoles();
        cargarSucursales();
        
    }
    
    public void inicializarDatos(Colaborador colaboradorEdicion, INotificador observador) {
        this.colaboradorEdicion = colaboradorEdicion;
        this.observador = observador;
        if (colaboradorEdicion != null) {
            tfNoPersonal.setText(colaboradorEdicion.getNumeroPersonal());
            tfNombre.setText(colaboradorEdicion.getNombre());
            tfApellidoPaterno.setText(colaboradorEdicion.getApellidoPaterno());
            tfApellidoMaterno.setText(colaboradorEdicion.getApellidoMaterno());
            tfPassword.setVisible(false);
            tfNoPersonal.setDisable(true);
            tfCurp.setText(colaboradorEdicion.getCurp());
            tfCorreo.setText(colaboradorEdicion.getCorreo());
            tfNoLicencia.setText(colaboradorEdicion.getNumeroLicencia());
            int pos = obtenerPosicionRol(colaboradorEdicion.getIdRol());
            cbRol.getSelectionModel().select(pos);
            int posSC = obtenerPosicionSucursal(colaboradorEdicion.getIdSucursal());
            cbSucursal.getSelectionModel().select(posSC);
            tfNoPersonal.setDisable(true);
            cbSucursal.setDisable(true);

            if (colaboradorEdicion.getFotoBase64() != null && !colaboradorEdicion.getFotoBase64().isEmpty()) {
                try {
                    byte[] bytes = Base64.getDecoder().decode(colaboradorEdicion.getFotoBase64());
                    Image imagen = new Image(new ByteArrayInputStream(bytes));
                    ivPerfil.setImage(imagen);
                } catch (Exception e) {
                    ivPerfil.setImage(null);
                }
            }
        }
    }

    @FXML
    private void clicBtnGuardar(ActionEvent event) {
        if(sonCamposValidos()){
            Colaborador colaborador = new Colaborador();
            colaborador.setNumeroPersonal(tfNoPersonal.getText());
            colaborador.setNombre(tfNombre.getText());
            colaborador.setApellidoPaterno(tfApellidoPaterno.getText());
            colaborador.setApellidoMaterno(tfApellidoMaterno.getText());
            colaborador.setCurp(tfCurp.getText());
            colaborador.setCorreo(tfCorreo.getText());
            colaborador.setNumeroLicencia(tfNoLicencia.getText());
            colaborador.setPassword(tfPassword.getText());
            Rol rolseleccion = cbRol.getSelectionModel().getSelectedItem();
            colaborador.setIdRol(rolseleccion.getIdRol());
            SucursalNombres sucursalseleccion = cbSucursal.getSelectionModel().getSelectedItem();
            colaborador.setIdSucursal(sucursalseleccion.getIdSucursal());
            subirFoto(colaborador);

            if (colaboradorEdicion == null){
                registrarColaborador(colaborador);
            }else{
                colaborador.setIdColaborador(colaboradorEdicion.getIdColaborador());
                editarColaborador(colaborador);
            }
            cerrarVentana();
        }
    }

    @FXML
    private void clicBtnCancelar(ActionEvent event) {
        cerrarVentana();
    }
    private void cargarRoles(){
        HashMap<String, Object> respuesta = CatalogoImp.obtenerRolesSistema();
        if (!(boolean)respuesta.get(Constantes.KEY_ERROR)){
            List<Rol> listaRoles =(List<Rol>)respuesta.get(Constantes.KEY_LISTA);
            roles = FXCollections.observableArrayList();
            roles.addAll(listaRoles);
            cbRol.setItems(roles);
        }else{
            Utilidades.mostrarAlertaSimple("Error al cargar ", respuesta.get(Constantes.KEY_MENSAJE).toString(),
                    Alert.AlertType.ERROR);
            cerrarVentana();
        }
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
    
    private boolean sonCamposValidos() {
        String noPersonal = tfNoPersonal.getText();
        String nombre = tfNombre.getText();
        String apellidoPaterno = tfApellidoPaterno.getText();
        String apellidoMaterno = tfApellidoMaterno.getText();
        String password = tfPassword.isVisible() ? tfPassword.getText() : "ok";
        String curp = tfCurp.getText();
        String correo = tfCorreo.getText();
        String noLicencia = tfNoLicencia.getText();
        Rol rolSeleccion = cbRol.getSelectionModel().getSelectedItem();
        SucursalNombres sucursalSeleccion = cbSucursal.getSelectionModel().getSelectedItem();

        if (noPersonal == null || noPersonal.trim().isEmpty()
                || nombre == null || nombre.trim().isEmpty()
                || apellidoPaterno == null || apellidoPaterno.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || curp == null || curp.trim().isEmpty()
                || correo == null || correo.trim().isEmpty()
                || rolSeleccion == null
                || sucursalSeleccion == null) {

            Utilidades.mostrarAlertaSimple("Campos incompletos",
                    "Por favor llena todos los campos obligatorios.",
                    Alert.AlertType.WARNING);
            return false;
        }

        if (curp.length() != 18) {
            Utilidades.mostrarAlertaSimple("CURP inválido",
                    "El CURP no debe exceder los 18 caracteres.",
                    Alert.AlertType.WARNING);
            return false;
        }
        
        if ("Conductor".equalsIgnoreCase(rolSeleccion.getRol())) {
            if (noLicencia == null || noLicencia.trim().isEmpty()) {
                Utilidades.mostrarAlertaSimple("Licencia requerida",
                        "Esta registrando a un conductor, por favor ingrese el número de licencia.",
                        Alert.AlertType.WARNING);
                return false;
            }
        }

        return true;
    }

    private void cerrarVentana(){
        ((Stage)tfNombre.getScene().getWindow()).close();
    }
    
    
    private void registrarColaborador(Colaborador colaborador){
        Respuesta respuesta = ColaboradorImp.registrar(colaborador);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Colaborador Registrado",
                    "La información del colaborador "+colaborador.getNombre()+" ha sido guardada correctamente",
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Registro", colaborador.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error al registrar", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void editarColaborador(Colaborador colaborador){
        Respuesta respuesta = ColaboradorImp.editar(colaborador);
        if (!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Colaborador(a) modificado correctamente", 
                    "La información del colaborador "+colaborador.getNombre()+" ha sido modificada correctamente.", 
                    Alert.AlertType.INFORMATION);
            observador.notificarOperacionExitosa("Modificación", colaborador.getNombre());
            cerrarVentana();
        }else{
            Utilidades.mostrarAlertaSimple("Error", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private int obtenerPosicionRol(int idRol){
        for (int i=0; i<roles.size(); i++){
            if (roles.get(i).getIdRol()==idRol)
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

    @FXML
    private void clicBtnCambiarImagen(ActionEvent event) {
        FileChooser dialogoFoto = new FileChooser();
        dialogoFoto.setTitle("Selecciona una foto de perfil");
        FileChooser.ExtensionFilter filtroImg = 
                new FileChooser.ExtensionFilter("Archivos de Imagen(.jpg, .png)", "*.jpg", "*.png");
        dialogoFoto.getExtensionFilters().add(filtroImg);
        fotoPerfil = dialogoFoto.showOpenDialog(ivPerfil.getScene().getWindow());
        if(fotoPerfil !=null){
            mostrarFoto(fotoPerfil);
        }
    }
    
    private void mostrarFoto(File archivoFoto){
        try{
            BufferedImage bifferImg = ImageIO.read(archivoFoto);
            Image imagen = SwingFXUtils.toFXImage(bifferImg,null);
            ivPerfil.setImage(imagen);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    
    private void subirFoto(Colaborador colaborador) {
        if (fotoPerfil != null) {
            try {
                // Extraemos los bytes del archivo seleccionado en el FileChooser
                byte[] bytesImagen = Files.readAllBytes(fotoPerfil.toPath());

                // Asignamos ambos formatos al objeto colaborador
                colaborador.setFotografia(bytesImagen);
                colaborador.setFotoBase64(Base64.getEncoder().encodeToString(bytesImagen));

            } catch (IOException e) {
                Utilidades.mostrarAlertaSimple("Error de imagen",
                        "No se pudo procesar la fotografía seleccionada.",
                        Alert.AlertType.WARNING);
            }
        } else if (colaboradorEdicion != null) {
            // Opcional: Si estamos editando y no se seleccionó foto nueva, 
            // mantenemos la que ya tenía para que no se envíe nulo a la API.
            colaborador.setFotoBase64(colaboradorEdicion.getFotoBase64());
            colaborador.setFotografia(colaboradorEdicion.getFotografia());
        }
    }
}
