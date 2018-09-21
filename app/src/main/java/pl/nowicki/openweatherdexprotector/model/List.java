package pl.nowicki.openweatherdexprotector.model;

import java.util.ArrayList;

public class List {private int dt;

    public int getDt() { return this.dt; }

    public void setDt(int dt) { this.dt = dt; }

    private Main main;

    public Main getMain() { return this.main; }

    public void setMain(Main main) { this.main = main; }

    private ArrayList<Weather> weather;

    public ArrayList<Weather> getWeather() { return this.weather; }

    public void setWeather(ArrayList<Weather> weather) { this.weather = weather; }

    private Clouds clouds;

    public Clouds getClouds() { return this.clouds; }

    public void setClouds(Clouds clouds) { this.clouds = clouds; }

    private Wind wind;

    public Wind getWind() { return this.wind; }

    public void setWind(Wind wind) { this.wind = wind; }

    private Sys sys;

    public Sys getSys() { return this.sys; }

    public void setSys(Sys sys) { this.sys = sys; }

    private String dt_txt;

    public String getDtTxt() { return this.dt_txt; }

    public void setDtTxt(String dt_txt) { this.dt_txt = dt_txt; }

    private Rain rain;

    public Rain getRain() { return this.rain; }

    public void setRain(Rain rain) { this.rain = rain; }
}
