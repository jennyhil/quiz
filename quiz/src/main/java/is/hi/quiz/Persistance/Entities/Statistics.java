package is.hi.quiz.Persistance.Entities;

import javax.persistence.*;

@Entity
@Table(name="statistics")
public class Statistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @OneToOne(fetch = FetchType.LAZY)
    private Account account;
    private int accountID;
    private int questionsAnswered;
    private int answeredCorrectly;
    private int gamesPlayed;

    public Statistics() {
    }

    public Statistics(Account account, int accountID,int questionsAnswered, int answeredCorrectly, int gamesPlayed) {
        this.account = account;
        this.accountID=accountID;
        this.questionsAnswered = questionsAnswered;
        this.answeredCorrectly = answeredCorrectly;
        this.gamesPlayed = gamesPlayed;
    }


    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public long getAccountID(){
        return accountID;
    }

    public int getQuestionsAnswered() {
        return questionsAnswered;
    }

    public void setQuestionsAnswered(int questionsAnswered) {
        this.questionsAnswered = questionsAnswered;
    }

    public int getAnsweredCorrectly() {
        return answeredCorrectly;
    }

    public void setAnsweredCorrectly(int answeredCorrectly) {
        this.answeredCorrectly = answeredCorrectly;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }
}
