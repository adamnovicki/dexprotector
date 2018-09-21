package pl.nowicki.openweatherdexprotector.model;

import java.util.ArrayList;

public class WeatherRsp {
    private String cod;

    public String getCod() { return this.cod; }

    public void setCod(String cod) { this.cod = cod; }

    private double message;

    public double getMessage() { return this.message; }

    public void setMessage(double message) { this.message = message; }

    private int cnt;

    public int getCnt() { return this.cnt; }

    public void setCnt(int cnt) { this.cnt = cnt; }

    private ArrayList<List> list;

    public ArrayList<List> getList() { return this.list; }

    public void setList(ArrayList<List> list) { this.list = list; }

    private City city;

    public City getCity() { return this.city; }

    public void setCity(City city) { this.city = city; }
}
