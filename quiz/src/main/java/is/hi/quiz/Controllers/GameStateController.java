package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class GameStateController {
    private QuizService quizService;
    //public int noOfQuestions; í staðinn er quizService og quizImplementationService að halda utan um noQuestions.

    @Autowired
    public GameStateController(QuizService quizService){
        this.quizService = quizService;
    }

    // Lists available categories for the quiz
    // Returns: Template for category page
    @RequestMapping("/quiz")
    public String AccountController(Model model){
        quizService.resetNoOfQuestions();
        List<Category> allCategories = quizService.findAllCategories();
        model.addAttribute("categories" ,allCategories);
        return "quizPage.html";
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
