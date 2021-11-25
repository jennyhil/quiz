package is.hi.quiz.Persistance.Repository;

import is.hi.quiz.Persistance.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account save(Account account);
    void delete (Account account);
    List<Account> findAll();
    Account findByUsername(String username);
}
