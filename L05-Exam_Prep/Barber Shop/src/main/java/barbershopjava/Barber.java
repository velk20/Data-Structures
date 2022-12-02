package barbershopjava;

public class Barber {

    public String name;
    public int haircutPrice;
    public int stars;

    public Barber(String name, int haircutPrice, int stars) {
        this.name = name;
        this.haircutPrice = haircutPrice;
        this.stars = stars;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHaircutPrice() {
        return haircutPrice;
    }

    public void setHaircutPrice(int haircutPrice) {
        this.haircutPrice = haircutPrice;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }
}
