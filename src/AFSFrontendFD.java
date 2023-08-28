import java.io.FileNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AFSFrontendFD implements AFSFrontendInterface{
  Scanner userInput; //user input
  //TODO switch this to necessary backend if given
  AFSBackendInterface backend; //the necessary backend needed for frontend

  public AFSFrontendFD(Scanner userInput, AFSBackendInterface backend) {
    this.userInput = userInput;
    this.backend = backend;
  }
  @Override
  /**
   * takes responsibility of the "control panel" of the application. includes:
   * loading data from files
   * adding constraints to the flight path (and delays)
   * displaying statistics about the flight data
   */
  public void runCommandLoop() {
    System.out.println("Welcome to the Flight Navigator App!");
    
    String words;
    char command = '\0';
    while (command != 'Q') { // main loop continues until user chooses to quit
        command = this.mainMenuPrompt();
        switch(command) {
          case 'L': // System.out.println(" [L]oad data from file");
            loadDataCommand();
            break;
        case 'P': // System.out.println("  Search available flight [P]aths");
          
            String constraint = constraintSelect();
            searchPaths(constraint);
            break;
       
        case 'S': // System.out.println(" Display [S]tatistics for dataset");
            displayStats();
            break;
        case 'Q': // System.out.println(" [Q]uit");
            // do nothing, containing loop condition will fail
            break;
        case 'D':
          setDelay();
          break;
        default:
            System.out.println(
                    "Didn't recognize that command.  Please type one of the letters presented within []s to identify the command you would like to choose.");
            break;
        }
    }
    System.out.println("thank you for using the Flight Navigator app. goodbye!");
  }
  

  @Override
  /**
   * displays the main menu for the user, giving them several options including:
   * loading data
   * selecting constraints for flights (avoiding certain weather, delays)
   * searching for the shortest flight path
   * displaying statistics from the shortest path
   */
  public char mainMenuPrompt() {
    // display menu of choices
    System.out.println("Choose a command from the list below:");
    System.out.println("    [L]oad data from file");
    System.out.println("    Search available flight [P]aths");
    System.out.println("    Set [D]elays between two airports that are connected");
    System.out.println("    Display [S]tatistics for dataset");
    
    System.out.println("    [Q]uit");

    // read in user's choice, and trim away any leading or trailing whitespace
    System.out.print("Choose command: ");
    String input = userInput.nextLine();
    if (input.length() == 0) // if user's choice is blank, return null character
        return '\0';
    // otherwise, return an uppercase version of the first character in input
    return Character.toUpperCase(input.charAt(0));
}

  @Override
  public void loadDataCommand() {
    System.out.print("Enter the name of the file to load: ");
    String filename = userInput.nextLine().trim();
    try {
        backend.loadData(filename);
        System.out.println("Successfully loaded data from file!");
    } catch (FileNotFoundException e) {
        System.out.println("Error: Could not find or load file: " + filename);
    }
    
  }

  @Override
  /**
   * this will be responsible for retriving the different search paths from the backend
   * It will call 3 different methods based on the constraint selected for path searching
   * this method will prompt the user to give a start and end node for their path
   */
  public void searchPaths(String constraint) {
    try {
    List<String> path;
       
    if (constraint.equals("1")) {
      System.out.println("you've chosen to add delay to certain weather conditions");
      System.out.println("please specify weather condition you'd like to avoid. (stormy, windy, rainy, snowy, blizzard) :");
      String weather = userInput.next();
      System.out.print("please select start airport:");
      String start = userInput.next();
      System.out.print("please select end airport: ");
      String end = userInput.next();

      userInput.nextLine();

       path =  backend.findPathsConstraintWeather(start, end, weather);
       if (path == null||path.size() <= 0) {
         System.out.println("there is no flight path with the given information");
         return;
       }
       System.out.println("Start:");
       for (String airport : path) {
         System.out.println(airport);
       }
       System.out.println("end");
    }
    if (constraint.equals("0")) {
      System.out.println("no constraint selected");
      System.out.print("Please specify a start airport: ");
      String start = userInput.next();


      
      System.out.print("Please select an end airport: ");
      String end = userInput.next();

      userInput.nextLine();

      path =  backend.findPathsConstraintNone(start, end);
      if (path == null || path.size() <= 0) {
        System.out.println("there is no flight path with the given information");
        return;
        
      } 
      System.out.println("Start:");
      for (String airport : path) {
        System.out.println(airport);
      }
      System.out.println("end");
    }
    } catch (NoSuchElementException e) {
      System.out.println("there was not a path found with the given constraints");
    }
    
    
    
    
  }

  @Override
  public String constraintSelect() {
    System.out.println("Would you like to set any constraints for your flight?");
    System.out.println("your options are");
    System.out.println("[0] no constraints for flight");
    System.out.println("[1] add delay for specific weather conditions");
    
    String selection = userInput.next();
    switch(selection){
      case"0":
        return "0";
      case"1":
        return "1";
      default:
        System.out.println(
                "Didn't recognize that command. continuing with no constraints");
        return "0";
        
    }
    
    
  }

  
  @Override
  public void displayStats() {
   System.out.println(backend.getAirportStatistics());
  }


  
  @Override
  public void setDelay() {
   System.out.println("please select the airports you would like to select to add a delay too");
   System.out.println("Start:");
   String start = userInput.nextLine();
   System.out.println("End:");
   String end = userInput.nextLine();
   System.out.println("please give a decimal value for the delay");
   String delay = userInput.nextLine();
   backend.addDelay(start, end, delay);
   
    
  }

  public static void main(String[] args){
    Scanner userInput = new Scanner(System.in);
    AirportGraphAE<String, Double> airportGraph = new AirportGraphAE<String, Double>();
    AirportProcessorDW airportReader = new AirportProcessorDW();
    AFSBackendBD backend = new AFSBackendBD(airportGraph, airportReader);
    AFSFrontendFD app = new AFSFrontendFD(userInput, backend);
    app.runCommandLoop();

  }

}
