package is.hi.quiz.Persistance.Entities;

import java.util.List;
import java.util.Timer;
// Todo implement with category entity and questions
public class Quiz {
    long ID;
    Category category;
    //Timer timer;
    int noOfPlayers;
    String loggedInUser;

    public Quiz(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    // List category has a list of questions.
    public Quiz(Category category,  int noOfPlayers) {
        this.category = category;
      //  this.timer = timer;
        this.noOfPlayers = noOfPlayers;
    }

    /*public String getLoggedInUser() {
        return loggedInUser;
    }*/

    public void setLoggedInUser(String loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*
        public Timer getTimer() {
            return timer;
        }

        public void setTimer(Timer timer) {
            this.timer = timer;
        }
    */
    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }
}
