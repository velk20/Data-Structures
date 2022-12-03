package vaccopsjava;

import javax.print.Doc;
import java.util.*;
import java.util.stream.Collectors;

public class VaccOps implements IVaccOps {
    private Map<String, Doctor> doctorsWithNames;
    private Map<String, Patient> patientsWithNames;
    private Map<String, Doctor> patientNameWithDoctor;

    public VaccOps() {
        this.doctorsWithNames = new HashMap<>();
        this.patientsWithNames = new HashMap<>();
        this.patientNameWithDoctor = new HashMap<>();
    }

    public void addDoctor(Doctor d) {
        if (this.doctorsWithNames.containsKey(d.name)) {
            throw new IllegalArgumentException();
        }
        this.doctorsWithNames.put(d.name, d);
    }

    public void addPatient(Doctor d, Patient p) {
        if (!this.doctorsWithNames.containsKey(d.name)) {
            throw new IllegalArgumentException();
        }

        Doctor doctor = this.doctorsWithNames.get(d.name);
        doctor.addPatient(p);
        this.patientNameWithDoctor.put(p.name, doctor);

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

        this.doctorsWithNames.get(name).getPatients().clear();

        Doctor removedDoctor = this.doctorsWithNames.remove(name);

        for (Map.Entry<String, Doctor> stringDoctorEntry : patientNameWithDoctor.entrySet()) {
            if (stringDoctorEntry.getValue().name.equals(removedDoctor.name)) {
                this.patientNameWithDoctor.remove(stringDoctorEntry.getKey());
                break;
            }
        }

        return removedDoctor;

    }

    public void changeDoctor(Doctor from, Doctor to, Patient p) {
        if (!exist(from) || !exist(to) || !exist(p)) {
            throw new IllegalArgumentException();
        }

        if (!this.doctorsWithNames.get(from.name).containsPatient(p)) {
            throw new IllegalArgumentException();
        }

        this.doctorsWithNames.get(from.name).removePatient(p);
        this.doctorsWithNames.get(to.name).addPatient(p);

        this.patientNameWithDoctor.put(p.name, to);
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
                    int d1Size = d1.getPatients().size();
                    int d2Size = d2.getPatients().size();
                    if (d1Size == d2Size) {
                        return d1.name.compareTo(d2.name);
                    }

                    return Integer.compare(d2Size, d1Size);
                }).collect(Collectors.toList());
    }

    public Collection<Patient> getPatientsSortedByDoctorsPopularityAscThenByHeightDescThenByAge() {
        return null;

    }
}
