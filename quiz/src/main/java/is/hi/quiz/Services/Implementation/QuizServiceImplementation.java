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
    private Boolean twoPlayer = false;
    @Autowired
    public QuizServiceImplementation(QuizRepository quizRepository, ScoreRepository scoreRepository) {
        this.quizRepository = quizRepository;
        this.scoreRepository = scoreRepository;
        // Inserting questions into db.
        //0 = Entertainment
        //1 = General Knowledge
        //2 = Geography
        //3 = Sports
        //scoreRepository.deleteAll();
/*
        quizRepository.deleteAll();
        Entertainment = 10
        quizRepository.save(new Question(0, "Who portrays the character 'Paul' in the film Dune(2021)?", "Timothee Chalamet", "Jason Momoa", "Timothee Chalamet", "Harry Styles", "Dave Bautista"));
        quizRepository.save(new Question(0, "Which member of the band 'One Direction' famously left the band on the 25th of March 2015?", "Zayn", "Harry", "Louis", "Neil", "Zayn"));
        quizRepository.save(new Question(0, "Who won 'Best Actor' at the Oscars in 2021?", "Anthony Hopkins", "Leonardo DiCaprio", "Anthony Hopkins", "Bob", "Adam Sandler"));
        quizRepository.save(new Question(0, "How many seasons are there of the show 'The Bold and the Beautiful'?", "35", "30", "120", "35", "45"));
        quizRepository.save(new Question(0, "What is the name of Wario‘s sidekick in the Super Mario franchise?","Waluigi", "Luigi", "Waluigi", "Bowser", "Toad"));
        quizRepository.save(new Question(0, "What spell in Harry potter can unlock doors? ", "Alohamora", "Alohamora", "Lumos", "Wingardium Leviosa", "Expelliarmus"));
        quizRepository.save(new Question(0, "Which British actor will play Batman in the upcoming reboot?", "Robert Pattinson", "Robert Pattinson", "Ricky Gervais", "Martin Freeman", "James Buckley"));
        quizRepository.save(new Question(0, "Which Harry Potter book does not exist? Harry Potter and.. ", "the Sparkling Wands", "the Philosopher's Stone", "the Order of the Phoenix", "the Half-Blood Prince", "the Sparkling Wands"));
        quizRepository.save(new Question(0, "Who wrote 'The Lord of the Rings'?", "J.R.R Tolkien", "Dan Brown", "J.K Rowling", "J.R.R Tolkien", "Milan Kundera"));
        quizRepository.save(new Question(0, "Who of the following has not been a judge on 'The Great British Bake Off'?", "Jenny Omars", "Jenny Omars", "Prue Leith", "Paul Hollywood", "Mary Berry"));
        //General Knowledge = 10
        quizRepository.save(new Question(1, "What year did the Titanic famously sink?", "1912", "1912", "1918", "1926", "1900"));
        quizRepository.save(new Question(1, "What is the largest animal in the world?", "Blue Whale", "Blue Whale", "Hippopotamus", "African elephant", "Leopard"));
        quizRepository.save(new Question(1, "What is the key ingredient in the Italian dish 'parmigiana di melanzane'?", "Aubergine", "Parmesan", "Aubergine", "Penne pasta", "Bananas"));
        quizRepository.save(new Question(1, "What is the chemical formula for sparkling water?", "H2CO3", "H2OCO2", "H2OCO", "H2OC2", "H2CO3"));
        quizRepository.save(new Question(1, "What fish is known for swimming against the stream?", "Salmon", "Salmon", "Trout", "Goldfish", "Tuna"));
        quizRepository.save(new Question(1, "What year did 'The Beatles' split up?", "1970", "1970", "1969", "1980", "1966"));
        quizRepository.save(new Question(1, "What is queen Elizabeth II's surname?","Windsor", "Breadfordshire", "Cummings", "Primrose", "Windsor"));
        quizRepository.save(new Question(1, "What does 'Sn' stand for on the periodic table?", "Tin", "Snow", "Silver", "Tin", "Snulker"));
        quizRepository.save(new Question(1, "What is the person who runs a game of 'Dungeons and Dragons' called?", "Dungeon Master", "Dungeon Master", "Wizard Man", "A noob", "Dungeon King"));
        //quizRepository.save(new Question(1, "How many sides does a hexagon have?", "Six", "Hex", "Five", "Sixteen", "Six"));
        quizRepository.save(new Question(1, "What is the name of the green pigment in plants that allows the photosynthesis?", "Chlorophyl", "Chlorophyl", "Anylin", "Greentini", "Seiva"));
        //Geography = 10
        quizRepository.save(new Question(2, "What is the smallest continent on the Earth?", "Oceania", "Oceania", "Europe", "Asia", "Antarctica"));
        quizRepository.save(new Question(2, "What is the most populous city in the world?", "Tokyo", "Reykjavik", "Delhi", "Beijing", "Tokyo"));
        quizRepository.save(new Question(2, "What is the capital city of Afghanistan?", "Kabul", "Shanghai", "Kandahar", "Kabul", "Jalalabad"));
        quizRepository.save(new Question(2, "What is the most populous country in the world?", "China", "India", "Iceland", "Nigeria", "China"));
        quizRepository.save(new Question(2, "In what fjord in Iceland is Isafjordur?", "Skutulsfirdi", "Alftafirdi", "Faskrudsfirdi", "Isafirdi", "Skutulsfirdi"));
        quizRepository.save(new Question(2, "What is the capital city of New York State?", "Albany", "Yonkers", "Albany", "New York", "Buffalo"));
        quizRepository.save(new Question(2, "What is the southernmost capital in Europe?", "Valetta (Malta)", "Copenhagen (Denmark)", "Nicosia (Cyprus)", "Valetta (Malta)", "Rome (Italy)"));
        quizRepository.save(new Question(2, "Which of the following islands is the smallest in size?", "Malta", "Isle of Man", "Cyprus", "Malta", "Tenerife"));
        quizRepository.save(new Question(2, "What country has the most natural lakes?", "Canada", "China", "Canada", "India", "Norway"));
        quizRepository.save(new Question(2, "Which African nation has the most pyramids?", "Sudan", "Algeria", "Egypt", "Libya", "Sudan"));
        //Sports = 20
        quizRepository.save(new Question(3, "How many chukkers are there in a polo match?", "6", "4", "1", "2", "6"));
        quizRepository.save(new Question(3, "What is the beginner belt in karate?", "White", "Orange", "White", "Green", "Yellow"));
        quizRepository.save(new Question(3, "Who is the most successful F1 driver of all time?", "Lewis Hamilton", "Lewis Hamilton", "Max Verstappen", "Kimi Raikkonen", "Valtteri Bottas"));
        quizRepository.save(new Question(3, "What sport does not require its players to carry a stick of some sort?", "Rugby", "Lacrosse", "Rugby", "Hockey", "Golf"));
        quizRepository.save(new Question(3, "What is the maximum score you can achieve in 10-pin bowling?", "300", "200", "100", "300", "1000"));
        quizRepository.save(new Question(3, "Which country won the FIFA Women's World Cup in 2019?", "The United States", "France", "Germany", " The United States", "Azerbaijan"));
        quizRepository.save(new Question(3, "How many holes are played in an average round of golf?", "18", "16", "18", " 10", "12"));
        quizRepository.save(new Question(3, "In which winter sport are the terms 'stale fish' and 'mule kick' used?", "Snowboarding", "Snowboarding", "Skiing", "Ice skating", "Bob Sledding"));
        quizRepository.save(new Question(3, "Which of the following is not a swim stroke?", "Toestroke", "Butterfly", "Backstroke", "Breaststroke", "Toestroke"));
        quizRepository.save(new Question(3, "Who was the youngest player to score 10.000 points in the NBA?", "LeBron James", "Kobe Bryant", "LeBron James", "Michael Jordan", "Kareem Abdul-Jabbar"));
        quizRepository.save(new Question(3, "What is the only sport to be played on the moon?", "Golf", "Golf", "Football", "Basketball", "Bowling"));
        quizRepository.save(new Question(3, "Which country won the first ever football world cup?", "Uruguay", "Argentina", "Brazil", "Uruguay", "Columbia"));
        quizRepository.save(new Question(3, "Who did Bobby Fischer defeat to win the World Chess Championship in 1972 in a famous match held in Reykjavik?", "Boris Spassky", "Garry Kasparov", "Boris Spassky", "Anatoly Karpov", "Paul Morphy"));
        quizRepository.save(new Question(3, "What’s the diameter of a basketball hoop in inches?", "18 inches", "14 inches", "16 inches", "18 inches", "20 inches"));
        quizRepository.save(new Question(3, "How long is the total distance of a marathon in kilometers?", "42.16 kilometers", "42.16 kilometers", "40.12 kilometers", "45.41 kilometers", "47.82 kilometers"));
        quizRepository.save(new Question(3, "Who is the Premier League’s all-time top scorer?", "Alan Shearer", "Wayne Rooney", "Andy Cole", "Robbie Fowler", "Alan Shearer"));
        quizRepository.save(new Question(3, "What was Muhammad Ali’s original name?", "Cassius Clay", "Rubin Carter", "Cassius Clay", "Joe Louis", "Jimmy Wilde"));
        quizRepository.save(new Question(3, "What is the national sport of Russia?", "Bandy", "Football", "Ice skating", "Gymnastics", "Bandy"));
        quizRepository.save(new Question(3, "How many NBA championships did Michael Jordan win with the Chicago Bulls?", "6", "4", "5", "6", "7"));
        quizRepository.save(new Question(3, "How many medals did China win at the Beijing Olympics?", "100", "37", "51", "67", "100"));

*/


        // Mögulega gera category repository til að adda categories ?
        categories.add(new Category(0, "Entertainment"));
        categories.add(new Category(1, "General Knowledge"));
        categories.add(new Category(2, "Geography"));
        categories.add(new Category(3, "Sports"));

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
        List<Scores> topTen =scoreRepository.findByAccountIDOrderByScoreDesc(accountID);
        if(topTen.size()>10) topTen = topTen.subList(0,10);
        return topTen;
    }

    @Override
    public Scores saveScores(Scores scores) {
        return scoreRepository.save(scores);
    }

    @Override
    public List<Scores> findAllScores() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
     }

    // Helper functions to keep track of scores when game is being played
    // Resets once round is over
    public int resetScore(){
        return score =0;
    }
    public int addScore(int points){
        return score+=points;
    }
    public int getScore(){
        return score;
    }

}
