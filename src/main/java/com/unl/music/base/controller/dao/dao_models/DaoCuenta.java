package com.unl.music.base.controller.dao.dao_models;

public class DaoCuenta {
    private Integer id;
    private String correo;
    private String clave;
    private boolean estado;
    private Integer id_persona;
    private String nombrePersona;
    private String apellidoPersona;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public String getClave() {
        return clave;
    }
    public void setClave(String clave) {
        this.clave = clave;
    }
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    public Integer getId_persona() {
        return id_persona;
    }
    public void setId_persona(Integer id_persona) {
        this.id_persona = id_persona;
    }
    public String getNombrePersona() {
        return nombrePersona;
    }
    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }
    public String getApellidoPersona() {
        return apellidoPersona;
    }
    public void setApellidoPersona(String apellidoPersona) {
        this.apellidoPersona = apellidoPersona;
    }
}