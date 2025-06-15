package com.unl.music.base.controller.dao.dao_models;

import java.util.HashMap;

import com.unl.music.base.controller.Utiles; // Asumo que Utiles tiene ASCEDENTE y DESCENDENTE
import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.controller.data_struct.list.LinkedList;
import com.unl.music.base.models.Album;
import com.unl.music.base.models.Cancion;
import com.unl.music.base.models.Genero;


public class DaoCancion extends AdapterDao<Cancion> {
    private Cancion obj;
    private LinkedList<Cancion> listAll;

    public DaoCancion() {
        super(Cancion.class);
        // TODO Auto-generated constructor stub
    }

    // getter and setter
    public Cancion getObj() {
        if (obj == null) {
            this.obj = new Cancion();

        }
        return this.obj;
    }

    public void setObj(Cancion obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {

            return false;
            // TODO: handle exception
        }
    }



    public Boolean update(Integer pos) {
        try {
            this.update(obj, pos);
            return true;
        } catch (Exception e) {

            return false;
            // TODO: handle exception
        }
    }

    public LinkedList<Cancion> getListAll() {
        if (listAll == null) {
            listAll = listAll();
        }
        return listAll;
    }


public LinkedList<HashMap<String, String>> all() throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        LinkedList<Cancion> canciones = this.listAll();

