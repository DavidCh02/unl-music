package com.unl.music.base.controller;

import com.unl.music.base.controller.data_struct.list.LinkedList;

import java.io.BufferedReader;
import java.io.FileReader;

public class Practica {

    private Integer[] matriz;
    private LinkedList<Integer> lista;

    public void cargar() {
        lista = new LinkedList<>();
        try (BufferedReader fb = new BufferedReader(new FileReader("data/data.txt"))) {
            String line;
            while ((line = fb.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lista.add(Integer.parseInt(line.trim()));
                }
            }
            System.out.println("Datos cargados: " + lista.getLength());
        } catch (Exception e) {
            System.err.println("Error al cargar datos: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private Boolean verificar_numero_arreglo(Integer a) {
        int cont = 0;
        for (Integer valor : matriz) {
            if (a.equals(valor)) cont++;
            if (cont >= 2) return true;
        }
        return false;
    }

    public String[] verificar_arreglo() {
        StringBuilder resp = new StringBuilder();
        for (Integer num : matriz) {
            if (verificar_numero_arreglo(num)) {
                resp.append(num).append("-");
            }
        }
        return resp.toString().split("-");
    }

    private Boolean verificar_numero_lista(Integer a) {
        int cont = 0;
        for (int i = 0; i < lista.getLength(); i++) {
            if (a.equals(lista.get(i))) cont++;
            if (cont >= 2) return true;
        }
        return false;
    }

    public LinkedList<Integer> verificar_lista() {
        LinkedList<Integer> resp = new LinkedList<>();
        for (int i = 0; i < lista.getLength(); i++) {
            Integer valor = lista.get(i);
            if (verificar_numero_lista(valor)) {
                resp.add(valor);
            }
        }
        return resp;
    }

    public LinkedList<Integer> burbuja() {
        cargar();
        Integer cont = 0;
        long startTime = System.currentTimeMillis();

        if (!lista.isEmpty()) {
            Integer[] m = lista.toArray();
            for (int i = 0; i < m.length - 1; i++) {
                for (int j = 0; j < m.length - 1 - i; j++) {
                    if (m[j] > m[j + 1]) {
                        Integer aux = m[j];
                        m[j] = m[j + 1];
                        m[j + 1] = aux;
                        cont++;
                    }
                }
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Burbuja: " + endTime + "ms, intercambios: " + cont);
            lista.toList(m);
        }

        return lista;
    }

    public LinkedList<Integer> burbujaMejorado() {
        cargar();
        int intercambios = 0;
        long startTime = System.currentTimeMillis();

        if (!lista.isEmpty()) {
            Integer[] arreglo = lista.toArray();
            int i, izq = 1, der = arreglo.length - 1, k = der;
            while (izq <= der) {
                for (i = der; i >= izq; i--) {
                    if (arreglo[i - 1] > arreglo[i]) {
                        int aux = arreglo[i - 1];
                        arreglo[i - 1] = arreglo[i];
                        arreglo[i] = aux;
                        k = i;
                        intercambios++;
                    }
                }
                izq = k + 1;
                for (i = izq; i <= der; i++) {
                    if (arreglo[i - 1] > arreglo[i]) {
                        int aux = arreglo[i - 1];
                        arreglo[i - 1] = arreglo[i];
                        arreglo[i] = aux;
                        k = i;
                        intercambios++;
                    }
                }
                der = k - 1;
            }

            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Burbuja Mejorado: " + endTime + "ms, intercambios: " + intercambios);
            lista.toList(arreglo);
        }
        return lista;
    }

    public LinkedList<Integer> insercion() {
        cargar();
        Integer cont = 0;
        long startTime = System.currentTimeMillis();

        if (!lista.isEmpty()) {
            Integer[] array = lista.toArray();
            for (int i = 1; i < array.length; i++) {
                int key = array[i];
                int j = i - 1;
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                    cont++;
                }
                array[j + 1] = key;
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Inserción: " + endTime + "ms, movimientos: " + cont);
            lista.toList(array);
        }
        return lista;
    }

    public LinkedList<Integer> seleccion(Integer type) {
        cargar();
        Integer cont = 0;
        long startTime = System.currentTimeMillis();

        if (!lista.isEmpty()) {
            Integer[] arr = lista.toArray();
            for (int i = 0; i < arr.length - 1; i++) {
                int idx = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if ((type == 1 && arr[j] < arr[idx]) || (type != 1 && arr[j] > arr[idx])) {
                        idx = j;
                        cont++;
                    }
                }
                int temp = arr[idx];
                arr[idx] = arr[i];
                arr[i] = temp;
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("Selección: " + endTime + "ms, comparaciones: " + cont);
            lista.toList(arr);
        }
        return lista;
    }

    private void quickSort(Integer[] arr, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end);
            quickSort(arr, begin, partitionIndex - 1);
            quickSort(arr, partitionIndex + 1, end);
        }
    }

    private int partition(Integer[] arr, int begin, int end) {
        int pivot = arr[end];
        int i = begin - 1;
        for (int j = begin; j < end; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = temp;
        return i + 1;
    }

    public long q_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer[] arr = lista.toArray();
            long startTime = System.currentTimeMillis();
            quickSort(arr, 0, arr.length - 1);
            long endTime = System.currentTimeMillis() - startTime;
            lista.toList(arr);
            return endTime;
        }
        return -1;
    }

    public long s_order() {
        cargar();
        if (!lista.isEmpty()) {
            Integer[] arr = lista.toArray();
            long startTime = System.currentTimeMillis();
            shell_sort(arr);
            long endTime = System.currentTimeMillis() - startTime;
            lista.toList(arr);
            return endTime;
        }
        return -1;
    }

    public void shell_sort(Integer[] array) {
        int n = array.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int key = array[i];
                int j = i;
                while (j >= gap && array[j - gap] > key) {
                    array[j] = array[j - gap];
                    j -= gap;
                }
                array[j] = key;
            }
        }
    }

    public static void main(String[] args) {
        Practica p1 = new Practica();
        long tiempoQuick = p1.q_order();

        Practica p2 = new Practica();
        long tiempoShell = p2.s_order();

        System.out.println("--- Tiempos de Ordenamiento ---");
        System.out.println("-------------------------------");
        System.out.printf("| %-15s | %-10s |\n", "Algoritmo", "Tiempo (ms)");
        System.out.println("-------------------------------");
        System.out.printf("| %-15s | %-10d |\n", "Quicksort", tiempoQuick);
        System.out.printf("| %-15s | %-10d |\n", "Shellsort", tiempoShell);
        System.out.println("-------------------------------");
    }
}
