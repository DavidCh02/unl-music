package com.unl.music.base.models;

public class Cuenta {
        private Integer id;
        private String correo;
        private String clave;
        public boolean estado;
        private Integer id_persona;

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
        
    }
