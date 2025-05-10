package com.phuocan.quizapp.Controller;

import com.phuocan.quizapp.Model.Question;
import com.phuocan.quizapp.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("questions")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("category") String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestions(@RequestBody List<Question> questions) {
        return questionService.addQuestions(questions);
    }

    @PutMapping("update/{questionID}")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question, @PathVariable Integer questionID) {
        return questionService.updateQuestion(question, questionID);
    }

    @DeleteMapping("delete/{questionID}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer questionID) {
        return questionService.deleteQuestion(questionID);
    }
}
