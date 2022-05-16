package dao.custom.impl;

import Entity.LastWeather;
import Entity.Weather;
import dao.CrudUtil;
import dao.custom.LastWeatherDAO;
import db.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LastWeatherDaoImpl implements LastWeatherDAO {
    @Override
    public boolean save(LastWeather r) throws SQLException,ClassNotFoundException{
       /*Session session = HibernateUtil.openSession();
       Transaction transaction = session.beginTransaction();
       session.save(r);
       transaction.commit();
        return true;*/
        return CrudUtil.execute("INSERT INTO lastweather VALUES(?,?,?,?,?,?,?,?,?,?,?)",
               r.getCode(),r.getName(),r.getTemp(),r.getDesc(),r.getWind(),r.getCloud(),
                r.getPress(),r.getHumidity(),r.getDate(),r.getTime(),r.getImg());
    }

    @Override
    public boolean update(LastWeather r) throws SQLException, ClassNotFoundException {
       /* Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(r);
        transaction.commit();
        return true;*/
        return CrudUtil.execute("INSERT INTO lastweather VALUES(?,?,?,?,?,?,?,?,?,?,?)",
                r.getName(),r.getTemp(),r.getDesc(),r.getWind(),r.getCloud(),
                r.getPress(),r.getHumidity(),r.getDate(),r.getTime(),r.getImg(),r.getCode());
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        /*Session session = HibernateUtil.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE lastweather WHERE code=:id");
        query.setParameter("id",s);
        query.executeUpdate();
        transaction.commit();
        return true;*/
        return CrudUtil.execute("DELETE FROM lastweather WHERE code=?",s);
    }

    @Override
    public LastWeather get(String s) throws SQLException, ClassNotFoundException {
       /* return HibernateUtil.openSession().get(LastWeather.class, s);*/
        ResultSet rst;
        if (s.matches("\\d+")){
             rst = CrudUtil.execute("SELECT * FROM lastweather WHERE code=?", s);
        }else{
             rst = CrudUtil.execute("SELECT * FROM lastweather WHERE name=?",s);
        }
        if (rst.next()) {
            return new LastWeather(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)

            );
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<LastWeather> getAll(String text) throws SQLException, ClassNotFoundException {
       /* Query query = HibernateUtil.openSession().createQuery("FROM lastweather WHERE (code like :id OR :id like '' OR name like :id)");
        query.setParameter("id","%"+text+"%");
        List<LastWeather> list = query.list();
        ArrayList<LastWeather> entityList = new ArrayList<>();
        for (LastWeather r: list
        ) {
            LastWeather lastWeather = new LastWeather(
                    r.getCode(),
                    r.getName(),
                    r.getTemp(),
                    r.getDesc(),
                    r.getWind(),
                    r.getCloud(),
                    r.getPress(),
                    r.getHumidity(),
                    r.getDate(),
                    r.getTime()
            );
            entityList.add(lastWeather);
        }
        return entityList;*/
  ResultSet rst = CrudUtil.execute("SELECT * FROM lastweather WHERE code LIKE ? OR name LIKE ? ",text,text);
        ArrayList<LastWeather> entityList = new ArrayList<>();
        while (rst.next()){
            LastWeather dto = new LastWeather(
                    rst.getInt(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getString(4),
                    rst.getString(5),
                    rst.getString(6),
                    rst.getString(7),
                    rst.getString(8),
                    rst.getString(9),
                    rst.getString(10),
                    rst.getString(11)
            );
            entityList.add(dto);
        }
        return entityList;
    }
}
