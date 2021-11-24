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
    private int questionsAnswered=0;
    private int answeredCorrectly=0;
    private int gamesPlayed=0;

    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository,StatisticsRepository statisticsRepository){
        this.accountRepository = accountRepository;
        this.statisticsRepository=statisticsRepository;
        statisticsRepository.deleteAll();
        accountRepository.deleteAll();
        accountRepository.save(new Account("admin","1234","email@email.com","Admin Adminsson",true));
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
    public int getQuestionsAnswered(int id){
        if(statisticsRepository.findByAccountID(id)==null) return questionsAnswered;
        questionsAnswered+=statisticsRepository.findByAccountID(id).getQuestionsAnswered();
        return questionsAnswered;
    }

    public int addAnsweredCorrectly(int q){return answeredCorrectly+=q;}
    public int getAnsweredCorrectly(int id){
        if(statisticsRepository.findByAccountID(id)==null) return answeredCorrectly;
        answeredCorrectly+= statisticsRepository.findByAccountID(id).getAnsweredCorrectly();
        return answeredCorrectly;
    }

    public int addGamesPlayed(int q){
        return gamesPlayed+=q;
    }
    public int getGamesPlayed(int id){
        if(statisticsRepository.findByAccountID(id)==null) return gamesPlayed;
        gamesPlayed+=statisticsRepository.findByAccountID(id).getGamesPlayed();
        return gamesPlayed;
    }

    @Override
    public void updateStatistics(int questionsAnswered, int answeredCorrectly, int gamesPlayed, int id) {
        statisticsRepository.updateStatistics(questionsAnswered,answeredCorrectly,gamesPlayed,id);
    }
    public int resetQA(){
        return questionsAnswered=0;
    }
    public int resetAC(){
        return  answeredCorrectly=0;
    }
    public int resetGP(){
        return  gamesPlayed=0;
    }

    public void resetScore(){
        gamesPlayed=0;
        answeredCorrectly=0;
        questionsAnswered=0;

    }
}
