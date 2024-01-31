import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;
    private ArrayList<Employee> members;
    private ArrayList<Project> projects;
    private double lf;

    public double getLf() {
        return lf;
    }

    public void setLf(double lf) {
        this.lf = lf;
    }

    public ArrayList<Employee> getMembers() {
        return members;
    }

    public ArrayList<Project> getProjects() {
        return projects;
    }

    public String getName() {
        return teamName;
    }

    public Team(String n, Employee hd) {
        lf = 0;
        members = new ArrayList<>();
        this.addMember(hd);
        projects = new ArrayList<>();
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();// Set all object fields (Read lab sheet!)
    }

    public void addMember(Employee employee) {
        members.add(employee);
        employee.addToTeam(this);
        Collections.sort(members);
    }

    public void addProject(Project project) {
        projects.add(project);
        project.assignTeam(this);
        Collections.sort(projects);
    }

    public void removeMember(Employee employee) {
        members.remove(employee);
        employee.addToTeam(null);
    }

    public void removeProject(Project project) {
        projects.remove(project);
        project.assignTeam(null);
    }

    public static void list(ArrayList<Team> list) {
        // Learn: "-" means left-aligned
        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list)
            System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup.toString()); // display
                                                                                                          // t.teamName,
                                                                                                          // etc..
    }

    public void listMembers() {
        // Learn: "-" means left-aligned
        System.out.printf("%-10s%-10s%-13s\n", "Role", "Name", "Current / coming leaves");
        System.out.printf("%-10s%-10s%-13s\n", "Leader", head.getName(), head.printLeaves());
        for (Employee e : members) {
            if (e == head)
                continue;
            System.out.printf("%-10s%-10s%-13s\n", "Member", e.getName(), e.printLeaves()); // display t.teamName, etc..
        }
    }

    @Override
    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }

    @Override
    public String toString() {
        String s = teamName;
        for (int i = 0; i < members.size(); i++) {
            if (i == 0)
                s = s.concat(" (");
            s = s.concat(members.get(i).getName());
            if (i != members.size() - 1)
                s = s.concat(", ");
            else
                s = s.concat(")");
        }
        return s;
    }
}
