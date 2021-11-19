package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Account;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Quiz;
import is.hi.quiz.Persistance.Entities.Scores;
import is.hi.quiz.Services.AccountService;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class QuizController {
    private QuizService quizService;
    private AccountService as;
    private AccountController ac;

    @Autowired
    public QuizController(QuizService quizService, AccountService as,AccountController ac){
        this.quizService = quizService;
        this.as=as;
        this.ac=ac;
    }
     @GetMapping("/category/{id}")
    public String getQuestions(@PathVariable("id")long id,Model model){
        Question nextQuestion;
        nextQuestion = getNextQuestion(id);
        int scores=quizService.getScore();
         model.addAttribute("questions", nextQuestion);
         model.addAttribute("scores", scores);
       //  model.addAttribute("scores", lisi);
        return "displayQuestion";
    }

    @RequestMapping(value="/category/{id}",method=RequestMethod.POST)
    public String checkAnswer(@PathVariable("id")long id,@RequestParam(value = "option", required = false) String option,Question question, BindingResult result,Model model){
        Quiz quiz= quizService.getQuiz((int)id,1);
        List<Question> allQuestions = quiz.getCategory().getQuestions();
        String questionAnswer = allQuestions.get(quizService.getNoOfQuestions()-1).getCorrectAnswer();
        if(questionAnswer.equals(option)){
            quizService.addScore(100);
            System.out.println("CORRECT: "+" scores: "+quizService.getScore());
        }
        return"redirect:/category/{id}";
    }
    @GetMapping("/category2/{id}")
    public String getQuestions2(@PathVariable("id")long id,Model model){
        Question nextQuestion;
        nextQuestion = getNextQuestion(id);
        model.addAttribute("questions", nextQuestion);
        return "displayQuestionTwoPlayer";
    }

    // Helper function to get next question when button is clicked and keeps count of questions.
    // Param is the id of chosen category.
    // Returns: A question object
    public Question getNextQuestion(long id){
        Account account=as.findByUsername(ac.currentPlayer);
        Scores score =new Scores(account, quizService.getScore());

        Quiz quiz= quizService.getQuiz((int)id,1);
        List<Question> allQuestions = quiz.getCategory().getQuestions();
        if(quizService.getNoOfQuestions()< allQuestions.size()){
            Question question = allQuestions.get(quizService.getNoOfQuestions());
            // Increment to get next question
            quizService.incrementNoOfQuestion();
            return question;
        }
        quizService.saveScores(score);
        return null;
    }

    // Admin action - requires admin log in.
    // Returns: A template to input a new question and answers.
    @RequestMapping(value="/addquestion",method=RequestMethod.GET)
    public String addQuestion(Question question){
        return "newQuestion";
    }

    // Admin action - requires admin log in. Adds a question.
    // Returns: Redirects to homepage if no errors in input fields.
    @RequestMapping(value="/addquestion",method=RequestMethod.POST)
    public String addQuestion(Question question, BindingResult result,Model model){
        if(result.hasErrors()){
            return "newQuestion";
        }
        quizService.save(question);
        return "redirect:/admin";
    }

    // Admin action - requires admin log in. Deletes a question
    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    public String deleteQuestion(@PathVariable("id")long id,Model model){
        Question questionToDelete = quizService.findById(id);
        quizService.delete(questionToDelete);
        return "redirect:/admin";
    }
}
