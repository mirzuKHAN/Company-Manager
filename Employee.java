import java.util.ArrayList;

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private int annualLeavesCopy;
    private Team team;
    private Company company;

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;
        annualLeavesCopy = yle;
        team = null;
        company = Company.getInstance();
    }

    public String getName() {
        return name;
    }

    public int getAnnualLeaves() {
        return annualLeaves;
    }

    public int getAnnualLeaveCopy() {
        return annualLeavesCopy;
    }

    public void substractAnnualLeaves(int duration) {
        annualLeavesCopy -= duration;
    }

    public void addAnnualLeaves(int duration) {
        annualLeavesCopy += duration;
    }

    public Team getTeam() {
        return team;
    }

    public void addToTeam(Team t) {
        team = t;
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (e.name.equals(nameToSearch))
                return e;
        }
        return list.get(0);
    }

    public String printLeaves() {
        int cnt = 0;
        String output = "";
        for (Leave l : company.getAllLeaves()) {
            if (l.getEmployee() == this) {
                cnt++;
                if (cnt > 1) {
                    output = output.concat(", " + l.getStart().toString() + " to " + l.getEnd().toString());
                } else {
                    output = output.concat(l.getStart().toString() + " to " + l.getEnd().toString());
                }
            }
        }
        if (cnt == 0) {
            output = output.concat("--");
        }
        return output;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public String toString() {
        String ans = name + " (Entitled Annual Leaves: " + annualLeaves + " days)";
        return ans;
    } // Return a string like: " Kit ($30000, 21 days)" [given_code.txt];

}
