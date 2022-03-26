package com.wellbeignatwork.backend.service;




import com.wellbeignatwork.backend.entity.Answer;
import com.wellbeignatwork.backend.entity.Survey;
import com.wellbeignatwork.backend.entity.User;

import java.util.List;

public interface IntQVTService {
     User addUser(User u);
     List<Survey> retrieveAllSurveys();
     String UserAnswer(List<Answer> answer);
     String nbreSentiment();


















}
