package com.jolmoz.puzzle8webapp.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RestController
public class WebController {

    /*
     * Este servicio web recibe un arreglo de 9 numeros que representa una matriz 3x3 del puzzle 8
     */
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/api/solve")
    public Object receiveData(@RequestBody int[] data) {
        
        Gson gson = new Gson();
        // Se construye un objeto para responder la solicitud
        JsonObject response = new JsonObject();

        System.out.println("Datos recibidos");
        try {
            Map<String, List<?>> puzzleSolved = PuzzleControl.solvePuzzle(data);

            response.addProperty("msg", "Datos recibidos correctamente");
            response.add("actions", gson.toJsonTree(puzzleSolved.get("actions")));
            response.add("states", gson.toJsonTree(puzzleSolved.get("states")));

        } catch (Exception e) {
            System.out.println("Datos incorrectos");
            response.addProperty("msg", "Datos incorrectos");
        }
        return gson.toJson(response);
    }
}
