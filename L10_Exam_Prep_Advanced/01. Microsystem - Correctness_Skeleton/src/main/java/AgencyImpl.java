import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AgencyImpl implements Agency {

    private Map<String,Invoice> invoiceBySerialNumber;
    public AgencyImpl() {
        this.invoiceBySerialNumber = new HashMap<>();
    }

    @Override
    public void create(Invoice invoice) {
        if (this.contains(invoice.getNumber())) {
            throw new IllegalArgumentException();
        }

        this.invoiceBySerialNumber.put(invoice.getNumber(), invoice);
    }

    @Override
    public boolean contains(String number) {
        return this.invoiceBySerialNumber.containsKey(number);
    }

    @Override
    public int count() {
        return this.invoiceBySerialNumber.size();
    }

    @Override
    public void payInvoice(LocalDate dueDate) {
        List<Invoice> collect = this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getDueDate().isEqual(dueDate))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }

        collect.forEach(i -> i.setSubtotal(0));
    }

    @Override
    public void throwInvoice(String number) {
        if (!this.contains(number)) {
            throw new IllegalArgumentException();
        }
        this.invoiceBySerialNumber.remove(number);
    }

    @Override
    public void throwPayed() {
        List<Invoice> collect = this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getSubtotal() == 0)
                .collect(Collectors.toList());

        collect.forEach(i -> this.invoiceBySerialNumber.remove(i.getNumber()));
    }

    @Override
    public Iterable<Invoice> getAllInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {
        return this.invoiceBySerialNumber.values()
                .stream()
                //fix the inclisive
                .filter(i->
                        (i.getDueDate().isAfter(startDate) || i.getDueDate().isEqual(startDate))
                        &&
                                (i.getDueDate().isBefore(endDate)) || i.getDueDate().isEqual(endDate))
                .sorted((l,r)->{
                    LocalDate lIssueDate = l.getIssueDate();
                    LocalDate rIssueDate = r.getIssueDate();

                    if (lIssueDate.isEqual(rIssueDate)) {
                        return l.getDueDate().compareTo(r.getDueDate());
                    }

                    return lIssueDate.compareTo(rIssueDate);
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Invoice> searchByNumber(String number) {
        List<Invoice> collect = this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getNumber().contains(number))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return collect;
    }

    @Override
    public Iterable<Invoice> throwInvoiceInPeriod(LocalDate startDate, LocalDate endDate) {
        List<Invoice> collect = this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getDueDate().isAfter(startDate) && i.getDueDate().isBefore(endDate))
                .collect(Collectors.toList());

        if (collect.isEmpty()) {
            throw new IllegalArgumentException();
        }
        collect.forEach(i->this.invoiceBySerialNumber.remove(i.getNumber()));
        return collect;
    }

    @Override
    public Iterable<Invoice> getAllFromDepartment(Department department) {
        return this.invoiceBySerialNumber
                .values()
                .stream()
                .filter(i->i.getDepartment() == department)
                .sorted((l,r)->{
                    double lSubtotal = l.getSubtotal();
                    double rSubtotal = r.getSubtotal();

                    if (lSubtotal == rSubtotal) {
                        return l.getIssueDate().compareTo(r.getIssueDate());
                    }

                    return Double.compare(rSubtotal, lSubtotal);
                }).collect(Collectors.toList());
    }

    @Override
    public Iterable<Invoice> getAllByCompany(String companyName) {
        return this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getCompanyName().equals(companyName))
                .sorted(Comparator.comparing(Invoice::getNumber).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public void extendDeadline(LocalDate endDate, int days) {
        this.invoiceBySerialNumber.values()
                .stream()
                .filter(i -> i.getDueDate().isEqual(endDate))
                .forEach(i -> i.setDueDate(i.getDueDate().plusDays(days)));
    }
}
