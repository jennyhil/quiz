package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Quiz;
import is.hi.quiz.Services.AccountService;
import is.hi.quiz.Services.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class AccountController {
    AccountService accountService;
    QuizService quizService;

    public AccountController(AccountService accountService, QuizService quizService) {
        this.accountService = accountService;
        this.quizService = quizService;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(Account account){
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(Account account, BindingResult result, Model model){
        if(result.hasErrors()){
            return "signup";
        }
        Account exists = accountService.findByUsername(account.getUsername());
        if(exists == null){
            accountService.save(account);
            return "home";
        }
        model.addAttribute("alreadyExistsInput", true);
        return "signup";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Account account){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginPOST(Account account, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            return "login";
        }
        Account exists = accountService.login(account);
        // Get all questions for admin delete and/or admin add question.
        List<Question> allQuestions = quizService.findAll();
        if(exists != null){
            session.setAttribute("loggedInUser", exists);
            model.addAttribute("loggedInUser",exists);
            model.addAttribute("questions",allQuestions);
            /*if(exists.isAdmin()){
                return "redirect:/admin";
            }
            else return "loggedInUser";*/
            return "loggedInUser";
        }
        model.addAttribute("incorrectInput",true);
        return "login";
    }
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Account account){
        List <Question> questions = quizService.findAll();
        model.addAttribute("questions",questions);
        return "admin";
    }

    @GetMapping("/")
    public String style(){
        // Model of class structure that allows to insert data into templates and http session
        // Busniess logic
        // Add some data to the model
        // Call a method in service class
        return "home";
    }
}
