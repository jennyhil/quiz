package is.hi.quiz.Services;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Statistics;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    Account save(Account account);
    void delete(Account account);
    Account findByUsername(String username);
    Account login(Account account);
    Account logout(Account account);

    // Statistics stuff
    Statistics saveStatistics(Statistics statistics);
    Statistics findByAccountID(int accountID);
    int addQuestionsAnswered(int q);
    int getQuestionsAnswered(int i);
    int addAnsweredCorrectly(int q);
    int getAnsweredCorrectly(int i);
    int addGamesPlayed(int q);
    int getGamesPlayed(int i);
    void resetScore();
    int resetQA();
    int resetAC();
    int resetGP();

    void updateStatistics(int questionsAnswered, int answeredCorrectly, int gamesPlayed, int id);
}
