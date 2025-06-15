package com.unl.music.base.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFormatter {
    private static final String DATA_PATH = "c:\\Users\\David\\Desktop\\estructura de datos 2\\unl-music\\data\\";

    public static void formatJsonFile(String fileName) {
        try {
            // Read the JSON file
            String jsonString = new String(Files.readAllBytes(Paths.get(DATA_PATH + fileName)));
            
            // Create Gson instance with pretty printing
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
            
            // Parse and format
            Object jsonObject = gson.fromJson(jsonString, Object.class);
            String prettyJson = gson.toJson(jsonObject);
            
            // Write back to file
            try (FileWriter writer = new FileWriter(DATA_PATH + fileName)) {
                writer.write(prettyJson);
            }
            
            System.out.println("JSON file " + fileName + " has been formatted successfully.");
        } catch (IOException e) {
            System.err.println("Error formatting JSON file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        formatJsonFile("Cancion.json");
        formatJsonFile("Album.json");
        formatJsonFile("Genero.json");
        formatJsonFile("Artista.json");
        formatJsonFile("Banda.json");        
    }
}