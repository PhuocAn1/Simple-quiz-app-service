package com.phuocan.quizapp.Service;

import com.phuocan.quizapp.DAO.QuestionDAO;
import com.phuocan.quizapp.DAO.QuizDAO;
import com.phuocan.quizapp.Model.Question;
import com.phuocan.quizapp.Model.QuestionAnswer;
import com.phuocan.quizapp.Model.QuestionWrapper;
import com.phuocan.quizapp.Model.Quiz;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDAO;
    @Autowired
    QuestionDAO questionDAO;
    Logger logger = LoggerFactory.getLogger(QuizService.class);;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> questions = questionDAO.findRandomQuestionByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizDAO.save(quiz);
            return new ResponseEntity<>("Success!", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to create a quiz!!!", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        try {
            Optional<Quiz> quiz = quizDAO.findById(quizId);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for (Question q : questionsFromDB) {
                questionsForUser.add(new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()));
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<Integer> calculateResult(Integer quizId, List<QuestionAnswer> questionAnswers) {
        try {
            Optional<Quiz> quiz  = quizDAO.findById(quizId);
            if (quiz.isEmpty()) {
                return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            List<Question> questions =quiz.get().getQuestions(); //The questions from db
            int i = 0, right = 0;
            for (QuestionAnswer qa : questionAnswers) {
                if (qa.getQuestionAnswer().equals(questions.get(i).getRightAnswer())) {
                    right++;
                }
                i++;
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(-1, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<String> deleteQuiz(Integer quizId) {
        try {
            if (quizDAO.findById(quizId).isEmpty()) {
                logger.info("Unable to find quiz Id");
                return new ResponseEntity<>("Unable to find quiz Id", HttpStatus.BAD_REQUEST);
            }
            quizDAO.deleteById(quizId);
            return new ResponseEntity<>("Delete quiz successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting quiz Id: " + quizId, e);
            return new ResponseEntity<>("An error occurred while delete the quiz!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> updateQuiz(Integer quizId, List<Question> questions) {
        try {
            if (quizDAO.findById(quizId).isEmpty()) {
                logger.info("Unable to find quiz Id");
                return new ResponseEntity<>("Unable to find quiz Id", HttpStatus.BAD_REQUEST);
            }

            Quiz quiz = quizDAO.findById(quizId).get();

            for (Question q : questions) {
                quiz.getQuestions().add(q);
            }

            quizDAO.save(quiz);
            return new ResponseEntity<>("Update quiz successfully!", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while updating quiz Id: " + quizId, e);
            return new ResponseEntity<>("An error occurred while updating the quiz!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> removeQuestionFromQuiz(Integer quizId, List<Integer> questions) {
        // The list of question is the id for the questions need to be removed.
        try {
            if (quizDAO.findById(quizId).isEmpty()) {
                logger.info("Unable to find quiz Id");
                return new ResponseEntity<>("Unable to find quiz Id", HttpStatus.BAD_REQUEST);
            }
            Quiz quiz = quizDAO.findById(quizId).get();

            for (Integer i : questions) {
                quiz.getQuestions().removeIf(question -> question.getId().equals(i));
            }
            quizDAO.save(quiz);
            return new ResponseEntity<>("Done deleting questions from quiz", HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while deleting question from quiz Id: " + quizId, e);
            return new ResponseEntity<>("An error occurred while deleting questions!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
