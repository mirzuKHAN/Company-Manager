import java.util.ArrayList;
import java.util.Collections; //Provides sorting

public class Company {
	private ArrayList<Employee> allEmployees;
	private ArrayList<Team> allTeams;
	private ArrayList<Project> allProjects;
	private ArrayList<Leave> allLeaves;

	public ArrayList<Leave> getAllLeaves() {
		return allLeaves;
	}

	private static Company instance = new Company() ;

	private Company() {
		allEmployees = new ArrayList<>();
		allTeams = new ArrayList<>();
		allProjects = new ArrayList<>();
		allLeaves = new ArrayList<>();
	}

	public static Company getInstance() {
		return instance;
	}

	public Employee createEmployee( String name, int annualLeaves) // See how it is called in main()
	{
		Employee e = new Employee( name, annualLeaves );
		allEmployees.add( e );
		Collections.sort( allEmployees ); //allEmployees
		return e ;
	}

	public Team createTeam( String tName, String name ) // See how it is called in main()
	{
		Employee e = Employee.searchEmployee( allEmployees, name );
		Team t = new Team( tName, e );
		allTeams.add( t );
		Collections.sort( allTeams ); //allTeams
		return t; //Why return?  Ans: Later you'll find it useful for undoable comments.
	}

	public Project createProject( String name, String date, int dur ) // See how it is called in main()
	{
		Project p = new Project( name, date, dur );
		allProjects.add( p );
		Collections.sort( allProjects ); //allEmployees
		return p ;
	}

	public Leave createLeave( Employee e, Day start, Day end ) // See how it is called in main()
	{
		Leave l = new Leave( e, start, end );
		allLeaves.add( l );
		Collections.sort( allLeaves ); //allEmployees
		return l ;
	}

	public void addEmployee(Employee e) {
		allEmployees.add(e);
		Collections.sort(allEmployees);
	}

	public void addTeam(Team t) {
		allTeams.add(t);
		for (Employee e : t.getMembers()) {
			e.addToTeam(t);
		}

		Collections.sort(allTeams);
	}

	public void addProject(Project p) {
		allProjects.add(p);
		Collections.sort(allProjects);
	}

	public void addLeave(Leave l) {
        allLeaves.add(l);
		l.getEmployee().substractAnnualLeaves(l.getDuration());
        Collections.sort(allLeaves);
    }

	public void removeEmployee (Employee e) {
		allEmployees.remove(e); //remove is a method of ArrayList
	}

	public void removeTeam (Team t) {
		allTeams.remove(t); //remove is a method of ArrayList
		for (Employee e : t.getMembers()) {
			e.addToTeam(null);
		}
	}

	public void removeProject(Project p) {
		if (p.getAssignedTeam() != null)
			p.getAssignedTeam().removeProject(p);
		allProjects.remove(p);
    }

    public void removeLeave(Leave l) {
		l.getEmployee().addAnnualLeaves(l.getDuration());
        allLeaves.remove(l);
    }

	public Team findTeam (String name) throws ExTeamNotFound {
		for (Team t : allTeams) {
            if (t.getName().equals(name)) {
				return t;
			}
        }
		throw new ExTeamNotFound();
	}

	public Employee findEmployee (String name) throws ExEmployeeNotFound {
		for (Employee e : allEmployees) {
            if (e.getName().equals(name)) {
				return e;
			}
        }
		throw new ExEmployeeNotFound();
	}

	public Project findProject (String name) throws ExProjectNotFound {
		for (Project p : allProjects) {
            if (p.getName().equals(name)) {
				return p;
			}
        }
		throw new ExProjectNotFound();
	}

	public void listEmployees() {
        for (Employee e : allEmployees) {
            System.out.println(e.toString());
        }
    } //for each employee, apply.toString() and output.

	public void listTeams() {
		Team.list( allTeams );
	}

	public void listMembers( Team t ) {
		t.listMembers( );
	}

	public void listProjects() {
		Project.list(allProjects);
	}