        if (!canciones.isEmpty()) {
            Album[] albumes = new DaoAlbum().listAll().toArray();
            Genero[] generos = new DaoGenero().listAll().toArray();

            Cancion[] arreglo = canciones.toArray();
            for (int i = 0; i < arreglo.length; i++) {
                lista.add(toDict(arreglo[i], albumes, generos));
            }
        }
        return lista;
    }


     private HashMap<String, String> toDict(Cancion cancion, Album[] albumes, Genero[] generos) {
        HashMap<String, String> db = new HashMap<>();
        db.put("id", cancion.getId() != null ? cancion.getId().toString() : "0");
        db.put("nombre", cancion.getNombre() != null ? cancion.getNombre() : "");
        db.put("duracion", cancion.getDuracion() != null ? cancion.getDuracion().toString() : "0");
        db.put("url", cancion.getUrl() != null ? cancion.getUrl() : "");
        db.put("tipo", cancion.getTipo() != null ? cancion.getTipo().toString() : "");
        db.put("id_genero", cancion.getId_genero() != null ? cancion.getId_genero().toString() : "0");
        db.put("id_album", cancion.getId_album() != null ? cancion.getId_album().toString() : "0");

        String nombreGenero = "Desconocido";
        if (cancion.getId_genero() != null && cancion.getId_genero() > 0 && cancion.getId_genero() <= generos.length) {
            nombreGenero = generos[cancion.getId_genero() - 1].getNombre();
        }
        db.put("genero", nombreGenero);
        String nombreAlbum = "Desconocido";
        if (cancion.getId_album() != null && cancion.getId_album() > 0 && cancion.getId_album() <= albumes.length) {
            nombreAlbum = albumes[cancion.getId_album() - 1].getNombre();
        }
        db.put("album", nombreAlbum);

        return db;
    }
    /*private int partition(Cancion arr[], int begin, int end, Integer type) {
        // hashmap //clave - valor
        // Calendar cd = Calendar.getInstance();

        Cancion pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].getNombre().toLowerCase().compareTo(pivot.getNombre().toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    Cancion swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        Cancion swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }*/


    private int partition(HashMap<String, String>  arr[], int begin, int end, Integer type, String attribute) {

        HashMap<String, String> pivot = arr[end];
        int i = (begin - 1);
        if (type == Utiles.ASCEDENTE) {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.get(attribute).toLowerCase()) < 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        } else {
            for (int j = begin; j < end; j++) {
                if (arr[j].get(attribute).toString().toLowerCase().compareTo(pivot.get(attribute).toLowerCase()) > 0) {
                    // if (arr[j] <= pivot) {
                    i++;
                    HashMap<String, String> swapTemp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = swapTemp;
                }
            }
        }
        HashMap<String, String> swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }



     /*private void quickSort(Cancion arr[], int begin, int end, Integer type) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type);

            quickSort(arr, begin, partitionIndex - 1, type);
            quickSort(arr, partitionIndex + 1, end, type);
        }
    }*/

      private void quickSort(HashMap<String, String> arr[], int begin, int end, Integer type, String attribute) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, type, attribute);

            quickSort(arr, begin, partitionIndex - 1, type, attribute);
            quickSort(arr, partitionIndex + 1, end, type, attribute);
        }
    }


    public LinkedList<HashMap<String, String>> orderByArtist(Integer type, String attribute) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        if (!lista.isEmpty()) {
            HashMap arr[] = lista.toArray();
            int n = arr.length;
            if (type == Utiles.ASCEDENTE) {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) < 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            } else {
                for (int i = 0; i < n - 1; i++) {
                    int min_idx = i;
                    for (int j = i + 1; j < n; j++)
                        if (arr[j].get(attribute).toString().toLowerCase()
                                .compareTo(arr[min_idx].get(attribute).toString().toLowerCase()) > 0) {
                            min_idx = j;
                        }

                    HashMap temp = arr[min_idx];
                    arr[min_idx] = arr[i];
                    arr[i] = temp;
                }
            }
        }
        return lista;
    }


     public LinkedList<HashMap<String, String>> orderQ(Integer type, String attribute)throws Exception {
        LinkedList<HashMap<String, String>> lista = new LinkedList<>();
        if (!all().isEmpty()) {

            HashMap<String, String> arr[] = all().toArray();
            quickSort(arr, 0, arr.length - 1, type, attribute);
            lista.toList(arr);
        }
        return lista;
    }
     /*public LinkedList<Cancion> orderQ(Integer type) {
        LinkedList<Cancion> lista = new LinkedList<>();
        if (!listAll().isEmpty()) {

            Cancion arr[] = listAll().toArray();
            quickSort(arr, 0, arr.length - 1, type);
            lista.toList(arr);
        }
        return lista;
    }*/



    public LinkedList<HashMap<String, String>> search(String attribute, String text, Integer type) throws Exception {
        LinkedList<HashMap<String, String>> lista = all();
        LinkedList<HashMap<String, String>> resp = new LinkedList<>();

        if (!lista.isEmpty()) {
           lista = orderQ(Utiles.ASCEDENTE, attribute);
            HashMap<String, String>[] arr = lista.toArray();
            Integer n = bynaryLineal(arr, attribute, text);
            System.out.println("LA N DE LA MITAD"+n);
            switch (type) {
                case 1:// Utiles START
                if (n > 0) {
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }else if (n < 0) {
                    n *= -1;
                    for (int i = 0; i < n; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }

                } else {
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().startsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                    break;
                case 2:// Utiles.END
                // escogemos la derecha
                if (n > 0) {
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }else if (n < 0) {
                    // escogemos la izquierda desde 0 hasta n
                    n *= -1;
                    for (int i = 0; i < n; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }

                } else {
                    // escogemos todo
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().endsWith(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }
                    break;
                default:
                  System.out.println(attribute+" "+text+" TRES");
                 // escogemos la derecha
               /* if (n > 0) {
                    for (int i = n; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }else if (n < 0) {
                    // escogemos la izquierda desde 0 hasta n
                    n *= -1;
                    for (int i = 0; i < n; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }

                } else {
                    // escogemos todo
                    for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                }*/
                for (int i = 0; i < arr.length; i++) {
                        if (arr[i].get(attribute).toString().toLowerCase().contains(text.toLowerCase())) {
                            resp.add(arr[i]);
                        }
                    }
                break;
            }
        }
        return resp;
    }



    // METODO DE BUSQUEDA
    // MEtodo que permite saber desde donde se debe iniciar la busqueda
    private Integer bynaryLineal(HashMap<String, String>[] array, String attribute, String text){
        Integer half = 0;
        if (!(array.length == 0) && !text.isEmpty()) {
            half = array.length / 2;
            int db = 0;
            if (text.trim().toLowerCase().charAt(0) > array[half].get(attribute).toString().trim().toLowerCase().charAt(0))
                db = 1;
             else if (text.trim().toLowerCase().charAt(0) < array[half].get(attribute).toString().trim().toLowerCase().charAt(0) )
                db = -1;
            half = half * db;
        }
        return half;
    }


    public static void main(String[] args) {
        DaoCancion da = new DaoCancion();
        da.getObj().setId(da.listAll().getLength() + 1);
        da.getObj().setNombre("OLIMPO");
        da.getObj().setId_genero(1);
        da.getObj().setDuracion(3);
        da.getObj().setUrl("https://www.youtube.com");
        da.getObj().setTipo(null);
        da.getObj().setId_album(1);

        if (da.save()) {
            System.out.println("Guardado");
        } else {
            System.out.println("Error al guardar");

        }
        System.out.println(da.getListAll().getLength());

    }


}
