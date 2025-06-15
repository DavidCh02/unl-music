package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.models.Genero;

public class DaoGenero extends AdapterDao<Genero> {
    private Genero obj;
    private static final String base_path = "c:\\Users\\David\\Desktop\\estructura de datos 2\\unl-music\\data\\";

    public DaoGenero() {
        super(Genero.class);
    }

    public Genero getObj() {
        if (obj == null) {
            this.obj = new Genero();
        }
        return this.obj;
    }

    public void setObj(Genero obj) {
        this.obj = obj;
    }

    public Boolean save() {
        try {
            this.persist(obj);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
            return false;
        }
    }

    public Boolean update(Integer id) {
        try {
            Genero existingGenero = this.findById(id);
            if (existingGenero != null) {
                obj.setId(id);
                this.persist(obj);
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void listarGeneros() {
        System.out.println("\n=== LISTA DE GÉNEROS MUSICALES ===");
        System.out.printf("%-5s %-20s %-50s%n", "ID", "NOMBRE", "DESCRIPCIÓN");
        System.out.println("--------------------------------------------------------------------------------");
        
        com.unl.music.base.controller.data_struct.list.LinkedList<Genero> generos = this.listAll();
        for (int i = 0; i < generos.getLength(); i++) {
            Genero genero = generos.get(i);
            System.out.printf("%-5d %-20s %-50s%n",
                genero.getId(),
                genero.getNombre(),
                genero.getDescripcion()
            );
        }
        System.out.println("--------------------------------------------------------------------------------\n");
    }

    public static void main(String[] args) {
        DaoGenero daoGenero = new DaoGenero();
        
        // Ejemplo de creación
        System.out.println("=== CREANDO NUEVO GÉNERO ===");
        daoGenero.getObj().setId(daoGenero.listAll().getLength() + 1);
        daoGenero.getObj().setNombre("Rock");
        daoGenero.getObj().setDescripcion("Género musical caracterizado por sonidos fuertes de guitarra");

        if (daoGenero.save()) {
            System.out.println("Género creado exitosamente!");
        } else {
            System.out.println("Error al crear el género");
        }

        // Ejemplo de modificación
        System.out.println("\n=== MODIFICANDO GÉNERO ===");
        Genero generoModificar = daoGenero.findById(1);
        if (generoModificar != null) {
            generoModificar.setNombre("Rock Alternativo");
            generoModificar.setDescripcion("Subgénero del rock con elementos experimentales");
            
            if (daoGenero.update(1)) {
                System.out.println("Género modificado exitosamente!");
            } else {
                System.out.println("Error al modificar el género");
            }
        }

        // Listar todos los géneros
        daoGenero.listarGeneros();
    }

    public Genero findById(Integer id) {
        try {
            com.unl.music.base.controller.data_struct.list.LinkedList<Genero> list = this.listAll();
            for (int i = 0; i < list.getLength(); i++) {
                Genero genero = list.get(i);
                if (genero.getId().equals(id)) {
                    return genero;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}