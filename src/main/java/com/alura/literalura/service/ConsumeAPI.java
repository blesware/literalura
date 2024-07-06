package com.alura.literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;

public class ConsumeAPI {

    //Metodo para consultar la API y obtener un JSON
    public static String obtenerDatos(String url) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {

            throw new RuntimeException(e);

        } catch (InterruptedException e) {

            throw new RuntimeException(e);
        }

        String json = response.body();
        return json;
    }
}
