package is.hi.quiz.Services.Implementation;
import is.hi.quiz.Persistance.Entities.*;
import is.hi.quiz.Persistance.Repository.QuizRepository;
import is.hi.quiz.Persistance.Repository.ScoreRepository;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuizServiceImplementation implements QuizService {

    private List<Category> categories = new ArrayList<>();
    private QuizRepository quizRepository;
    private ScoreRepository scoreRepository;
    private Quiz quiz;
    private int id_counter2 = 0;
    private int noOfQuestions = 0;
    private int score=0;
    private List<String> answers = new ArrayList<>();
    private List<String> correctAnswers= new ArrayList<>();

    @Autowired
    public QuizServiceImplementation(QuizRepository quizRepository, ScoreRepository scoreRepository) {
        this.quizRepository = quizRepository;
        this.scoreRepository = scoreRepository;
        // Inserting questions into db.
        // TODO: Find a better way to insert into database.
    /*    quizRepository.save(new Question(0, "Question 1 - Category 0", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(0, "Question 2 - Category 0", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(0, "Question 3 - Category 0", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(1, "Question 1 - Category 1", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(1, "Question 2 - Category 1", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(1, "Question 3 - Category 1", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(2, "Question 1 - Category 2", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(2, "Question 2 - Category 2", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(2, "Question 3 - Category 2", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(3, "Question 1 - Category 3", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(3, "Question 2 - Category 3", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
        quizRepository.save(new Question(3, "Question 3 - Category 3", "OptionA", "OptionA", "OptionB", "OptionC", "OptionD"));
       */
        // scoreRepository.deleteAll();
        //quizRepository.deleteAll();
        // Mögulega gera category repository til að adda categories ?
        categories.add(new Category(0, "Category 0"));
        categories.add(new Category(1, "Category 1"));
        categories.add(new Category(2, "Category 2"));
        categories.add(new Category(3, "Category 3"));

        for (Category c : categories) {
            c.setID(id_counter2);
            id_counter2++;
        }
        helper();
    }

    /****************************
     * Question number handling
     ****************************/

    // When new quiz is played we reset number of questions that have been displayed/asked
    @Override
    public int resetNoOfQuestions() {
        return noOfQuestions = 0;
    }

    // Gets how many questions have been displayed/asked
    @Override
    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    // We need to increment after each question to get to next question after answering question w. button click
    @Override
    public int incrementNoOfQuestion() {
        return noOfQuestions++;
    }

    public void resetAnswers() {
        answers.clear();
        correctAnswers.clear();
    }
    public void addAnswer(String answer, String correctAns) {
        answers.add(answer);
        correctAnswers.add(correctAns);
    }

    public List<String> getAnswers() {
        return answers;
    }
    public List<String> getCorrectAnswers() {
        return correctAnswers;
    }
    /********************************************************************
     * Get questions handlers(save, delete, getby category, ID, etc...)
     ********************************************************************/

    // Gets all available categories to be displayed in view template
    // TODO: Needs to be implemented in a different way with a repository
    @Override
    public List<Category> findAllCategories() {
        return categories;
    }

    // Get questions by category
    @Override
    public List<Question> findByCategory(int categoryID) {
        return quizRepository.findByCategoryID(categoryID);
    }

    // Returns all questions in database
    @Override
    public List<Question> findAll() {
        return quizRepository.findAll();
    }

    // Finds a question by it's ID
    @Override
    public Question findById(long ID) {
        return quizRepository.findById(ID);
    }

    // Admin required for this action
    @Override
    public Question save(Question question) {
        return quizRepository.save(question);
    }

    // Admin required for this action
    @Override
    public void delete(Question question) {
        quizRepository.delete(question);
    }

    // Returns quiz by categoryID according to chosen category in template
    // contains a category with a set of questions.
    @Override
    public Quiz getQuiz(int categoryID, int noOfplayers) {
        helper();
        for (Category c : categories) {
            if (categoryID == c.getID()) {
                quiz = new Quiz(c, noOfplayers);
                return quiz;
            }
        }
        return null;
    }

    // Helper function
    // Add questions from dummy question db to relevant categories to make a "question package" for each category.
    public void helper() {
        for (int j = 0; j < categories.size(); j++) {
            List<Question> allQuestions = quizRepository.findAll();
            List<Question> questionList = new ArrayList<>();
            for (int i = 0; i < allQuestions.size(); i++) {
                if (categories.get(j).getCategoryID() == allQuestions.get(i).getCategoryID()) {
                    questionList.add(allQuestions.get(i));
                }
            }
            categories.get(j).setQuestions(questionList);
        }
    }
    /**************************************************************
     * Handle scores
     ***************************************************************/
    @Override
    public List <Scores> findByAccountID(long accountID) {
        return scoreRepository.findByAccountIDOrderByScoreDesc(accountID);
    }

    @Override
    public Scores saveScores(Scores scores) {
        return scoreRepository.save(scores);
    }

    @Override
    public List<Scores> findAllScores() {
        //return scoreRepository.findTop10ByOrderByScoreDesc();
        return scoreRepository.findAll();
    }

    // Helper function to keep track of scores when game is being played - could be done another way probably
    // Resets once round is over
    public int resetScore(){
        return score =0;
    }
    // Helper function to keep track of scores when game is being played- could be done another way probably
    public int addScore(int points){
        return score+=points;
    }
    // Helper function to keep track of scores when game is being played- could be done another way probably
    public int getScore(){
        return score;
    }

}
