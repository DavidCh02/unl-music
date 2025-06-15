package com.unl.music.base.controller.dao.dao_models;

public class DaoArtista_Cancion {
    private Integer id;
    private Integer id_artista;
    private Integer id_cancion;
    private String nombreArtista;
    private String nombreCancion;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId_artista() {
        return id_artista;
    }
    public void setId_artista(Integer id_artista) {
        this.id_artista = id_artista;
    }
    public Integer getId_cancion() {
        return id_cancion;
    }
    public void setId_cancion(Integer id_cancion) {
        this.id_cancion = id_cancion;
    }
    public String getNombreArtista() {
        return nombreArtista;
    }
    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }
    public String getNombreCancion() {
        return nombreCancion;
    }
    public void setNombreCancion(String nombreCancion) {
        this.nombreCancion = nombreCancion;
    }
}