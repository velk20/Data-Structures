package vaccopsjava;

import java.util.ArrayList;
import java.util.List;

public class Doctor {

    public String name;
    public int popularity;

    private List<Patient> patients;

    public Doctor(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
        this.patients = new ArrayList<>();
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

    public boolean containsPatient(Patient patient) {
        return this.patients.contains(patient);
    }
    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }

    public void removePatient(Patient patient) {
        this.patients.remove(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }
}
