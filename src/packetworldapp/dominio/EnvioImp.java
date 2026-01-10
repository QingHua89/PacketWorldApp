package packetworldapp.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldapp.conexion.ConexionAPI;
import packetworldapp.dto.Respuesta;
import packetworldapp.pojo.Colaborador;
import packetworldapp.pojo.Envio;
import packetworldapp.pojo.RespuestaHTTP;
import packetworldapp.utilidades.Constantes;

public class EnvioImp {
    public static HashMap<String,Object> obtenerTodos(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "envios/todos";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
            List<Envio> envios =
                gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("envios", envios);
        }else{
            respuesta.put("error", true);
            switch(respuestaAPI.getCodigo()){
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_URL);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put("mensaje","Lo sentimos hay problemas para "
                        + "obtener la información en este momento, por favor inténtelo más tarde.");
            }
        }
        return respuesta;
    }
    
    public static Respuesta registrar(Envio envio){
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "envios/nuevo";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(envio);
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL,
        Constantes.METODO_POST, parametrosJSON, Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
            }else{
            respuesta.setError(true);
                switch(respuestaAPI.getCodigo()){
                    case Constantes.ERROR_MALFORMED_URL:
                        respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                        break;
                    case Constantes.ERROR_PETICION:
                        respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                        break;
                    case HttpURLConnection.HTTP_BAD_REQUEST:
                        respuesta.setMensaje("Campos en formato incorrecto, "
                        + "+por favor verifica toda la información enviada.");
                        break;
                    default:
                        respuesta.setMensaje("Lo sentimos hay problemas para "
                        + "registrar la información en este momento, por favor inténtelo más tarde.");
            }
        }
        return respuesta;
    }
    
    public static Respuesta editar(Envio envio) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "envios/editar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(envio);
        RespuestaHTTP respuestaAPI
                = ConexionAPI.peticionBody(URL, "PUT", parametrosJSON,
                        Constantes.CONTENT_JSON);
        if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
            respuesta = gson.fromJson(respuestaAPI.getContenido(), Respuesta.class);
        } else {
            respuesta.setError(true);
            switch (respuestaAPI.getCodigo()) {
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                    break;
                case HttpURLConnection.HTTP_BAD_REQUEST:
                    respuesta.setMensaje("Campos en formato incorrecto, "
                            + "+por favor verifica toda la información enviada.");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos hay problemas para "
                            + "editar la información en este momento, por favor inténtelo más tarde.");
            }
        }
        return respuesta;
    }
    
    public static HashMap<String,Object> busqueda(String busqueda){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "envios/buscar/"+busqueda;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Envio>>(){}.getType();
            List<Envio> envios =
                gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("envios", envios);
        }else{
            respuesta.put("error", true);
            switch(respuestaAPI.getCodigo()){
                case Constantes.ERROR_MALFORMED_URL:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_URL);
                    break;
                case Constantes.ERROR_PETICION:
                    respuesta.put("mensaje", Constantes.MSJ_ERROR_PETICION);
                    break;
                default:
                    respuesta.put("mensaje","Lo sentimos hay problemas para "
                        + "obtener la información en este momento, por favor inténtelo más tarde.");
            }
        }
        return respuesta;
    }
}
