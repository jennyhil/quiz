package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Scores;
import is.hi.quiz.Persistance.Entities.Statistics;
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
import java.util.Objects;

@Controller
public class AccountController {
    AccountService accountService;
    QuizService quizService;
    public String currentPlayer;
    private long currentID;
    private Account exists;

    public AccountController(AccountService accountService, QuizService quizService) {
        this.accountService = accountService;
        this.quizService = quizService;

    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(Account account){
        account.setAdmin(false);
        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(Account account, BindingResult result, Model model){
        if(result.hasErrors()){
            return "signup";
        }
        Account exists = accountService.findByUsername(account.getUsername());
        //Don't let an account be saved without a username or password
        System.out.println(account.isAdmin());
        if(!Objects.equals(account.getPassword(), "") && !Objects.equals(account.getUsername(), "")) {
            //Check if it already exists
           if(exists == null){

                accountService.save(account);
                return "home";
            }
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
        Account username =accountService.findByUsername(account.getUsername());
        if(username==null)  model.addAttribute("notRegistered",true);
         exists = accountService.login(account);

        // Get all questions for admin delete and/or admin add question.
        if(exists != null){
            currentPlayer=exists.getUsername();
            currentID=(int)exists.getID();
            session.setAttribute("loggedInUser", exists);
            model.addAttribute("loggedInUser",exists);
          /* if(exists.isAdmin()){
                return "redirect:/admin";
            }*/
            return "redirect:/user";
        }
        model.addAttribute("incorrectInput",true);
        return "login";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(Model model,Account account){

        List<Question> allQuestions = quizService.findAll();
        model.addAttribute("loggedInUser",exists);
        model.addAttribute("questions",allQuestions);
        if(currentPlayer!=null) return "loggedInUser";
        else return "login";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Account account){

        List <Question> questions = quizService.findAll();
        System.out.println("LENGTH"+questions.size());
        for(Question q: questions)System.out.println("ALL QUESTIONS"+q.getQuestionText());
        model.addAttribute("questions",questions);
        return "admin";
    }
    @RequestMapping(value = "/topscores", method = RequestMethod.GET)
    public String topScores(Model model, Account account){
        List <Scores> scores = quizService.findAllScores();
        model.addAttribute("scores",scores);
        return "topScores";
    }

    @RequestMapping(value = "/accountPage", method = RequestMethod.GET)
    public String accountPage(Model model, Account account){
        List <Scores> scores = quizService.findByAccountID(currentID);
        Statistics statistics = accountService.findByAccountID((int)currentID);
        model.addAttribute("scores",scores);
        model.addAttribute("statistics",statistics);
        if(currentPlayer!=null)return "accountPage";
        else return "login";
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
