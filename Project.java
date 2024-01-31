import java.util.ArrayList;

public class Project implements Comparable<Project> {
    private String projectName;
    private Day startDay;
    private Day endDay;
    private Team assignedTeam;
    private int duration;

    public int getDuration() {
        return duration;
    }

    public Day getStartDay() {
        return startDay;
    }

    public Day getEndDay() {
        return endDay;
    }

    public String getName() {
        return projectName;
    }

    public Team getAssignedTeam() {
        return assignedTeam;
    }

    public void assignTeam(Team t) {
        assignedTeam = t;
    }

    public Project(String n, String d, int dur) {
        projectName = n;
        startDay = new Day(d);
        endDay = startDay.add(dur);
        duration = dur;
    }

    public static void list(ArrayList<Project> list) {
        // Learn: "-" means left-aligned
        System.out.printf("%-9s%-13s%-13s%-13s\n", "Project", "Start Day", "End Day", "Team");
        for (Project p : list) {

            String asteam;
            if (p.assignedTeam == null) {
                asteam = "--";
            } else {
                asteam = p.assignedTeam.toString();
            }
            System.out.printf("%-9s%-13s%-13s%-13s\n", p.projectName, p.startDay.toString(), p.endDay, asteam); // display
                                                                                                                // t.teamName,
                                                                                                                // etc..

        }
    }

    @Override
    public int compareTo(Project another) {
        return this.projectName.compareTo(another.projectName);
    }
}
