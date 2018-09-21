package pl.nowicki.openweatherdexprotector.model;

public class City {
    private int id;

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    private String name;

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    private Coord coord;

    public Coord getCoord() { return this.coord; }

    public void setCoord(Coord coord) { this.coord = coord; }

    private String country;

    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }

    private int population;

    public int getPopulation() { return this.population; }

    public void setPopulation(int population) { this.population = population; }
}
