package is.hi.quiz.Persistance.Entities;
import javax.persistence.*;
@Entity
@Table(name="scores")
public class Scores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
   @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    private int score;


    public Scores() {
    }

    public Scores(Account account, int score) {
        this.account = account;
        this.score = score;
    }
/*
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }*/

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

}

