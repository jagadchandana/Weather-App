package bo.costom.impl;

import Entity.LastWeather;
import bo.costom.LastWeatherBO;
import dao.DaoFactory;
import dao.custom.LastWeatherDAO;
import dto.LastWeatherDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class LastWeatherBoImpl implements LastWeatherBO {

    LastWeatherDAO dao = DaoFactory.getInstance().getDao(DaoFactory.DaoType.LASTWEATHER);

    @Override
    public boolean saveWeather(LastWeatherDTO dto) throws SQLException, ClassNotFoundException {
        return dao.save( new LastWeather(dto.getCode(),dto.getName(),dto.getTemp(),dto.getDesc(),dto.getWind(),
        dto.getCloud(),dto.getPress(),dto.getHumidity(),dto.getDate(),dto.getTime(),dto.getImg()));
    }

    @Override
    public boolean updateWeather(LastWeatherDTO dto) throws SQLException, ClassNotFoundException {
        return dao.update(
                new LastWeather(dto.getCode(),dto.getName(),dto.getTemp(),dto.getDesc(),dto.getWind(),
                        dto.getCloud(),dto.getPress(),dto.getHumidity(),dto.getDate(),dto.getTime(),dto.getImg()));
    }

    @Override
    public boolean deleteWeather(String id) throws SQLException, ClassNotFoundException {
        return dao.delete(id);
    }

    @Override
    public LastWeatherDTO getWeather(String id) throws SQLException, ClassNotFoundException {
        LastWeather r = dao.get(id);
        if (r!=null){
            return new LastWeatherDTO(
                    r.getCode(),r.getName(),r.getTemp(),r.getDesc(),r.getWind(),
            r.getCloud(),r.getPress(),r.getHumidity(),r.getDate(),r.getTime(),r.getImg());
        }
        return null;
    }

    @Override
    public ArrayList<LastWeatherDTO> getAllWeather(String text) throws SQLException, ClassNotFoundException {
        ArrayList<LastWeather> entityList =dao.getAll(text);
        ArrayList<LastWeatherDTO> dtoList = new ArrayList<>();
        for (LastWeather r: entityList
        ) {
            LastWeatherDTO dto = new LastWeatherDTO(
                    r.getCode(),r.getName(),r.getTemp(),r.getDesc(),r.getWind(),
                    r.getCloud(),r.getPress(),r.getHumidity(),r.getDate(),r.getTime(),r.getImg());
            dtoList.add(dto);
        }
        return dtoList;
    }
}
