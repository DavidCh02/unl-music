package com.unl.music.base.controller.dao;

import com.unl.music.base.controller.data_struct.list.LinkedList;

public interface InterfaceDao<T> {
    LinkedList<T> listAll();
    void persist(T obj) throws Exception;
    void update(T obj, Integer pos) throws Exception;
    void update_by_id(T obj, Integer id) throws Exception;
    T get(Integer id) throws Exception;
    boolean delete(Integer id) throws Exception;
}
