package com.wellbeignatwork.backend.service.Evaluation;


import com.wellbeignatwork.backend.entity.Evaluation.*;
import com.wellbeignatwork.backend.entity.User;
import com.wellbeignatwork.backend.repository.Evaluation.AnswerRepo;
import com.wellbeignatwork.backend.repository.Evaluation.IntAdviceRepo;
import com.wellbeignatwork.backend.repository.UserRepository;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.apache.logging.log4j.LogManager.getLogger;


@Service
public class QVTService implements IntQVTService {

    @Autowired
    private UserRepository MyUserRepo;
    @Autowired
    private IntAdviceRepo MyAdviceRepo;
    @Autowired
    private AnswerRepo MyAnswerRepo ;

    public QVTService() throws IOException {
    }


    @Override
    public User addUser(User u) {

        u.setBadge(Badge.None);
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

    private static List<Survey> surveys1 = new ArrayList<>();

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

        Survey survey = new Survey("2", "Evaluate Your responsible",
                "Description of the Survey", questions);

        surveys1.add(survey);
    }

    @Autowired
    private AnswerRepo answerRepo;


    @Override
    public List<Survey> retrieveAllSurveys() {
        return surveys;
    }



    @Override
    public List<Survey> retrieveAllSurveys1() {
        return surveys1;
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
        String Res = "";
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getPipeline();
        for (Answer answer1 : answer) {
            String text = answer1.getContent();
            CoreDocument coreDocument = new CoreDocument(text);
            stanfordCoreNLP.annotate(coreDocument);
            List<CoreSentence> sentences = coreDocument.sentences();
            for (CoreSentence sentence : sentences) {
                String sentiment = sentence.sentiment();
                System.out.println(sentiment + "\t" + sentence);
                if (!sentiment.contentEquals("Very negative")) {
                    answer1.setSentiment(Sentiment.valueOf(sentiment));
                } else {
                    answer1.setSentiment(Sentiment.Very_negative);
                }
                answerRepo.save(answer1);
            }
        }
        if (answerRepo.nbreByStatus(Sentiment.Positive) > (answerRepo.nbreByStatus(Sentiment.Very_negative) | (answerRepo.nbreByStatus(Sentiment.Negative)))) {
            System.out.println
                    ("Exellent ! Your Work Life Is Very Positive,i wish you much success in your carreer");


         //   return Res = "Exellent ! Your Work Life Is Very Positive,i wish you much success in your carreer";
        } else if (answerRepo.nbreByStatus(Sentiment.Positive) == 3 && ((answerRepo.nbreByStatus(Sentiment.Very_negative) == (answerRepo.nbreByStatus(Sentiment.Negative))))) {
            System.out.println("Your Survey Is Positive :),You Have Some Issues Don't Wory We Will Fix That ");

           // return Res = "Your Survey Is Positive :),You Have Some Issues Don't Wory We Will Fix That ";
        } else if (answerRepo.nbreByStatus(Sentiment.Very_negative) == 5 | (answerRepo.nbreByStatus(Sentiment.Negative) == 5) | answerRepo.nbreByStatus(Sentiment.Very_negative) == 5 && (answerRepo.nbreByStatus(Sentiment.Negative) == 5)) {
            System.out.println("Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ");


          //  return Res = "Very Negative ! Thank You For Your FeedBack We Will Take This In Hand ";

        } else {
            System.out.println("Neutral ! Thank You For Your FeedBack Enjoy Your Time ");
         //   return Res = "Neutral ! Thank You For Your FeedBack Enjoy Your Time ";
        }


    }






    @Override
    public String nbreSentiment( ) {
        String Res = "";

      /*  log.info("Total Sentiment Positive = "+answerRepo.nbreByStatus(Sentiment.Positive));

        log.info("Total Sentiment Negative = "+answerRepo.nbreByStatus(Sentiment.Negative));

        log.info("Total Sentiment Neutral = "+answerRepo.nbreByStatus(Sentiment.Neutral));
        log.info("Total Sentiment Very negative = "+answerRepo.nbreByStatus(Sentiment.Very_negative));

       */
            return Res = "Positive Sentiment = " + " " + answerRepo.nbreByStatus(Sentiment.Positive) + " " +
                    "Negative Sentiment = " + " " + answerRepo.nbreByStatus(Sentiment.Negative) + " " +
                    " Neutral Sentiment = " + " " + answerRepo.nbreByStatus(Sentiment.Neutral) + " " +
                    " Very negative Sentiment = " + " " + answerRepo.nbreByStatus(Sentiment.Very_negative);


    }




    @Override
    public ByteArrayInputStream load() {
        List<Answer> answers = (List<Answer>) MyAnswerRepo.findAll();

       ByteArrayInputStream in = CsvHelper.ToCSV(answers);
        return in;
    }


















    }














































