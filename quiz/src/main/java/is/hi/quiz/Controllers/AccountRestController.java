package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Statistics;
import is.hi.quiz.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {

    private AccountService accountService;

    @Autowired
    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping("/accounts")
    public List<Account> getAccounts() {
        List<Account> accounts = accountService.findAll();
        return accounts;
    }

    //login
    @RequestMapping(value = "loginAPI", method = RequestMethod.POST)
    public Account loginPost(@RequestBody Account account) {
        Account exists = accountService.login(account);
        if (exists != null) {
            return account;
        }
        else return null;
    }

    // save account
    @PostMapping("/signup")
    Account newAccount(@RequestBody Account newAccount) {
        Account exists = accountService.findByUsername(newAccount.getUsername());
        if (exists == null) {
            return accountService.save(newAccount);
        }
        else return null;
    }

    @RequestMapping(value="/deleteAccount/{id}",method = RequestMethod.DELETE)
    public String deleteAccount(@PathVariable("id")String id){
        Account delAccount = accountService.findByUsername(id);
        accountService.delete(delAccount);
        return null;
    }

    @RequestMapping(value="/getStats/{id}", method = RequestMethod.GET)
    public Statistics getStatistics(@PathVariable("id") int id) {
        Statistics stats = accountService.findByAccountID(id);
        return stats;
    }

    @PostMapping("/saveStats")
    Statistics statistics(@RequestBody Statistics statistics) {
        return accountService.saveStatistics(statistics);
    }

}