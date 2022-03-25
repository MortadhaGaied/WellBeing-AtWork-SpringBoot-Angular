package com.wellbeignatwork.backend.Service;




import com.wellbeignatwork.backend.Entity.Answer;
import com.wellbeignatwork.backend.Entity.Survey;
import com.wellbeignatwork.backend.Entity.User;

import java.util.List;

public interface IntQVTService {
    public User addUser(User u);
    public List<Survey> retrieveAllSurveys();
    public String UserAnswer(List<Answer> answer);

















}
