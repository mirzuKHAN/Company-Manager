import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

   public static void main(String[] args) throws FileNotFoundException {

      Scanner in = new Scanner(System.in);

      System.out.print("Please input the file pathname: ");
      String filepathname = in.nextLine();

      Scanner inFile = null;
      try {
         inFile = new Scanner(new File(filepathname));

         String cmdLine1 = inFile.nextLine();
         String[] cmdLine1Parts = cmdLine1.split("\\|");
         System.out.println("\n> " + cmdLine1);
         SystemDate.createTheInstance(cmdLine1Parts[1]);

         while (inFile.hasNext()) {
            try {
               String cmdLine = inFile.nextLine().trim();

               if (cmdLine.equals(""))
                  continue;

               System.out.println("\n> " + cmdLine);

               String[] cmdParts = cmdLine.split("\\|");

               if (cmdParts[0].equals("hire"))
                  (new CmdHire()).execute(cmdParts);
               else if (cmdParts[0].equals("listEmployees"))
                  (new CmdListEmployees()).execute(cmdParts);
               else if (cmdParts[0].equals("startNewDay"))
                  (new CmdStartNewDay()).execute(cmdParts);
               else if (cmdParts[0].equals("setupTeam"))
                  (new CmdSetupTeam()).execute(cmdParts);
               else if (cmdParts[0].equals("listTeams"))
                  (new CmdListTeams()).execute(cmdParts);
               else if (cmdParts[0].equals("joinTeam"))
                  (new CmdJoinTeam()).execute(cmdParts);
               else if (cmdParts[0].equals("listTeamMembers"))
                  (new CmdListTeamMembers()).execute(cmdParts);
               else if (cmdParts[0].equals("createProject"))
                  (new CmdCreateProject()).execute(cmdParts);
               else if (cmdParts[0].equals("listProjects"))
                  (new CmdListProjects()).execute(cmdParts);
               else if (cmdParts[0].equals("assign"))
                  (new CmdAssign()).execute(cmdParts);
               else if (cmdParts[0].equals("takeLeave"))
                  (new CmdTakeLeave()).execute(cmdParts);
               else if (cmdParts[0].equals("listLeaves"))
                  (new CmdListLeaves()).execute(cmdParts);
               else if (cmdParts[0].equals("suggestProjectTeam"))
                  (new CmdSuggestProjectTeam()).execute(cmdParts);
               else if (cmdParts[0].equals("undo"))
                  RecordedCommand.undoOneCommand();
               else if (cmdParts[0].equals("redo"))
                  RecordedCommand.redoOneCommand();
               else
                  throw new ExWrongCommand();

            } catch (ExWrongCommand e) {
               System.out.println("Unknown command - ignored.");
            } catch (NumberFormatException e) {
               System.out.println("Wrong number format.");
            } catch (ExProjectNotFound e) {
               System.out.println(e.getMessage());
            } catch (ExTeamNotFound e) {
               System.out.println(e.getMessage());
            } catch (ExEmployeeNotFound e) {
               System.out.println(e.getMessage());
            } catch (ArrayIndexOutOfBoundsException e) {
               System.out.println("Insufficient command arguments!");
            }
         }
      } catch (FileNotFoundException e) {
         System.out.println("File not found.");
      } catch (InputMismatchException e) {
         System.out.println("File content problem. Program terminated.");
      } finally {
         if (inFile != null)
            inFile.close();
         in.close();
      }
   }
}