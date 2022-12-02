package tripadministratorjava;

import java.util.LinkedList;
import java.util.List;

public class Company {

    public String name;
    public int tripOrganizationLimit;


    public Company(String name, int tripOrganizationLimit) {
        this.name = name;
        this.tripOrganizationLimit = tripOrganizationLimit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTripOrganizationLimit() {
        return tripOrganizationLimit;
    }

    public void setTripOrganizationLimit(int tripOrganizationLimit) {
        this.tripOrganizationLimit = tripOrganizationLimit;
    }
}
