package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Scores;
import is.hi.quiz.Services.AccountService;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class GameStateController {
    private  QuizService quizService;
    private  AccountService accountService;
    private  QuizController qc;

    @Autowired
    public GameStateController(QuizService quizService,QuizController qc,AccountService accountService){
        this.quizService = quizService;
        this.accountService=accountService;
        this.qc=qc;
    }

    // Lists available categories for the quiz
    // Returns: Template for category page
    @RequestMapping("/quiz")
    public String AccountController(Model model){
        quizService.resetNoOfQuestions();
        quizService.resetScore();
        quizService.resetAnswers();
        qc.guestScore=0;
        accountService.resetScore();

        List<Category> allCategories = quizService.findAllCategories();
        model.addAttribute("categories" ,allCategories);
        return "quizPage";
    }

    // Check number of players and adjust size of question list accordingly
    @RequestMapping(value="/quiz",method= RequestMethod.POST)
    public String startQuiz(@RequestParam(value = "players", required = false) String players,Model model){
        quizService.resetNoOfQuestions();
        quizService.resetScore();
        quizService.resetAnswers();
        accountService.resetAC();
        accountService.resetQA();
        accountService.resetGP();
        qc.guestScore=0;
        if(players.equals("One Player"))quizService.setOnePlayer();
        if(players.equals("Two Player"))quizService.setTwoPlayer();
        System.out.println(quizService.isTwoPlayer());
        List<Category> allCategories = quizService.findAllCategories();
        model.addAttribute("categories" ,allCategories);
        return "quizPage";
    }
    // Returns top scores for all time high scorers
    @RequestMapping(value = "/topscores", method = RequestMethod.GET)
    public String topScores(Model model, Account account){
        List <Scores> scores = quizService.findAllScores();
        model.addAttribute("scores",scores);
        return "topScores";
    }
}
