package packetworldapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import packetworldapp.pojo.Colaborador;

public class FXMLPrincipalController implements Initializable {

    @FXML
    private Label lbNombre;
    @FXML
    private Button btColaborador;
    @FXML
    private Button btClientes;
    @FXML
    private Button btPaquetes;
    @FXML
    private Button btSuscursales;
    @FXML
    private Button btUnidades;
    @FXML
    private Button btEnvios;
    @FXML
    private Button btAsignar;
    @FXML
    private ImageView ivCerrarSesion;
    private Colaborador colaborador;
    @FXML
    private Label lbNumPErsonal;
    @FXML
    private Label lbRol;
    @FXML
    private Label lbSucursal;
    @FXML
    private ImageView ivFotoPerfil;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void inicializarDatos(Colaborador colaborador){
        this.colaborador=colaborador;
        lbNombre.setText(colaborador.getNombre()+" "+colaborador.getApellidoPaterno()+" "+colaborador.getApellidoMaterno());
        lbNumPErsonal.setText(colaborador.getNumeroPersonal());
        lbRol.setText(colaborador.getRol());
        lbSucursal.setText(colaborador.getSucursalNombre());
    }

    @FXML
    private void ClicIrColaboradores(ActionEvent event) {
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAdminColaboradores.fxml"));
            Parent vista = cargador.load();
            FXMLAdminColaboradoresController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btColaborador.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Administrador de Colaboradores");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicIrClientes(ActionEvent event) {
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAdminClientes.fxml"));
            Parent vista = cargador.load();
            FXMLAdminClientesController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btClientes.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Administrador de Clientes");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicIrPaquetes(ActionEvent event) {
    }

    @FXML
    private void ClicIrSucursales(ActionEvent event) {
        try{
            FXMLLoader cargador2 = new FXMLLoader(getClass().getResource("FXMLAdminSucursal.fxml"));
            Parent vista = cargador2.load();
            FXMLAdminSucursalController controlador = cargador2.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btSuscursales.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Administrador de Sucursales");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicIrUnidades(ActionEvent event) {
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAdminUnidades.fxml"));
            Parent vista = cargador.load();
            FXMLAdminUnidadesController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btUnidades.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Administrador de Unidades");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicIrEnvio(ActionEvent event) {
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAdminEnvios.fxml"));
            Parent vista = cargador.load();
            FXMLAdminEnviosController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btEnvios.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Administrador de Envios");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    @FXML
    private void ClicAsignarUnidad(ActionEvent event) {
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLAdminAsignacion.fxml"));
            Parent vista = cargador.load();
            FXMLAdminAsignacionController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scColaborador = new Scene(vista);
            Stage primaryStage = (Stage) btAsignar.getScene().getWindow();
            primaryStage.setScene(scColaborador);
            primaryStage.setTitle("Asignación de Unidades");
            primaryStage.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void ClicCerrarSesion(MouseEvent event) {
        try{
            Parent vista1 = FXMLLoader.load(getClass().getResource("FXMLInicioSesion.fxml"));
            Scene scSalir = new Scene(vista1);
            Stage cerrarSesion = (Stage) ivCerrarSesion.getScene().getWindow();
            cerrarSesion.setScene(scSalir);
            cerrarSesion.setTitle("Inicio de Sesión");
            cerrarSesion.show();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    private void mostrarFoto(File archivoFoto){
        try{
            BufferedImage bifferImg = ImageIO.read(archivoFoto);
            Image imagen = SwingFXUtils.toFXImage(bifferImg,null);
            ivFotoPerfil.setImage(imagen);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
