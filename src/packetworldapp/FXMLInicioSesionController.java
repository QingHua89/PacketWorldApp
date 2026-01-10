package packetworldapp;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import packetworldapp.dominio.InicioSesionImp;
import packetworldapp.dto.RespuestaInicioSesion;
import packetworldapp.pojo.Colaborador;
import packetworldapp.utilidades.Utilidades;

public class FXMLInicioSesionController implements Initializable {

    @FXML
    private TextField tfNumPersonal;
    @FXML
    private Label lbErrorPersonal;
    @FXML
    private Label lbErrorPassword;
    @FXML
    private PasswordField pfPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ClicInicioSesion(ActionEvent event) {
        String numeroPersonal = tfNumPersonal.getText();
        String password = pfPassword.getText();
        if (sonCamposValidos(numeroPersonal,password)){
            validarCredenciales(numeroPersonal,password);
        }
    }
    
    private boolean sonCamposValidos(String numeroPersonal, String password) {
        boolean valido = true;
        lbErrorPersonal.setText("");
        lbErrorPassword.setText("");
        if (numeroPersonal.isEmpty()) {
            valido = false;
            lbErrorPersonal.setText("No. de Personal requerido.");
        }
        if (password.isEmpty()) {
            valido = false;
            lbErrorPassword.setText("Contraseña requerida.");
        }
        return valido;
    }
    private void validarCredenciales (String numeroPersonal, String password){
        RespuestaInicioSesion respuesta = InicioSesionImp.verificarCredenciales(numeroPersonal, password);
        if(!respuesta.isError()){
            Utilidades.mostrarAlertaSimple("Credenciales Correctas",
                    respuesta.getMensaje(), Alert.AlertType.INFORMATION);
            irPantallaPrincipal(respuesta.getColaborador());
        }else{
            Utilidades.mostrarAlertaSimple("Error en la validación.", respuesta.getMensaje(), Alert.AlertType.ERROR);
        }
    }
    
    private void  irPantallaPrincipal(Colaborador colaborador){
        try{
            FXMLLoader cargador = new FXMLLoader(getClass().getResource("FXMLPrincipal.fxml"));
            Parent vista = cargador.load();
            FXMLPrincipalController controlador = cargador.getController();
            controlador.inicializarDatos(colaborador);
            
            Scene scPrincipal = new Scene(vista);
            Stage primaryStage = (Stage) tfNumPersonal.getScene().getWindow();
            primaryStage.setScene(scPrincipal);
            primaryStage.setTitle("Principal");
            primaryStage.show();
            
        }catch(IOException ex){
            ex.printStackTrace();
        } 
    }
}
