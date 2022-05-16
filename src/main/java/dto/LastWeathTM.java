package dto;

public class LastWeathTM {
    private String code;
    private String name;
    private String temp;
    private String desc;
    private String windSpeed;
    private String cloud;
    private String press;
    private String humid;
    private String date;
    private String time;

    public LastWeathTM() {
    }

    public LastWeathTM(String code, String name, String temp, String desc, String windSpeed, String cloud, String press, String humid, String date, String time) {
        this.code = code;
        this.name = name;
        this.temp = temp;
        this.desc = desc;
        this.windSpeed = windSpeed;
        this.cloud = cloud;
        this.press = press;
        this.humid = humid;
        this.date = date;
        this.time = time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
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

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
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

    public String getHumid() {
        return humid;
    }

    public void setHumid(String humid) {
        this.humid = humid;
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
}
