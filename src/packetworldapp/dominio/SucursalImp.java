package packetworldapp.dominio;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import packetworldapp.conexion.ConexionAPI;
import packetworldapp.dto.Respuesta;
import packetworldapp.pojo.RespuestaHTTP;
import packetworldapp.pojo.Sucursal;
import packetworldapp.utilidades.Constantes;

public class SucursalImp {
    public static HashMap<String,Object> obtenerTodas(){
        HashMap<String, Object> respuesta = new LinkedHashMap<>();
        String URL = Constantes.URL_WS + "sucursal/obtener-todas";
        RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);
        if(respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK){
            Gson gson = new Gson();
            Type tipoLista = new TypeToken<List<Sucursal>>(){}.getType();
            List<Sucursal> sucursales =
                gson.fromJson(respuestaAPI.getContenido(), tipoLista);
            respuesta.put("error", false);
            respuesta.put("sucursales", sucursales);
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
    
    public static Respuesta registrar(Sucursal sucursal){
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "sucursal/nuevo";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(sucursal);
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
    
    public static Respuesta editar(Sucursal sucursal) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "sucursal/editar";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(sucursal);
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
    
    public static Respuesta darBaja(Sucursal sucursal) throws UnsupportedEncodingException {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS+"sucursal/baja";
        Gson gson = new Gson();
        String parametrosJSON = gson.toJson(sucursal);

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
    
}
