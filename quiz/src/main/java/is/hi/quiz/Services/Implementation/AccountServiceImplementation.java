package is.hi.quiz.Services.Implementation;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Statistics;
import is.hi.quiz.Persistance.Repository.AccountRepository;
import is.hi.quiz.Persistance.Repository.StatisticsRepository;
import is.hi.quiz.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;
    private StatisticsRepository statisticsRepository;
    private int questionsAnswered;
    private int answeredCorrectly;
    private int gamesWon;
    private int gamesPlayed;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository,StatisticsRepository statisticsRepository){
        this.accountRepository = accountRepository;
        this.statisticsRepository=statisticsRepository;
        //statisticsRepository.deleteAll();
        //accountRepository.deleteAll();
        //accountRepository.save(new Account("admin","1234","email@email.com","Admin Adminsson",true));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
    @Override
    public Account save(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Account account) {
        accountRepository.delete(account);
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account login(Account account) {
        Account doesExist = findByUsername(account.getUsername());
        if(doesExist != null){
            if(doesExist.getPassword().equals(account.getPassword())){
                return doesExist;
            }
        }
        return null;
    }

    public  Account logout(Account account){
        Account doesExist = findByUsername(account.getUsername());
        if(doesExist != null){
            if(doesExist.getPassword().equals(account.getPassword())){
                 doesExist=null;
                 return doesExist;
            }
        }
        return null;
    }

    // Statistics stuff
    @Override
    public Statistics saveStatistics(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }

    @Override
    public Statistics findByAccountID(int id) {
        return statisticsRepository.findByAccountID(id);
    }

    // Statistics stuff
    public int addQuestionsAnswered(int q){
        return questionsAnswered+=q;
    }
    public int getQuestionsAnswered(){
        return questionsAnswered;
    }

    public int addAnsweredCorrectly(int q){return answeredCorrectly+=q;}
    public int getAnsweredCorrectly(){return answeredCorrectly;}

    public int addGamesWon(int q){return gamesWon+=q;}
    public int getGamesWon(){return gamesWon;}

    public int addGamesPlayed(int q){return gamesPlayed+=q; }
    public int getGamesPlayed(){return gamesPlayed;}

    @Override
    public void updateStatistics(int questionsAnswered, int answeredCorrectly, int gamesPlayed, int accountID) {
        statisticsRepository.updateStatistics(questionsAnswered,answeredCorrectly,gamesPlayed,accountID);
    }


}
