package is.hi.quiz.Services;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;

import java.util.List;

public interface AccountService {
    List<Account> findAll();
    //Account findById(long ID);
    Account save(Account account);
    void delete(Account account);
    Account findByUsername(String username);
    Account login(Account account);
}
