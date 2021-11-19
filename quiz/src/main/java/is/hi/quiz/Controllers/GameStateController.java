package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Services.AccountService;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GameStateController {
    private QuizService quizService;
    private AccountController ac;
    private AccountService as;
 //   public Account account;
  //  public int scores=0;

    @Autowired
    public GameStateController(QuizService quizService,AccountService as,AccountController ac){
        this.quizService = quizService;
        this.ac=ac;
        this.as=as;
    }

    // Lists available categories for the quiz
    // Returns: Template for category page
    @RequestMapping("/quiz")
    public String AccountController(Model model){
        quizService.resetNoOfQuestions();
        quizService.resetScore();
        List<Category> allCategories = quizService.findAllCategories();
        model.addAttribute("categories" ,allCategories);
        return "quizPage";
    }

    //kallað á þetta inn í loggedInUser
    @RequestMapping("/twoPlayer")
    public String TwoPlayerController(Model model){
        quizService.resetNoOfQuestions();
        List<Category> allCategories = quizService.findAllCategories();
        model.addAttribute("categories" ,allCategories);
        return "twoPlayer";
    }

    // Todo: Show high scores
    // Todo: Check for 1 or 2 player game

}
