package dto;

public class LastWeatherDTO {
    private int code;
    private String name;
    private String temp;
    private String desc;
    private String wind;
    private String cloud;
    private String press;
    private String humidity;
    private String date;
    private String time;
    private String img;


    public LastWeatherDTO() {
    }

    public LastWeatherDTO(int code, String name, String temp, String desc, String wind, String cloud, String press, String humidity, String date, String time,String img) {
        this.code = code;
        this.name = name;
        this.temp = temp;
        this.desc = desc;
        this.wind = wind;
        this.cloud = cloud;
        this.press = press;
        this.humidity = humidity;
        this.date = date;
        this.time = time;
        this.img = img;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
