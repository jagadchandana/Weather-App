package dao;

import dao.custom.impl.LastWeatherDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;
    private DaoFactory(){}

    public enum DaoType{
        LASTWEATHER
    }
    public static DaoFactory getInstance(){
        return (daoFactory==null?(daoFactory=new DaoFactory()):(daoFactory));
    }
    public <T> T getDao(DaoType type){
        switch (type){
            case LASTWEATHER:
                return (T) new LastWeatherDaoImpl();
            default:
                return null;
        }
    }
}
