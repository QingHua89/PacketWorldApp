package packetworldapp.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldapp.conexion.ConexionAPI;
import packetworldapp.dto.Respuesta;
import packetworldapp.pojo.Paquete;
import packetworldapp.pojo.RespuestaHTTP;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Constantes;

public class PaqueteImp {
    public static HashMap<String,Object> obtenerPaquetes(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "paquete/obtener-todos";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Paquete>>(){}.getType();
            List<Paquete> paquetes =
                gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("paquetes", paquetes);
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
    
    public static Respuesta registrar(Paquete paquete){
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "paquete/nuevo";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);
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
    
    public static Respuesta editar(Paquete paquete) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "paquete/editar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);
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
    
    /*public static Respuesta eliminarEnvio(int idPaquete) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "paquete/eliminar/"+idPaquete;
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);
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
    }*/
    
    public static Respuesta darBaja(Paquete paquete){
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS+"paquete/eliminar-envio/";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);

        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL,"PUT",parametrosJSON,Constantes.CONTENT_JSON);
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
                    respuesta.setMensaje(
                            "No fue posible dar de baja la sucursal. "
                            + "Verifica el motivo y la información enviada."
                    );
                    break;
                default:
                    respuesta.setMensaje(
                            "Lo sentimos, hay problemas para dar de baja la sucursal "
                            + "en este momento, por favor inténtalo más tarde."
                    );
            }
        }
        return respuesta;
    }
    
    public static Respuesta agregarGuia(Paquete paquete) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "paquete/agregar-envio";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(paquete);
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionBody(URL, "PUT", parametrosJSON, Constantes.CONTENT_JSON);

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
                    respuesta.setMensaje("No fue posible vincular el paquete al envío. "
                            + "Verifica la información enviada.");
                    break;
                default:
                    respuesta.setMensaje("Lo sentimos, hay problemas para asignar la guía al paquete "
                            + "en este momento. Inténtalo más tarde.");
            }
        }
        return respuesta;
    }
    
    public static HashMap<String,Object> busqueda(String busqueda){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "paquete/buscar/"+busqueda;
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Paquete>>(){}.getType();
            List<Paquete> paquetes =
                gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("paquetes", paquetes);
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
