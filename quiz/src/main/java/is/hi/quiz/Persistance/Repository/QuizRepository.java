package is.hi.quiz.Persistance.Repository;

import is.hi.quiz.Persistance.Entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository extends JpaRepository<Question,Long> {
    Question save(Question question);
    void delete(Question question);
    Question findById(long ID);
    List<Question> findByCategoryID(int categoryID);
    List<Question> findAll();

}
