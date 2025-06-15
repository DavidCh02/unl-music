package com.unl.music.base.controller.services;

import java.util.Arrays;
import java.util.List;

import com.unl.music.base.controller.dao.dao_models.DaoPersona;
import com.unl.music.base.models.Persona;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;

@BrowserCallable
@AnonymousAllowed

    

public class PersonaService {
    
    private DaoPersona db;
    public PersonaService() {
        db = new DaoPersona();
    }

   public List<Persona> lisAllPersona(){
       return Arrays.asList(db.listAll().toArray());
        
 }
    

}
