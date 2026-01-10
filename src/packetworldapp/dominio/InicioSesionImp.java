package packetworldapp.dominio;

import com.google.gson.Gson;
import packetworldapp.conexion.ConexionAPI;
import packetworldapp.dto.RespuestaInicioSesion;
import packetworldapp.pojo.RespuestaHTTP;
import packetworldapp.utilidades.Constantes;
import java.net.HttpURLConnection;


public class InicioSesionImp {
    
    public static RespuestaInicioSesion verificarCredenciales (String numeroPersonal, String password){
        RespuestaInicioSesion respuesta = new RespuestaInicioSesion();
        String URL = Constantes.URL_WS + "autenticacion/colaborador";
        String parametros = "numeroPersonal="+numeroPersonal+"&password="+password;
        RespuestaHTTP respuestaAPI = 
                ConexionAPI.peticionBody(URL, Constantes.METODO_POST, parametros, Constantes.CONTENT_FORM);
        if(respuestaAPI.getCodigo()==HttpURLConnection.HTTP_OK){
            //Crear objeto
            try{
                Gson gson = new Gson();
                respuesta = gson.fromJson(respuestaAPI.getContenido(), RespuestaInicioSesion.class);
            }catch (Exception e){
                respuesta.setError(true);
                respuesta.setMensaje(e.getMessage());
            }
        }else{
            //Error en peticion
            respuesta.setError(true);
            switch (respuestaAPI.getCodigo()){
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje("Lo sentimos hubo un error al intentar conectar con el servicio.");
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje("Lo sentimos, hubo un error al leer la información.");
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje("Lo sentimos, la información no tiene el formato correcto.");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos, hubo un error con la respuesta del servicio.");
            }
        }
        return respuesta;
    }
}