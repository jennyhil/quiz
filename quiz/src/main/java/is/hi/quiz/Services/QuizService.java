package is.hi.quiz.Services;

import is.hi.quiz.Persistance.Entities.*;

import java.util.List;

public interface QuizService {
    List <Question> findByCategory(int categoryID);
    Quiz getQuiz(int categoryID,int noOfPlayers);
    List<Question> findAll();
    List<Category>findAllCategories();
    Question findById(long ID);
    Question save(Question question);
    void delete(Question question);
    int getNoOfQuestions();
    int resetNoOfQuestions();
    int incrementNoOfQuestion();
    int resetScore();
    int addScore(int score);
    int getScore();

    // For quizzes
    List <Scores> findByAccountID(long accountID);
    Scores saveScores(Scores scores);
    List <Scores> findAllScores();


}
