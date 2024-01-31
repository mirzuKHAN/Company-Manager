public class Leave implements Comparable<Leave> {

    private Day start, end;
    private Employee employee;
    private int duration;

    public Leave(Employee employee, Day s, Day e) {
        this.employee = employee;
        start = s;
        end = e;
        duration = e.substract(s) + 1;
    }

    public Employee getEmployee() {
        return employee;
    }

    public int getDuration() {
        return duration;
    }

    public Day getStart() {
        return start;
    }

    public Day getEnd() {
        return end;
    }

    @Override
    public int compareTo(Leave another) {
        return start.compareTo(another.getStart());
    }

}
