package com.unl.music.base.controller.dao.dao_models;

import com.unl.music.base.controller.dao.AdapterDao;
import com.unl.music.base.models.Album;

public class DaoAlbum extends AdapterDao<Album> {
    private Album obj;

    public DaoAlbum() {
        super(Album.class);
    }

    public Album getObj() {
        if (obj == null) {
            this.obj = new Album();
        }
        return this.obj;
    }

    public void setObj(Album obj) {
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

    public static void main(String[] args) {
        DaoAlbum da = new DaoAlbum();
        da.getObj().setId(da.listAll().getLength()+1);
        da.getObj().setNombre("Nuevo Album");
        da.getObj().setFecha(new java.util.Date());
        da.getObj().setId_banda(1);
        if (da.save()) {
            System.out.println("GUARDADO");
        } else {
            System.out.println("HUBO UN ERROR");
        }
    }

    public Album findById(Integer id) {
        try {
            Object[] albums = this.listAll().toArray();
            for (Object obj : albums) {
                Album album = (Album) obj;
                if (album.getId().equals(id)) {
                    return album;
                }
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}