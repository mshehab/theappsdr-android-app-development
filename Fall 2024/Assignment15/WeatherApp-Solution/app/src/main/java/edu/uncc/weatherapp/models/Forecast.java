package edu.uncc.weatherapp.models;

import org.json.JSONObject;

public class Forecast {
    String startTime, windSpeed, icon, shortForecast;
    double temperature, probOfPrecipitation;

    public Forecast() {
    }

    public Forecast(JSONObject jsonObject){
        try {
            this.startTime = jsonObject.getString("startTime");
            this.temperature = jsonObject.getDouble("temperature");
            this.probOfPrecipitation = jsonObject.getJSONObject("probabilityOfPrecipitation").optDouble("value", 0.0);
            this.windSpeed = jsonObject.getString("windSpeed");
            this.icon = jsonObject.getString("icon");
            this.shortForecast = jsonObject.getString("shortForecast");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getShortForecast() {
        return shortForecast;
    }

    public void setShortForecast(String shortForecast) {
        this.shortForecast = shortForecast;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getProbOfPrecipitation() {
        return probOfPrecipitation;
    }

    public void setProbOfPrecipitation(double probOfPrecipitation) {
        this.probOfPrecipitation = probOfPrecipitation;
    }
}
