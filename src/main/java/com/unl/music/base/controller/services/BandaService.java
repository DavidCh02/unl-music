package com.unl.music.base.controller.services;

import com.unl.music.base.controller.dao.dao_models.DaoBanda;
import com.unl.music.base.models.Banda;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import com.vaadin.hilla.Endpoint;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@BrowserCallable
@AnonymousAllowed
public class BandaService {

    private DaoBanda db;
    public BandaService() {
        db = new DaoBanda();
    }

    public void createBanda(@NotEmpty @NotBlank @NonNull String nombre, @NonNull String fechaCreacion) throws Exception {
        if (nombre.trim().length() > 0 && fechaCreacion.length() > 0) {
            try {
                db.getObj().setNombre(nombre);
                // Parse the date from ISO format (YYYY-MM-DD)
                java.text.SimpleDateFormat isoFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = isoFormat.parse(fechaCreacion);
                
                // Format for storage
                java.text.SimpleDateFormat outputFormat = new java.text.SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a", java.util.Locale.US);
                db.getObj().setFechaCreacion(date);
                
                if (!db.save()) {
                    throw new Exception("Error al guardar la banda");
                }
            } catch (java.text.ParseException e) {
                throw new Exception("Error en el formato de fecha: " + e.getMessage());
            }
        }
    }

    public void updateBanda(Integer id, @NotEmpty @NotBlank @NonNull String nombre, @NonNull String fechaCreacion) throws Exception {
        if (id != null && id > 0 && nombre.trim().length() > 0 && fechaCreacion.length() > 0) {
            try {
                db.setObj(db.listAll().get(id - 1));
                db.getObj().setNombre(nombre);
                
                // Parse the date from ISO format (YYYY-MM-DD)
                java.text.SimpleDateFormat isoFormat = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date date = isoFormat.parse(fechaCreacion);
                db.getObj().setFechaCreacion(date);
                
                if (!db.update(id - 1)) {
                    throw new Exception("Error no se pudo modificar la banda");
                }
            } catch (java.text.ParseException e) {
                throw new Exception("Error en el formato de fecha: " + e.getMessage());
            }
        }
    }


    public List<Banda> lisAllBanda(){
        return Arrays.asList(db.listAll().toArray());
        
    }
    
}
