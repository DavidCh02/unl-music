package com.unl.music.base.models;

import org.checkerframework.checker.units.qual.degrees;

import com.unl.music.base.controller.dao.dao_models.DaoCancion;
import com.unl.music.base.controller.data_struct.list.LinkedList;

public class Cancion {
    private Integer id;
    private String nombre;
    private Integer id_genero;
    private Integer duracion;
    private String url;
    private TipoArchivoEnum tipo;
    private Integer id_album;



    public Integer getId() {
        return id;
    }
   public void setId(Integer id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Integer getId_genero() {
        return id_genero;
    }
    
    public void setId_genero(Integer id_genero) {
        this.id_genero = id_genero;
    }
    public Integer getDuracion() {
        return duracion;
    }
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public TipoArchivoEnum getTipo() {
        return tipo;
    }
    public void setTipo(TipoArchivoEnum tipo) {
        this.tipo = tipo;
    }
    public Integer getId_album() {
        return id_album;
    }

    public void setId_album(Integer id_album) {
        this.id_album = id_album;
    }
    public Cancion getById(DaoCancion daoCancion, Integer id) {
        try {
            LinkedList<Cancion> canciones = daoCancion.listAll();
            if (id >= 0 && id < canciones.getLength()) {
                return canciones.get(id);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

}
