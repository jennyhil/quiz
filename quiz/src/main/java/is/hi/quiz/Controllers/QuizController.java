package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.*;
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
    private final QuizService quizService;
    private final AccountService as;
    private final AccountController ac;
    private Boolean nextPlayer=false;
    public int guestScore=0;
    private Scores score;
    private Statistics statistic;
    private Account account;
    private boolean quizOver=false;

    @Autowired
    public QuizController(QuizService quizService, AccountService as,AccountController ac){
        this.quizService = quizService;
        this.as=as;
        this.ac=ac;
    }
     @GetMapping("/category/{id}")
    public String getQuestions(@PathVariable("id")long id,Model model){
        if(!quizService.isTwoPlayer())nextPlayer=false;
        Question nextQuestion;
        int scores=quizService.getScore();
        nextQuestion = getNextQuestion(id);

        // One player answer lists to be displayed for one player game
        List<String>correctAnswers =quizService.getCorrectAnswers();
        List<String>answers =quizService.getAnswers();

        // Make answer lists for p1 and p2 to display at the end of game
         List<String>p1Answers =answers.subList(0,answers.size()/2);
         List<String>p2Answers =answers.subList(answers.size()/2,answers.size());
         List<String>p1corrAnswers =correctAnswers.subList(0,correctAnswers.size()/2);
         List<String>p2corrAnswers =correctAnswers.subList(correctAnswers.size()/2,correctAnswers.size());

        // Add models
        model.addAttribute("scores", scores);
        model.addAttribute("questions", nextQuestion);
        model.addAttribute("answers", answers);
        model.addAttribute("correctanswers", correctAnswers);
        model.addAttribute("guestScore",guestScore);
        model.addAttribute("nextPlayer",nextPlayer);
        model.addAttribute("onePlayer",quizService.isTwoPlayer());

        model.addAttribute("p1corrAnswers",p1corrAnswers);
        model.addAttribute("p2corrAnswers",p2corrAnswers);
        model.addAttribute("p1answers",p1Answers);
        model.addAttribute("p2answers",p2Answers);

         return "displayQuestion";
    }

    @RequestMapping(value="/category/{id}",method=RequestMethod.POST)
    public String checkAnswer(@PathVariable("id")long id,@RequestParam(value = "option", required = false) String option,Question question, BindingResult result,Model model){
        Quiz quiz= quizService.getQuiz((int)id,1);
        List<Question> allQuestions = quiz.getCategory().getQuestions();
        String questionAnswer = allQuestions.get(quizService.getNoOfQuestions()-1).getCorrectAnswer();
        quizService.addAnswer(option, questionAnswer);
        if(questionAnswer.equals(option)){
            if(!nextPlayer){
                quizService.addScore(100);
                as.addAnsweredCorrectly(1);
            }
            else guestScore+=100;

        }
        // Count answered questions
        if(!nextPlayer)as.addQuestionsAnswered(1);
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
        onePlayerSetUp();
        Quiz quiz= quizService.getQuiz((int)id,1);
        List<Question> allQuestions = quiz.getCategory().getQuestions();
        if(quizService.getNoOfQuestions()< allQuestions.size()){
            Question question = allQuestions.get(quizService.getNoOfQuestions());
            int  limit =(int)Math.floor(allQuestions.size()/2);
            // Increment to get next question
            quizService.incrementNoOfQuestion();
            // Change player's turn and save account score
            if(quizService.getNoOfQuestions()>limit && !nextPlayer){
                if(quizService.isTwoPlayer())nextPlayer=true;
            }
            return question;
        }
        setStatisticAndScore();
        return null;
    }

public boolean exists(){
    Statistics exists = as.findByAccountID((int)account.getID());
        if(exists!=null)return true;
        else return false;
}

public void setStatisticAndScore(){
    as.addGamesPlayed(1);
    statistic = new Statistics(account,(int)account.getID(),as.getQuestionsAnswered(),as.getAnsweredCorrectly(),as.getGamesPlayed());
    quizService.saveScores(score);
    if(exists()) as.updateStatistics(as.getQuestionsAnswered(),as.getAnsweredCorrectly(),as.getGamesPlayed(),(int)account.getID());
    else as.saveStatistics(statistic);
    nextPlayer=false;
}


    public void onePlayerSetUp(){
        if(!nextPlayer) {
            account = as.findByUsername(ac.currentPlayer);
            score = new Scores(account, quizService.getScore());
        }
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
