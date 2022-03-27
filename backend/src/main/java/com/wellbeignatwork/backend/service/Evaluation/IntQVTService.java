package com.wellbeignatwork.backend.service.Evaluation;




import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import com.wellbeignatwork.backend.entity.Evaluation.Survey;
import com.wellbeignatwork.backend.entity.Evaluation.User;

import java.util.List;

public interface IntQVTService {
     User addUser(User u);
     List<Survey> retrieveAllSurveys();
     String UserAnswer(List<Answer> answer);
     String nbreSentiment();


















}
