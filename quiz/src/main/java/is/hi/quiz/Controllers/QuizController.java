package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Persistance.Entities.Quiz;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuizController {
    private QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService, GameStateController gsc){
        this.quizService = quizService;
    }

    // Gets the id from chosen category and asks helper function getNextQuestion() to get questions from that category
    // Returns: A template with one questions and 4 options to choose from.
    @GetMapping("/category/{id}")
    public String getQuestions(@PathVariable("id")long id,Model model){
        Question nextQuestion;
        nextQuestion = getNextQuestion(id);
        model.addAttribute("questions", nextQuestion);
        return "displayQuestion";
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
        Quiz quiz= quizService.getQuiz((int)id,1);
        List<Question> allQuestions = quiz.getCategory().getQuestions();
        if(quizService.getNoOfQuestions()< allQuestions.size()){
            Question question = allQuestions.get(quizService.getNoOfQuestions());
            // Increment to get next question
            quizService.incrementNoOfQuestion();
            return question;
        }
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
