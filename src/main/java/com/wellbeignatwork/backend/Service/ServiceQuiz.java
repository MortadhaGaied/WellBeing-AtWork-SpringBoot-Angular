package com.wellbeignatwork.backend.Service;


import com.wellbeignatwork.backend.Repository.*;
import com.wellbeignatwork.backend.ServiceImp.IServicesQuiz;
import com.wellbeignatwork.backend.model.Question;
import com.wellbeignatwork.backend.model.QuestionTheme;
import com.wellbeignatwork.backend.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class ServiceQuiz implements IServicesQuiz {

    @Autowired
    private UserRepository iUserRepo;
    @Autowired
    private ITestRepo iTestRepo;
    @Autowired
    private IQuestionRepo iQuestionRepo;
    @Autowired
    private IQuizRepo iQuizRepo;
    @Autowired
    private IResultRepo iResultRepo;
    @Autowired
    Question question;
    @Autowired
    QuestionTheme qForm;
    @Autowired
    Result result;
    @Autowired
    private SendEmailService emailSenderService;




    @Override
    public void addQuiz(QuizCourses quiz, Integer idF) {
        Formation formation = this.iFormationRepo.findById(idF).orElse(null);
        quiz.setFormation(formation);
        quiz.setCreateAt(new Date());
        iQuizRepo.save(quiz);
    }

    @Override
    public void addQuestionAndAsigntoQuiz(Question question, Integer idQuiz) {
    QuizCourses quiz = iQuizRepo.findById(idQuiz).orElse(null);

    question.setQuiz(quiz);

    iQuestionRepo.save(question);
    }







    @Override
    public List<Question> getQuizQuestion(Integer idQuiz ) {
        List<Question> allQues =  iQuizRepo.getQuizQuestion(idQuiz);
        List<Question> qList = new ArrayList<>();

        Random random = new Random();

        for(int i=0; i<5; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        return qList;

    }



    public List<Question> getQuestions() {
        List<Question> allQues = (List<Question>) iQuestionRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random random = new Random();

        for(int i=0; i<5; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        return qList;
    }

    public int getResult(QuestionTheme qForm) {
        int correct = 0;

        for(Question q: qForm.getQuestions())
            if(q.getAns() == q.getChose())
                correct++;

        return correct;
    }

    @Override
    public Integer saveScore(Result result, Long idUser, Integer idQuiz ) {
        Result saveResult = new Result();

        User user = this.iUserRepo.findById(idUser).orElse(null);
        QuizCourses quiz = this.iQuizRepo.findById(idQuiz).orElse(null);
        if (iResultRepo.findUserQuiz(idUser,idQuiz) == 0)
        {
            saveResult.setSUser(user);
            saveResult.setQuiz(quiz);

            saveResult.setUsername(result.getUsername());
            saveResult.setTotalCorrect(result.getTotalCorrect());
            saveResult.setCorrectAnswer(result.getCorrectAnswer());
            saveResult.setInCorrectAnswer(result.getInCorrectAnswer());
            iResultRepo.save(saveResult);
            return 1;

        }else{
            log.info("This user is tested with this quiz");
            return 0;
        }

    }

    @Override
    public User ApprenentwithMaxScoreInFormation(Integer id) {
        return this.iUserRepo.ApprenentwithMaxScoreInFormation(id);
    }

    @Override
    public Object ApprenentwithMaxScore(Integer id) {
        Formation f = iFormationRepo.findById(id).orElse(null);

        return  iUserRepo.getApprenantWithScoreQuiz(id).get(0);


    }

    @Override
    public User ApprenentwithMaxScoreQuiz(Integer id) {

        return iUserRepo.getApprenantWithScoreForGifts(id).get(0);
    }

    @Override
    @Scheduled(cron = "0 0/1 * * * *")
   // @Scheduled(cron = "0 0 20 ? * *") //every day 20:00
    public void giftsToUserMaxScoreInCourses() {
        User user = new User();

        Date date = new Date();
        boolean status=false;

        for(Formation form : iFormationRepo.findAll())
        {
            if(iUserRepo.getApprenantWithScoreForGifts(form.getIdFormation()).size()!=0 )
            {
                user = iUserRepo.getApprenantWithScoreForGifts(form.getIdFormation()).get(0);

                Date tomorrow = new Date(form.getEnd().getTime() + (1000 * 60 * 60 * 48));
                log.info("Date : "+tomorrow);
                if (date.after(form.getEnd()) && date.before(tomorrow) && iResultRepo.getNbrQuiz(user.getId()) == 5)
                {
                    status=true;
                }

                if (status)
                {
                    this.emailSenderService.sendSimpleEmail(user.getEmail(), " we have max Score in courses   ", "Congratulations Mr's : " + user.getLastName() + "--" + user.getFirstName() + " We have Courses free and access in all domain Formation Id : "+ form.getIdFormation() + " .");
                    status=false;
                }
            }

        }

    }

    @Override
    public Integer MaxScoreInFormation() {
        return this.iUserRepo.MaxScoreInFormation();
    }

    @Override
    public List<Object> getApprenantWithScoreQuiz(Integer id) {
        return this.iUserRepo.getApprenantWithScoreQuiz(id);
    }
    @Override
     public List<Result> getTopScore() {
        List<Result> sList = (List<Result>) iResultRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));

        return sList;
   }

    @Override
    public Integer getScore(Long idU) {
        return iResultRepo.getScore(idU);
    }

    @Override
    public List<QuizCourses> getQuizByFormation(Integer idF) {
        return this.iQuizRepo.getQuizByCourses(idF);
    }

    @Override

    public void DeleteQuiz(Integer idQ) {
        this.iQuizRepo.deleteById(idQ);
    }




}
