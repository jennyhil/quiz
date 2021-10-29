package is.hi.quiz.Services;

import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;

import java.util.List;

public interface QuizService {
    List <Question> findByCategory(int categoryID);
    List<Question> findAll();
    List<Category>findAllCategories();
    Question findById(long ID);
    Question save(Question question);
    void delete(Question question);
    int getNoOfQuestions();
    int resetNoOfQuestions();
    int incrementNoOfQuestion();
}