package is.hi.quiz.Controllers;

import is.hi.quiz.Persistance.Entities.Category;
import is.hi.quiz.Persistance.Entities.Question;
import is.hi.quiz.Services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class QuizRestController {
    private QuizService quizService;

    @Autowired
    public QuizRestController(QuizService quizService) {
        this.quizService = quizService;
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

    @RequestMapping("/categories")
    public List<Category> getCategories() {
        List<Category> categories = quizService.findAllCategories();

        return categories;
    }
}
