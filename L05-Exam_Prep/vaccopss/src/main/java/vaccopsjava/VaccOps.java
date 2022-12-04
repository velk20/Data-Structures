package vaccopsjava;

import javax.print.Doc;
import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    private Map<String, Doctor> doctorsWithNames;
    private Map<String, Patient> patientsWithNames;
    private Map<String, List<Patient>> doctorsPatients;
    public VaccOps() {
        this.doctorsWithNames = new HashMap<>();
        this.patientsWithNames = new HashMap<>();
        this.doctorsPatients = new HashMap<>();
    }

    public void addDoctor(Doctor d) {
        if (this.doctorsWithNames.containsKey(d.name)) {
            throw new IllegalArgumentException();
        }
        this.doctorsWithNames.put(d.name, d);
        this.doctorsPatients.put(d.name, new ArrayList<>());
    }

    public void addPatient(Doctor d, Patient p) {
        if (!this.doctorsWithNames.containsKey(d.name)) {
            throw new IllegalArgumentException();
        }

        p.doctor = d;
        this.patientsWithNames.put(p.name, p);
        this.doctorsPatients.get(d.name).add(p);
    }

    public Collection<Doctor> getDoctors() {
        return this.doctorsWithNames.values();
    }

    public Collection<Patient> getPatients() {
        return this.patientsWithNames.values();
    }

    public boolean exist(Doctor d) {
        return this.doctorsWithNames.containsKey(d.name);
    }

    public boolean exist(Patient p) {
        return this.patientsWithNames.containsKey(p.name);
    }


    public Doctor removeDoctor(String name) {
        if (!this.doctorsWithNames.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        Doctor removedDoctor = this.doctorsWithNames.remove(name);
        this.doctorsPatients.get(name).clear();
        this.doctorsPatients.remove(name);
        return removedDoctor;
    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!exist(from) || !exist(to) || !exist(p)) {
            throw new IllegalArgumentException();
        }

        Doctor doctorFrom = this.doctorsWithNames.get(from.name);
        Doctor newDoctor = this.doctorsWithNames.get(to.name);
        Patient patient = this.patientsWithNames.get(p.name);
        if (patient.doctor != doctorFrom) {
            throw new IllegalArgumentException();
        }

        patient.doctor = newDoctor;

        this.doctorsPatients.get(doctorFrom.name).remove(patient);
        this.doctorsPatients.get(newDoctor.name).add(patient);
    }

    public Collection<Doctor> getDoctorsByPopularity(int populariry) {
        return this.doctorsWithNames.values()
                .stream()
                .filter(d -> d.popularity == populariry)
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsByTown(String town) {
        return this.patientsWithNames
                .values()
                .stream()
                .filter(p -> p.town.equals(town))
                .collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsInAgeRange(int lo, int hi) {
        return this.patientsWithNames.values()
                .stream()
                .filter(p -> p.age >= lo && p.age <= hi)
                .collect(Collectors.toList());
    }

    public Collection<Doctor> getDoctorsSortedByPatientsCountDescAndNameAsc() {
        Collection<Doctor> doctors = this.doctorsWithNames.values();
        return doctors.stream()
                .sorted((d1, d2) -> {
                    int d1Size = this.doctorsPatients.get(d1.name).size();
                    int d2Size = this.doctorsPatients.get(d2.name).size();
                    if (d1Size == d2Size) {
                        return d1.name.compareTo(d2.name);
                    }

                    return Integer.compare(d2Size, d1Size);
                }).collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return this.patientsWithNames.values()
                .stream()
                .sorted((p1, p2) -> {
                    int p1Popularity = p1.getDoctor().getPopularity();
                    int p2Popularity = p2.getDoctor().getPopularity();
                    if (p1Popularity == p2Popularity) {
                        int p1Height = p1.getHeight();
                        int p2Height = p2.getHeight();
                        if (p1Height == p2Height) {
                            return Integer.compare(p1.getAge(), p2.getAge());
                        }

                        return Integer.compare(p2Height, p1Height);
                    }
                    return Integer.compare(p1Popularity, p2Popularity);
                }).collect(Collectors.toList());
    }
}
