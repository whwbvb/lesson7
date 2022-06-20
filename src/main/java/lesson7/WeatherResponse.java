
package lesson7;

public class WeatherResponse {
    private String date;
    private String temperature;
    private String text;
    private String cityName;
    private String cityKey;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public WeatherResponse(String date, String temperature, String text, String cityName, String cityKey) {
        this.date = date;
        this.temperature = temperature;
        this.text = text;
        this.cityName = cityName;
        this.cityKey = cityKey;
    }

    public WeatherResponse() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}


