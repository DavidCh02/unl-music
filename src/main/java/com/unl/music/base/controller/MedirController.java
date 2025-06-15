package com.unl.music.base.controller;

import com.unl.music.base.controller.data_struct.list.LinkedList;

import com.unl.music.base.controller.data_struct.list.Node;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MedirController {
    private LinkedList<Integer> linkedList;
    private Integer[] array;
    private LinkedList<Integer> duplicatesLinkedList;
    private Integer[] duplicatesArray;
    private int size;

    public MedirController() {
        this.linkedList = new LinkedList<>();
        this.duplicatesLinkedList = new LinkedList<>();
    }

    public void readFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        String line;
        int count = 0;
        
        while ((line = reader.readLine()) != null) {
            count++;
        }
        reader.close();
        
        this.size = count;
        this.array = new Integer[size];
        this.duplicatesArray = new Integer[size];
        
        reader = new BufferedReader(new FileReader(path));
        int index = 0;
        while ((line = reader.readLine()) != null) {
            Integer number = Integer.parseInt(line.trim());
            array[index] = number;
            linkedList.add(number);
            index++;
        }
        reader.close();
    }

    public long findDuplicatesArray() {
        long startTime = System.nanoTime();
        int duplicateCount = 0;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                if (array[i].equals(array[j])) {
                    duplicatesArray[duplicateCount++] = array[i];
                    break;
                }
            }
        }
        
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public long findDuplicatesLinkedList() throws Exception {
        long startTime = System.nanoTime();
        
        for (int i = 0; i < linkedList.getLength(); i++) {
            Integer currentValue = linkedList.get(i);
            for (int j = i + 1; j < linkedList.getLength(); j++) {
                if (currentValue.equals(linkedList.get(j))) {
                    duplicatesLinkedList.add(currentValue);
                    break;
                }
            }
        }
        
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    public void runPerformanceComparison() throws Exception {
        System.out.println("\n=== Comparación de Rendimiento ===");
        System.out.println("Ejecutando 3 iteraciones...\n");
        
        System.out.printf("%-15s %-20s %-20s%n", "Iteración", "Tiempo Array (ns)", "Tiempo Lista (ns)");
        System.out.println("------------------------------------------------");
        
        for (int i = 1; i <= 3; i++) {
            duplicatesArray = new Integer[size];
            duplicatesLinkedList.clear();
            
            long arrayTime = findDuplicatesArray();
            long listTime = findDuplicatesLinkedList();
            
            System.out.printf("%-15d %-20d %-20d%n", i, arrayTime, listTime);
        }
        
        System.out.println("\n=== Conteo de Elementos Duplicados ===");
        int arrayCount = 0;
        for (Integer element : duplicatesArray) {
            if (element != null) {
                arrayCount++;
            }
        }
        int listCount = duplicatesLinkedList.getLength();
        System.out.println("Duplicados en Array: " + arrayCount);
        System.out.println("Duplicados en Lista Enlazada: " + listCount);
    }

    public static void main(String[] args) {
        try {
            MedirController controller = new MedirController();
            
            System.out.println("=== Programa de Comparación de Rendimiento ===");
            System.out.print("Ingrese la ruta del archivo de datos: ");
            
            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            String filePath = consoleReader.readLine();
            
            controller.readFile(filePath);
            controller.runPerformanceComparison();
            
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ha ocurrido un error: " + e.getMessage());
        }
    }
}
