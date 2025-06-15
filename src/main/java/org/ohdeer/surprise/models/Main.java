package org.ohdeer.surprise.models;
import org.ohdeer.surprise.data_struct.LinkedList;
import org.ohdeer.surprise.data_struct.ArrayOperations;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\David\\Documents\\Practica numero 2\\practica-dos\\src\\main\\java\\org\\ohdeer\\surprise\\models\\data.txt";

        List<Integer> data = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            while (fileScanner.hasNextInt()) {
                data.add(fileScanner.nextInt());
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: Archivo no encontrado en la ruta especificada.");
            return;
        }

        ArrayOperations arrayOps = new ArrayOperations(data.size());
        LinkedList linkedList = new LinkedList();

        for (int i = 0; i < data.size(); i++) {
            arrayOps.set(i, data.get(i));
            linkedList.add(data.get(i));
        }

        System.out.println("\n=== Análisis de Rendimiento y Comparación ===");
        System.out.println("\nTamaño total de datos: " + arrayOps.getSize() + " elementos");
        
        System.out.println("\n=== Prueba de Rendimiento ===");
        System.out.printf("%-15s %-25s %-25s\n", "Prueba", "Arreglo (ns)", "Lista Enlazada (ns)");
        System.out.println("------------------------------------------------");

        List<Integer> duplicatesArray = null;
        List<Integer> duplicatesLinkedList = null;
        long totalArrayTime = 0;
        long totalLinkedListTime = 0;

        for (int i = 1; i <= 3; i++) {
            long startTimeArray = System.nanoTime();
            duplicatesArray = arrayOps.findDuplicates();
            long timeArray = System.nanoTime() - startTimeArray;
            totalArrayTime += timeArray;

            long startTimeLinkedList = System.nanoTime();
            duplicatesLinkedList = linkedList.findDuplicates();
            long timeLinkedList = System.nanoTime() - startTimeLinkedList;
            totalLinkedListTime += timeLinkedList;

            System.out.printf("%-15d %-25d %-25d\n", i, timeArray, timeLinkedList);
        }

        System.out.println("\n=== Resultados del Análisis ===");
        System.out.println("Tiempo promedio del Arreglo: " + (totalArrayTime / 3) + " ns");
        System.out.println("Tiempo promedio de Lista Enlazada: " + (totalLinkedListTime / 3) + " ns");
        
        System.out.println("\nDuplicados encontrados:");
        System.out.println("- Arreglo: " + duplicatesArray.size() + " elementos");
        System.out.println("- Lista Enlazada: " + duplicatesLinkedList.size() + " elementos");

        System.out.println("\nCaracterísticas de las estructuras:");
        System.out.println("- Arreglo: Acceso directo, memoria contigua");
        System.out.println("- Lista Enlazada: Acceso secuencial, memoria dinámica");
    }
}