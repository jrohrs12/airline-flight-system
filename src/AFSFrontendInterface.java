import java.util.List;

public interface AFSFrontendInterface {
    //public AFSFrontend
    
    public void runCommandLoop(); //runs the command loop for finding flight paths
    public char mainMenuPrompt(); //displays the main menu options for the flight app
    public void loadDataCommand(); //loads all of the data
    public void searchPaths(String constraint); // driver for finding the paths for flights. This will include calls to constraint select
    public void setDelay(); //sets delay for an edge between two airports
    
    public String constraintSelect(); //selects constraint for the flights
    public void displayStats(); // displays various stats for the data loaded in the app.

    
    }
    