package bo.costom;

import bo.SuperBO;
import dto.LastWeatherDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface LastWeatherBO extends SuperBO {
    public boolean saveWeather(LastWeatherDTO r) throws SQLException, ClassNotFoundException;
    public boolean updateWeather(LastWeatherDTO r) throws SQLException, ClassNotFoundException;
    public boolean deleteWeather(String id) throws SQLException, ClassNotFoundException;
    public LastWeatherDTO getWeather(String id) throws SQLException, ClassNotFoundException;
    public ArrayList<LastWeatherDTO> getAllWeather(String text) throws SQLException, ClassNotFoundException;
}
