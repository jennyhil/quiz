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
    private int guestScore=0;
    private List<String> answers = new ArrayList<>();
    private List<String> correctAnswers= new ArrayList<>();
    private Boolean twoPlayer = false;
    @Autowired
    public QuizServiceImplementation(QuizRepository quizRepository, ScoreRepository scoreRepository) {
        this.quizRepository = quizRepository;
        this.scoreRepository = scoreRepository;
        // Inserting questions into db.
        // TODO: Find a better way to insert into database.
        /*
        quizRepository.save(new Question(0, "What is the only sport to be played on the moon?", "Golf", "Golf", "Football", "Basketball", "Bowling"));
        quizRepository.save(new Question(0, "Which country won the first ever football world cup?", "Uruguay", "Argentina", "Brazil", "Uruguay", "Columbia"));
        quizRepository.save(new Question(0, "Who did Bobby Fischer defeat to win the World Chess Championship in 1972 in a famous match held in Reykjavik?", "Boris Spassky", "Garry Kasparov", "Boris Spassky", "Anatoly Karpov", "Paul Morphy"));
        quizRepository.save(new Question(0, "What’s the diameter of a basketball hoop in inches?", "18 inches", "14 inches", "16 inches", "18 inches", "20 inches"));
        quizRepository.save(new Question(0, "How long is the total distance of a marathon in kilometers?", "42.16 kilometers", "42.16 kilometers", "40.12 kilometers", "45.41 kilometers", "47.82 kilometers"));
        quizRepository.save(new Question(0, "Who is the Premier League’s all-time top scorer?", "Alan Shearer", "Wayne Rooney", "Andy Cole", "Robbie Fowler", "Alan Shearer"));
        quizRepository.save(new Question(0, "What was Muhammad Ali’s original name?", "Cassius Clay", "Rubin Carter", "Cassius Clay", "Joe Louis", "Jimmy Wilde"));
        quizRepository.save(new Question(0, "Which Formula 1 driver has won the most races in the history of the sport?", "Lewis Hamilton", "Michael Schumacher", "Sebastian Vettel", "Ayrton Senna", "Lewis Hamilton"));
        quizRepository.save(new Question(0, "How many NBA championships did Michael Jordan win with the Chicago Bulls?", "6", "4", "5", "6", "7"));
        quizRepository.save(new Question(0, "How many medals did China win at the Beijing Olympics?", "100", "37", "51", "67", "100"));
*/

        //scoreRepository.deleteAll();
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
        helper(id_counter2);
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

public Boolean isTwoPlayer(){
        return twoPlayer;
}

public void setTwoPlayer(){
        twoPlayer=true;
}
    public void setOnePlayer(){
        twoPlayer=false;
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
        helper(categoryID);
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
    public void helper(int categoryID) {
        for (int j = 0; j < categories.size(); j++) {
           List<Question> questionList = quizRepository.findByCategoryID(categoryID);
         /*   List<Question> questionList = new ArrayList<>();
            for (int i = 0; i < allQuestions.size(); i++) {
                if (categories.get(j).getCategoryID() == allQuestions.get(i).getCategoryID()) {
                    questionList.add(allQuestions.get(i));
                }
            }*/
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
        return scoreRepository.findTop10ByOrderByScoreDesc();
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
