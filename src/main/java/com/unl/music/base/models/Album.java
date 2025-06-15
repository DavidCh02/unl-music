package com.unl.music.base.models;

import java.util.Date;

public class Album {
    private Integer id;
    private String nombre;
    private Date fecha;
    private Integer id_banda;

    
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

    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public Integer getId_banda() {
        return id_banda;
    }
    public void setId_banda(Integer id_banda) {
        this.id_banda = id_banda;
    }
}
