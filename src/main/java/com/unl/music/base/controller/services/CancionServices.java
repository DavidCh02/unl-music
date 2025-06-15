package com.unl.music.base.controller.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.unl.music.base.controller.Utiles;
import com.unl.music.base.controller.dao.dao_models.DaoAlbum;
import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.unl.music.base.controller.dao.dao_models.DaoCancion;
import com.unl.music.base.controller.dao.dao_models.DaoGenero;
import com.unl.music.base.controller.data_struct.list.LinkedList;
import com.unl.music.base.models.Album;
import com.unl.music.base.models.Banda;
import com.unl.music.base.models.Cancion;
import com.unl.music.base.models.Genero;
import com.unl.music.base.models.TipoArchivoEnum;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@BrowserCallable
@AnonymousAllowed
public class CancionServices {
    private DaoCancion db;
    public CancionServices(){
        db = new DaoCancion();
    }

    public void create(@NotEmpty String nombre, Integer id_genero, Integer duracion,
    @NotEmpty String url, @NotEmpty String tipo, Integer id_albun) throws Exception {
        if(nombre.trim().length() > 0 && url.trim().length() > 0 &&
        tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_albun > 0) {
            db.getObj().setId(db.listAll().getLength() + 1);
            db.getObj().setNombre(nombre);
            db.getObj().setDuracion(duracion);
            db.getObj().setId_album(id_albun);
            db.getObj().setId_genero(id_genero);
            db.getObj().setTipo(TipoArchivoEnum.valueOf(tipo));
            db.getObj().setUrl(url);
            if(!db.save())
                throw new Exception("No se pudo guardar los datos de la canción");
        }
    }

    public void update(Integer id, @NotEmpty String nombre, Integer id_genero, Integer duracion,
                       @NotEmpty String url, @NotEmpty String tipo, Integer id_album) throws Exception {
        if (nombre.trim().length() > 0 && url.trim().length() > 0 &&
                tipo.trim().length() > 0 && duracion > 0 && id_genero > 0 && id_album > 0) {

            Cancion cancion = db.listAll().get(id);
            cancion.setNombre(nombre);
            cancion.setDuracion(duracion);
            cancion.setId_album(id_album);
            cancion.setId_genero(id_genero);
            cancion.setTipo(TipoArchivoEnum.valueOf(tipo));
            cancion.setUrl(url);

            db.setObj(cancion);
            if (!db.update(id)) {
                throw new Exception("Error al actualizar la canción");
            }
        }
    }

    public List<Cancion> lisAllCancion() {
        return Arrays.asList(db.listAll().toArray());
    }

    public List<HashMap<String, String>> listaAlbumCombo() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        DaoAlbum da = new DaoAlbum();
        LinkedList<Album> albumes = da.listAll();

        if (!albumes.isEmpty()) {
            Album[] arreglo = albumes.toArray();
            for (Album album : arreglo) {
                if (album != null && album.getId() != null && album.getNombre() != null) {
                    HashMap<String, String> aux = new HashMap<>();
                    aux.put("value", album.getId().toString());
                    aux.put("label", album.getNombre());
                    lista.add(aux);
                }
            }
        }
        return lista;
    }

    public List<HashMap<String, String>> listaAlbumGenero() {
        List<HashMap<String, String>> lista = new ArrayList<>();
        DaoGenero da = new DaoGenero();
        LinkedList<Genero> generos = da.listAll();

        if (!generos.isEmpty()) {
            Genero[] arreglo = generos.toArray();
            for (Genero genero : arreglo) {

                if (genero != null && genero.getId() != null && genero.getNombre() != null) {
                    HashMap<String, String> aux = new HashMap<>();
                    aux.put("value", genero.getId().toString()); // Conversión correcta
                    aux.put("label", genero.getNombre());
                    lista.add(aux);
                }
            }
        }
        return lista;
    }


    public List<String> listTipo() {
        List<String> lista = new ArrayList<>();
        for (TipoArchivoEnum r : TipoArchivoEnum.values()) {
            lista.add(r.toString());
        }
        return lista;
    }

    public List<HashMap<String, String>> listCancion() throws Exception {
      return Arrays.asList(db.all().toArray());
    }

    public void delete(Integer id) throws Exception {
        try {
            if (id == null || id < 0) {
                throw new Exception("ID de canción inválido o nulo");
            }
            if (!db.delete(id)) {
                throw new Exception("No se pudo eliminar la canción con ID: " + id);
            }
        } catch (Exception e) {
            throw new Exception("No se pudo eliminar la canción: " + e.getMessage());
        }
    }

    public List<HashMap<String, String>> order(String atributo, Integer type) throws Exception {
        return Arrays.asList(db.orderQ(type, atributo).toArray());
    }

    public List<HashMap<String, String>> buscarPorCriterio(String criterio, String valor) throws Exception {
        if (valor == null || valor.trim().isEmpty()) {
            return listCancion();
        }

        return Arrays.asList(db.search(criterio, valor, 3).toArray());
    }
}
