package dao.custom;

import Entity.SuperEntity;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T extends SuperEntity, ID> {
    public boolean save(T r) throws SQLException, ClassNotFoundException;
    public boolean update(T r) throws SQLException, ClassNotFoundException;
    public boolean delete(ID id) throws SQLException, ClassNotFoundException;
    public T get(ID id) throws SQLException, ClassNotFoundException;
    public ArrayList<T> getAll(String text) throws SQLException, ClassNotFoundException;
}
