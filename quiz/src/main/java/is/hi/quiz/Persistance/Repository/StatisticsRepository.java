package is.hi.quiz.Persistance.Repository;

import is.hi.quiz.Persistance.Entities.Scores;
import is.hi.quiz.Persistance.Entities.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface StatisticsRepository  extends JpaRepository<Statistics,Long> {
    Statistics save(Statistics statistics);
    Statistics findByAccountID(int accountID);
   // Statistics findByAccountUsername(String username);
   /* Statistics update(Statistics statistics);*/

    @Transactional
    @Modifying
    @Query("update Statistics s set s.questionsAnswered = ?1, s.answeredCorrectly = ?2, s.gamesPlayed=?3 where s.accountID = ?4")
    void updateStatistics(int questionsAnswered, int answeredCorrectly,int gamesPlayed, int accountID);


}
