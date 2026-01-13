package packetworldapp.dominio;

import java.net.HttpURLConnection;
import com.google.gson.Gson;
import packetworldapp.conexion.ConexionAPI;
import packetworldapp.dto.Respuesta;
import packetworldapp.pojo.RespuestaHTTP;
import packetworldapp.utilidades.Constantes;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class DistanciaImp {

    public static Respuesta obtenerDistancia(String origen, String destino) {
        Respuesta respuesta = new Respuesta();
        String URL = Constantes.URL_WS + "distancia/" + origen + "/" + destino;

        try {
            RespuestaHTTP respuestaAPI = ConexionAPI.peticionGET(URL);

            if (respuestaAPI.getCodigo() == HttpURLConnection.HTTP_OK) {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(respuestaAPI.getContenido(), JsonObject.class);

                boolean error = json.get("error").getAsBoolean();
                respuesta.setError(error);

                if (!error) {
                    double distanciaKM = json.get("distanciaKM").getAsDouble();
                    respuesta.setMensaje(String.valueOf(distanciaKM));
                } else {
                    respuesta.setMensaje(json.get("mensaje").getAsString());
                }

            } else {
                respuesta.setError(true);
                switch (respuestaAPI.getCodigo()) {
                    case Constantes.ERROR_MALFORMED_URL:
                        respuesta.setMensaje(Constantes.MSJ_ERROR_URL);
                        break;
                    case Constantes.ERROR_PETICION:
                        respuesta.setMensaje(Constantes.MSJ_ERROR_PETICION);
                        break;
                    default:
                        respuesta.setMensaje("Lo sentimos, hay problemas para obtener la información en este momento.");
                }
            }

        } catch (Exception e) {
            respuesta.setError(true);
            respuesta.setMensaje("Error al consultar la API: " + e.getMessage());
        }

        return respuesta;
    }
}
