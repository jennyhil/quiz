package is.hi.quiz.Services;

import is.hi.quiz.Persistance.Entities.*;

import java.util.List;

public interface QuizService {
    // Question Stuff
    List <Question> findByCategory(int categoryID);
    Quiz getQuiz(int categoryID,int noOfPlayers);
    List<Question> findAll();
    List<Category>findAllCategories();
    Question findById(long ID);
    Question save(Question question);
    void delete(Question question);

    // Question helper stuff
    int getNoOfQuestions();
    int resetNoOfQuestions();
    int incrementNoOfQuestion();
    int resetScore();
    int addScore(int score);
    int getScore();
    void addAnswer(String answer, String correctAns);
    void resetAnswers();
    List<String> getAnswers();
    List<String> getCorrectAnswers();
    // Maybe stuff
   /* void setQuestionListLength(int no);
    int getQuestionListLength();*/
    Boolean isTwoPlayer();
    void setTwoPlayer();
    void setOnePlayer();


    // Scores
    List <Scores> findByAccountID(long accountID);
    Scores saveScores(Scores scores);
    List <Scores> findAllScores();


}
