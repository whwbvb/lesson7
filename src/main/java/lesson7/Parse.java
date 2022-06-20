
package lesson7;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Parse {
    private static final String API_KEY = "RJzbUbmg992eABiGC0hGtBHjulrf8rhy";
    private static final String URL_CITY_LIST = "https://dataservice.accuweather.com/locations/v1/topcities/100?apikey=" + API_KEY + "&language=ru-ru";
    private static final String URL_REQUEST_BEGIN = "https://dataservice.accuweather.com/forecasts/v1/daily/5day/";
    private static final String URL_REQUEST_END = "?apikey=" + API_KEY + "&language=ru&metric=true";
    private static String jsonCityList;

    public Parse() {
    }

    public static void initStringJsonCityList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(new URL(URL_CITY_LIST));
        jsonCityList = jsonNode.toString();
    }

    //Создает список городов
    public static List<City> cityKeysList() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(jsonCityList, new TypeReference<List<City>>() {
        });
        return cities;
    }


    //Находит ключ города по названию
    public static String searchKey(String cityName) throws IOException {
        String key = null;
        ObjectMapper objectMapper = new ObjectMapper();
        List<City> cities = objectMapper.readValue(jsonCityList, new TypeReference<List<City>>() {
        });
        Iterator<City> iterator = cities.iterator();
        int counter = 0;
        while (iterator.hasNext()) {
            if (iterator.next().getName().toLowerCase().equals(cityName.toLowerCase())) {
                key = cities.get(counter).getKey();
                break;
            }
            counter++;
        }
        return key;
    }

    public static List<WeatherResponse> listWeatherResponse(String cityName) throws IOException {
        if (Parse.searchKey(cityName) == null) {
            System.out.println("нет города " + cityName + " в базе\nзавершение работы программы");
            System.exit(0);
        }
        String tempCityKey = Parse.searchKey(cityName);
        ObjectMapper objectMapper = new ObjectMapper();
        URL urlForecast = new URL(URL_REQUEST_BEGIN + tempCityKey + URL_REQUEST_END);
        JsonNode jsonNode = objectMapper.readTree(urlForecast).at("/DailyForecasts");
        List<WeatherResponse> forecasts = new ArrayList<>();
        Iterator<WeatherResponse> iterator = forecasts.iterator();
        for (int i = 0; i < 5; i++) {
            forecasts.add(new WeatherResponse());
            forecasts.get(i).setCityName(cityName.substring(0, 1).toUpperCase() + cityName.substring(1));
            forecasts.get(i).setCityKey(tempCityKey);
            forecasts.get(i).setDate(jsonNode.get(i).get("Date").asText());
            forecasts.get(i).setText(jsonNode.get(i).get("Day").get("IconPhrase").asText());
            forecasts.get(i).setTemperature(jsonNode.get(i).get("Temperature").get("Maximum").get("Value").toString());
        }
        return forecasts;
    }

    public static void voidInfo(List<WeatherResponse> forecast) {
        for (int i = 0; i < 5; i++) {
            System.out.println(forecast.get(i).getDate().substring(0, 10) + " в городе " +
                    forecast.get(i).getCityName() + " ожидается " +
                    forecast.get(i).getText().toLowerCase() + ", температура достигнет " +
                    forecast.get(i).getTemperature() + " °C");
        }
    }

    public static void voidInfo(List<WeatherResponse> forecast, int index) {
        if (index < 5) {
            System.out.println(forecast.get(index).getDate().substring(0, 10) + " в городе " +
                    forecast.get(index).getCityName().substring(0, 1).toUpperCase() +
                    forecast.get(index).getCityName().substring(1) + " ожидается " +
                    forecast.get(index).getText().toLowerCase() + ", температура достигнет " +
                    forecast.get(index).getTemperature() + " °C");
        } else System.out.println("Индекс может быть от 0 до 4");

    }

}
