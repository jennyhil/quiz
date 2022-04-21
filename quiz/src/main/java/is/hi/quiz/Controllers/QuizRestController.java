package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.*;
import is.hi.quiz.Services.AccountService;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class QuizRestController {
    private QuizService quizService;
    private AccountService accountService;

    @Autowired
    public QuizRestController(QuizService quizService,AccountService accountService) {
        this.quizService = quizService;
        this.accountService = accountService;
    }

    @RequestMapping("/questions")
    public List<Question> getQuestions() throws InterruptedException {
        List<Question> questions = quizService.findAll();

        return questions;
    }

    @RequestMapping("/questions/{id}")
    public List<Question> getQuestionByCategory(@PathVariable(value="id") int id) throws InterruptedException {
        List<Question> questionsByCat = quizService.findByCategory(id);
        return questionsByCat;

    }

    @RequestMapping(value="/deleteQuestion/{id}",method = RequestMethod.DELETE)
    public String deleteQuestion(@PathVariable("id")long id){
        Question questionToDelete = quizService.findById(id);
        quizService.delete(questionToDelete);
        return null;
    }

    @RequestMapping("/categories")
    public List<Category> getCategories() {
        List<Category> categories = quizService.findAllCategories();

        return categories;
    }

    // Returns top scores for all time high scorers
    @RequestMapping("/topscoresAPI")
    public List<String> getTopScores() {
        List <Scores> scores = quizService.findAllScores();
        List <String> topScores = new ArrayList<>();
        for(int i=0;i<scores.size();i++){
            topScores.add(scores.get(i).getAccount().getUsername()+" "+scores.get(i).getScore());
        }
        return topScores;
    }

    @RequestMapping(value="/getScores", method = RequestMethod.GET)
    public List<Scores> getScores() throws InterruptedException {
        List<Scores> scores = quizService.findAllScores();

        return scores;
    }

    //
    @PostMapping( "saveScore")
    Scores score(@RequestBody String params, String score, String username) {
        // find account by username
        Account account = accountService.findByUsername(username);
        Scores userScore = new Scores(account, Integer.parseInt(score));
        return quizService.saveScores(userScore);
    }

    @PostMapping( "statisticsPOST")
    Statistics statistics(@RequestBody String params,String username, String questionsAnswered, String answeredCorrectly, String gamesPlayed) {
        // findaccout by username
        Account account = accountService.findByUsername(username);
        Statistics statistics = new Statistics(account,(int)account.getID(),Integer.parseInt(questionsAnswered),Integer.parseInt(answeredCorrectly),Integer.parseInt(gamesPlayed));
        return accountService.saveStatistics(statistics);
    }
}