	public void listLeaves() {
		for (Employee e : allEmployees) {
			int cnt = 0;
			ArrayList<Leave> temp = new ArrayList<>();

			for (int i = 0; i < allLeaves.size(); i++) {
				if (allLeaves.get(i).getEmployee() == e) {
					cnt++;
					temp.add(allLeaves.get(i));
				}
			}

			if (cnt == 0) {
				System.out.print(e.getName() + ": --");
			} else {
				for (int i = 0; i < temp.size(); i++) {
					Leave p = temp.get(i);
					if (i != 0) {
						System.out.print(", " + p.getStart().toString() + " to " + p.getEnd().toString());
					} else {
						System.out.print(p.getEmployee().getName() + ": " + p.getStart().toString() + " to " + p.getEnd().toString());
					}
				}
			}

			System.out.println();
		}
    }

	public void listLeaves(Employee e) {
		int cnt = 0;

        for (int i = 0; i < allLeaves.size(); i++) {
			if (allLeaves.get(i).getEmployee() != e) continue;
			cnt++;
			Leave p = allLeaves.get(i);
			if (cnt > 1) {
				System.out.print(", " + p.getStart().toString() + " to " + p.getEnd().toString());
			} else {
				System.out.print(p.getEmployee().getName() + ": " + p.getStart().toString() + " to " + p.getEnd().toString());
			}
        }

		if (cnt == 0) {
			System.out.print(e.getName() + ": --");
		}
		System.out.println();
    }

	public void suggestProjectTeam(Project p) {
		System.out.printf("During the period of project %s (%s to %s):\n",
		p.getName(), p.getStartDay().toString(), p.getEndDay().toString());

		System.out.printf("  Average manpower (m) and count of existing projects (p) of each team:\n");
		for (Team t : allTeams) {
			double m = t.getMembers().size();
			for (Leave l : allLeaves) {
				if (t.getMembers().contains(l.getEmployee())) {
					if (l.getStart().getNum() <= p.getStartDay().getNum()) {
						if (l.getEnd().getNum() >= p.getEndDay().getNum()) 
							m -= 1;
						else if (l.getEnd().getNum() >= p.getStartDay().getNum()) 
							m -= (l.getEnd().substract(p.getStartDay()) + 1.0) / p.getDuration();
					} else if (l.getStart().getNum() <= p.getEndDay().getNum()) {
						if (l.getEnd().getNum() <= p.getEndDay().getNum()) 
							m -= (l.getDuration() - 0.0) / p.getDuration();
						else 
							m -= (p.getEndDay().substract(l.getStart()) + 1.0) / p.getDuration();
					}
				}
			}

			double pnum = 0;
			for (Project project : t.getProjects()) {
				if (project.getStartDay().getNum() <= p.getStartDay().getNum()) {
					if (project.getEndDay().getNum() >= p.getEndDay().getNum())
						pnum += 1;
					else if (project.getEndDay().getNum() >= p.getStartDay().getNum())
						pnum += (project.getEndDay().substract(p.getStartDay()) + 1.0) / p.getDuration();
				} else if (project.getStartDay().getNum() <= p.getEndDay().getNum()) {
					if (project.getEndDay().getNum() <= p.getEndDay().getNum())
						pnum += project.getDuration() / p.getDuration();
					else
						pnum += (p.getEndDay().substract(project.getStartDay()) + 1.0) / p.getDuration();
				}
			}

			System.out.printf("    %s: m=%.2f workers, p=%.2f projects\n", t.getName(), m, pnum);
			t.setLf((1 + pnum) / m);
		}

		System.out.printf("  Projected loading factor when a team takes this project %s:\n", p.getName());

		Team theOne = null;
		for (Team t : allTeams) {
			if (theOne == null) theOne = t;
			System.out.printf("    %s: (p+1)/m = %.2f\n", t.getName(), t.getLf());
			if (t.getLf() < theOne.getLf()) theOne = t;
		}

		System.out.printf("Conclusion: %s should be assigned to %s for best balancing of loading\n", p.getName(), theOne.getName());

	}
}
