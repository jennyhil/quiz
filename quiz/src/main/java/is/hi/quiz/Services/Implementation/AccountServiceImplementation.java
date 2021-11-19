package is.hi.quiz.Services.Implementation;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Repository.AccountRepository;
import is.hi.quiz.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountServiceImplementation implements AccountService {
    private AccountRepository accountRepository;


    @Autowired
    public AccountServiceImplementation(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
        //accountRepository.deleteAll();
        //accountRepository.save(new Account("admin","1234","email@email.com","Admin Adminsson",true));
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }
/*
    @Override
    public Account findById(long ID) {
        for(Account a: accountRepository){
            if(a.getID()==ID){
                return a;
            }
        }
        return null;
    }*/

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
}
