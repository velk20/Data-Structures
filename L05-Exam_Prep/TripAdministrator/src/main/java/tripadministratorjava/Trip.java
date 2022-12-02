package tripadministratorjava;

import java.util.Objects;

public class Trip {

    public String id;
    public int peopleLimit;
    public Transportation transportation;
    public int price;

    public Trip(String id, int peopleLimit, Transportation transportation, int price) {
        this.id = id;
        this.peopleLimit = peopleLimit;
        this.transportation = transportation;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPeopleLimit() {
        return peopleLimit;
    }

    public void setPeopleLimit(int peopleLimit) {
        this.peopleLimit = peopleLimit;
    }

    public Transportation getTransportation() {
        return transportation;
    }

    public void setTransportation(Transportation transportation) {
        this.transportation = transportation;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
