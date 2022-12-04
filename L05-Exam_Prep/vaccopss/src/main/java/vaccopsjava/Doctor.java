package vaccopsjava;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    public String name;
    public int popularity;


    public Doctor(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }
}
