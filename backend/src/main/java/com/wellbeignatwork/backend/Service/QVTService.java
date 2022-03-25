package com.wellbeignatwork.backend.Service;


import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.pidev.spring.version0backend.Entity.*;
import tn.pidev.spring.version0backend.Repository.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class QVTService implements IntQVTService {

    @Autowired
    private IntUserRepo MyUserRepo;
    @Autowired
    private IntAdviceRepo MyAdviceRepo;



    @Override
    public User addUser(User u) {
        return MyUserRepo.save(u);
    }


    private static List<Survey> surveys = new ArrayList<>();
    static {
        Question question1 = new Question(1,
                "you feel physicaly safe in job ?");
        Question question2 = new Question(2,
                "your job provides good health benefits ?");
        Question question3 = new Question(3,
                "your salary job does well with your familly needs ?");
        Question question4 = new Question(4,
                "Any suggestions ?");
        Question question5 = new Question(5,
                "Any suggestions ?");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1,
                question2, question3, question4));

        Survey survey = new Survey("1", "quality of life at work",
                "Description of the Survey", questions);

        surveys.add(survey);
    }


    @Autowired
    private AnswerRepo answerRepo;



    @Override
    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }





    /*
    @Override
    public void AddAndAffectSurveyUser( )
    {
        List<User> u=new ArrayList<>();
        for(User user:MyUserRepo.findAll())
        {
            if(user.getRole().equals(s.getRole()))
            {

                user.getSurveys().add(s);
                if(s.getUsers()==null){
                    u.add(user);
                    s.setUsers(u);
                }
                else{
                    s.getUsers().add(user);
                }
                MyUserRepo.save(user);
                MySurveyRepo.save(s);
            }
        }
    }


     */














    @Override
    public void UserAnswer(List<Answer> answer) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        for(Answer answer1:answer)
        {
            String text=answer1.getContent();
            CoreDocument coreDocument = new CoreDocument(text);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreSentence> sentences = coreDocument.sentences();
            for(CoreSentence sentence : sentences) {
                String sentiment = sentence.sentiment();
                System.out.println(sentiment + "\t" + sentence);
                if(!sentiment.contentEquals("Very negative"))
                {
                    answer1.setSentiment(Sentiment.valueOf(sentiment));
                }
                else
                {
                    answer1.setSentiment(Sentiment.Very_negative);
                }
                answerRepo.save(answer1);
            }
        }
        if(answerRepo.nbreByStatus(Sentiment.Positive) > (answerRepo.nbreByStatus(Sentiment.Very_negative)|(answerRepo.nbreByStatus(Sentiment.Negative))))
        {
            System.out.println
                    ("Exellent ! Your Work Life Is Very Positive,i wish you much success in your carreer" );
        }

        else if(answerRepo.nbreByStatus(Sentiment.Positive)==3 && ((answerRepo.nbreByStatus(Sentiment.Very_negative)==(answerRepo.nbreByStatus(Sentiment.Negative))   )))
        {
            System.out.println("Your Survey Is Positive :),You Have Some Issues Don't Wory We Will Fix That ");
        }
        else if(answerRepo.nbreByStatus(Sentiment.Very_negative)==5 | (answerRepo.nbreByStatus(Sentiment.Negative)==5)| answerRepo.nbreByStatus(Sentiment.Very_negative)==5 && (answerRepo.nbreByStatus(Sentiment.Negative)==5))
        {
            System.out.println("Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ");
        }

        else
            System.out.println("Neutral ! Thank You For Your FeedBack Enjoy Your Time ");

    }


    //this stat is only for Admin And Responsble
    public void nbreSentiment(){

        log.info("Total Sentiment Positive = "+answerRepo.nbreByStatus(Sentiment.Positive));
        log.info("Total Sentiment Negative = "+answerRepo.nbreByStatus(Sentiment.Negative));
        log.info("Total Sentiment Neutral = "+answerRepo.nbreByStatus(Sentiment.Neutral));
        log.info("Total Sentiment Very negative = "+answerRepo.nbreByStatus(Sentiment.Very_negative));

    }

















}
