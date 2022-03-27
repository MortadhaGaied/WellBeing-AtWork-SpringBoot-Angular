package com.wellbeignatwork.backend.service.Evaluation;




import com.wellbeignatwork.backend.entity.Evaluation.Answer;
import com.wellbeignatwork.backend.entity.Evaluation.Survey;
import com.wellbeignatwork.backend.entity.User;

import java.io.ByteArrayInputStream;
import java.io.Writer;
import java.util.List;

public interface IntQVTService {
     User addUser(User u);
     List<Survey> retrieveAllSurveys();
     void UserAnswer(List<Answer> answer);
     String nbreSentiment();
     public ByteArrayInputStream load();
     public List<Survey> retrieveAllSurveys1();




















}
