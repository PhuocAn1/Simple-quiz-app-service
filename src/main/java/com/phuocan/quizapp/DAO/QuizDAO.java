package com.phuocan.quizapp.DAO;

import com.phuocan.quizapp.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDAO extends JpaRepository<Quiz, Integer> {
}
