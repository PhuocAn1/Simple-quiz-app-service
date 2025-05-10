package com.phuocan.quizapp.Service;

import com.phuocan.quizapp.DAO.QuestionDAO;
import com.phuocan.quizapp.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;
    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestions(List<Question> questions) {
        try {
            questionDAO.saveAll(questions);
            return new ResponseEntity<>("Success", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to save", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Question question, Integer questionID) {
        try {
            if (!questionDAO.existsById(questionID)) {
                return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Question updatedQuestion = new Question(questionID, question.getQuestionTitle()
                    , question.getOption1(), question.getOption2()
                    , question.getOption3(), question.getOption4()
                    , question.getRightAnswer(), question.getDifficultyLevel()
                    , question.getCategory());

            questionDAO.save(updatedQuestion);
            return new ResponseEntity<>("Update success!", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteQuestion(Integer questionID) {
        if (questionID == null) {
            return new ResponseEntity<>("ID is null", HttpStatus.BAD_REQUEST);
        }

        try {
            if (questionDAO.existsById(questionID)) {
                questionDAO.deleteById(questionID);
                return new ResponseEntity<>("Delete success!", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("ID not found", HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("An error occurred while deleting", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

