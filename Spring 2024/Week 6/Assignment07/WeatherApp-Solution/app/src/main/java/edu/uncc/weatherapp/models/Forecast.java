package edu.uncc.weatherapp.models;

import org.json.JSONObject;

public class Forecast {
    String startTime, temperatureUnit, windSpeed, iconUrl, shortForecast;
    Double temperature, relativeHumidity;

    public Forecast() {
    }

    public Forecast(JSONObject jsonObject) {
        this.startTime = jsonObject.optString("startTime");
        this.temperature = jsonObject.optDouble("temperature");
        this.temperatureUnit = jsonObject.optString("temperatureUnit");
        this.relativeHumidity = jsonObject.optJSONObject("relativeHumidity").optDouble("value");
        this.windSpeed = jsonObject.optString("windSpeed");
        this.iconUrl = jsonObject.optString("icon");
        this.shortForecast = jsonObject.optString("shortForecast");

    /*
    {
                "startTime": "2024-03-15T16:00:00-04:00",
                "temperature": 73,
                "temperatureUnit": "F",
                "relativeHumidity": {
                    "unitCode": "wmoUnit:percent",
                    "value": 69
                },
                "windSpeed": "16 mph",
                "icon": "https://api.weather.gov/icons/land/day/rain_showers,30?size=medium",
                "shortForecast": "Chance Rain Showers",
            },
     */
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(String temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(Double relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }


}
