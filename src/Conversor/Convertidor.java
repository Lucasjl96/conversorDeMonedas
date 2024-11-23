package Conversor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class Convertidor {

    private final HttpClient client;
    private final Gson gson;

    public Convertidor() {

        client = HttpClient.newHttpClient();
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }


    public String realizarSolicitud(String apiUrl) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .build();


        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        if (response.statusCode() != 200) {
            throw new IOException("Error en la API: Código de estado " + response.statusCode());
        }

        return response.body();
    }


    public Map<String, Object> procesarRespuesta(String respuestaJson) {
        return gson.fromJson(respuestaJson, Map.class);
    }
}


