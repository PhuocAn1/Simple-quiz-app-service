package com.phuocan.quizapp.Controller;

import com.phuocan.quizapp.Model.Question;
import com.phuocan.quizapp.Model.QuestionAnswer;
import com.phuocan.quizapp.Model.QuestionWrapper;
import com.phuocan.quizapp.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title) {
        return quizService.createQuiz(category, numQ, title);
    }

    @GetMapping("get/{quizId}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer quizId, @RequestBody List<QuestionAnswer> questionAnswers) {
        return quizService.calculateResult(quizId, questionAnswers);
    }

    @DeleteMapping("delete/{quizId}")
    public ResponseEntity<String> deleteQuiz(@PathVariable Integer quizId) {
        return quizService.deleteQuiz(quizId);
    }

    @PutMapping("update/{quizId}")
    public ResponseEntity<String> updateQuiz(@PathVariable Integer quizId, @RequestBody List<Question> questions) {
        // This method add new questions into a quiz list of questions
        return quizService.updateQuiz(quizId, questions);
    }

    @DeleteMapping("delete/{quizId}/questions")
    public ResponseEntity<String> removeQuestionFromQuiz(@PathVariable Integer quizId, @RequestBody List<Integer> questions) {
        // This method remove a list of questions from a quiz.
        return quizService.removeQuestionFromQuiz(quizId, questions);
    }
}
