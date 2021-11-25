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
/*****************************************************************************
 * Account Controller that handles admin and signing up and logging in users
 ****************************************************************************/
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

    // Return sign up page
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signupGET(Account account){
        account.setAdmin(false);
        return "signup";
    }

    // Signs up user if all is good in the input fields and user does not exist.
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signupPOST(Account account, BindingResult result, Model model){
        if(result.hasErrors()){
            return "signup";
        }
        Account exists = accountService.findByUsername(account.getUsername());
        //Don't let an account be saved without a username or password
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

    // Returns log in page
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(Account account){
        return "login";
    }

    // Logs user in
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
            if(exists.isAdmin()==null)return "redirect:/user";
            else if(exists.isAdmin()){
                return "redirect:/admin";
            }

        }
        model.addAttribute("incorrectInput",true);
        return "login";
    }

    // Logs out user
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutGET(Account account){
        System.out.println("LOG OUT");
        if(exists!=null)exists=accountService.logout(exists);
        currentPlayer=null;
        return "home";}

    // Returns home page for the logged in user
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userPage(Model model,Account account){

        List<Question> allQuestions = quizService.findAll();
        model.addAttribute("loggedInUser",exists);
        model.addAttribute("questions",allQuestions);
        if(currentPlayer!=null) return "loggedInUser";
        else return "login";
    }

    // Admin rights required to access admin page. Returns login if user doesn't have admin acess.
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Account account){
        if(exists==null) return "redirect:/login";
        if(exists.isAdmin() !=null && exists.isAdmin()){
            List<Question> questions = quizService.findAll();
            model.addAttribute("questions", questions);
            return "admin";
        }
        else return "redirect:/login";
    }
    // Returna logged in user's account page
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
        return "home";
    }
}
