package com.wellbeignatwork.backend.Service;


import com.google.zxing.WriterException;
import com.wellbeignatwork.backend.API.QRCodeGenerator;
import com.wellbeignatwork.backend.Repository.IResultRepo;
import com.wellbeignatwork.backend.Repository.ISearchRepo;
import com.wellbeignatwork.backend.Repository.ITestRepo;
import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.ServiceImp.IServiceTest;
import com.wellbeignatwork.backend.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class ServiceTest implements IServiceTest {

    @Autowired
    private UserRepository iUserRepo;

    @Autowired
    private ITestRepo iTestRepo;
    @Autowired
    private IResultRepo iResultRepo;
    @Autowired
    private ISearchRepo iSearchRepo;


    @Autowired
    private SendEmailService emailSenderService;

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/static/img/QRCode.png";



    @Override
    public void addTest(Test test) {
        iTestRepo.save(test);
    }

    @Override
    public void updateTest(Test test, Integer idTest) {
        Test f = iTestRepo.findById(idTest).orElse(null);

        f.setTitle(test.getTitle());
        f.setDomain(test.getDomain());
        f.setNbrHeures(test.getNbrHeures());
        f.setNbrParticipant(test.getNbrParticipant());
        //  formation.setFormateur(formateur);
        iTestRepo.save(f);
    }

    @Override
    public void deleteTest(Integer idTest) {
        Test test = iTestRepo.findById(idTest).orElse(null);
        iTestRepo.delete(test);
    }


    @Override
    public List<Test> afficherTest() {
        return (List<Test>) iTestRepo.findAll();
   }

    @Override
    public List<User> afficherEmployee() {
        return iUserRepo.getEmployee();
    }



    @Override
   // @Scheduled(cron = "0 0/1 * * * *")
    public void CertifactionEmployee() {

        boolean status = false;
        boolean fin;


        try {
            for (Test f : iTestRepo.findAll())
            {
                for (User u : iUserRepo.getEmployeeByTest(f.getIdTest()))
                {
                    if(iResultRepo.getScore(u.getId()) >= 1000 && iResultRepo.getScore(u.getId()) <=1500 && iResultRepo.getNbrQuiz(u.getId()) == 15 )
                    {
                        fin=false;

                        for (Result r : iResultRepo.getResultByIdUserAndAndIdTest(u.getId(),f.getIdTest()))
                        {

                            if (!r.isStatus())
                            {

                                r.setStatus(true);
                                iResultRepo.save(r);
                                status=true;
                            }

                            if (status && !fin)
                            {
                                log.info( " Status  true ");
                                QRCodeGenerator.generateQRCodeImage(f.getDomain().toString(),150,150,QR_CODE_IMAGE_PATH);
                                this.emailSenderService.sendSimpleEmail(u.getEmail()," Congratulations Mr's : "+u.getName()+" you have finished your Courses  " ," Certification At : "+ new Date()+"  in Courses of Domain "+f.getDomain()+" "+" And Niveau : " +f.getLevel() +" .");
                                fin=true; /// return /////
                            }


                        }
                    }

                }

            }

        } catch (WriterException | IOException e) {

            e.printStackTrace();
        }



    }

    @Override
   // @Scheduled(cron = "*/30 * * * * *")
    public void affecterEmployeeWithMaxTest(Long idUser, Integer idTest) {

        Test test = iTestRepo.findById(idTest).orElse(null);
        User employee = iUserRepo.findById(idUser).orElse(null);
        User user = new User();

        ///User with gifts Free for MAx Score


       for(Test form : iTestRepo.listEmployeeParTest(idUser)) {
          if(iUserRepo.getEmployeeWithScoreForGifts(form.getIdTest()).size()!=0)
           {
               user = iUserRepo.getEmployeeWithScoreForGifts(form.getIdTest()).get(0);
                //}


                if (iTestRepo.getNbrEmployeeByTestId(idTest) < test.getNbrParticipant() && employee.getProfession() == Profession.EMPLOYEE) {

                    if (iTestRepo.getNbrEmployeeByTest(idTest, test.getDomain()) < 2 || employee.getId().equals(user.getId())) {
                        if (iTestRepo.getNbrEmployeeByTest(Math.toIntExact(idUser), test.getDomain()) < 3) {

                            log.info("nbr "+iTestRepo.getNbrEmployeeByTest(idTest, test.getDomain()));
                            test.getEmployee().add(employee);
                            iTestRepo.save(test);
                        } else {
                            log.info("this apprenant we have 3 (MAX formation in this domain ");
                            this.emailSenderService.sendSimpleEmail(employee.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + employee.getName() + " .");
                        }

                    } else {
                        this.emailSenderService.sendSimpleEmail(employee.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + employee.getName() + " .");
                        log.info("this apprenant we have 2 (MAX formation in this domain ");
                    }
                } else {
                    this.emailSenderService.sendSimpleEmail(employee.getEmail(), "Learner list complete  ", " We have in this courses " + test.getNbrParticipant() + " number of learner Maximum" + employee.getName() + " .");
                    log.info(" Learner list complete Max learner " + test.getNbrParticipant());
                }
            }


    }

    }

    @Override
    public void AffecterEmployeeATest(Long idUser, Integer idTest) {
        User user = iUserRepo.findById(idUser).orElse(null);
        Test test = iTestRepo.findById(idTest).orElse(null);

        test.getEmployee().add(user);
        iTestRepo.save(test);
    }


    @Override
    public Integer getNbrEmployeeByTestt(String title) {
        return  iTestRepo.getNbrEmployeeByTestt(title);
    }





    @Override
    public void likeTest(Integer idF) {
        Test test = iTestRepo.findById(idF).orElse(null);

        test.setLikes(test.getLikes()+1);
        iTestRepo.save(test);


    }

    @Override
    public void dislikeTest(Integer idF) {
        Test test = iTestRepo.findById(idF).orElse(null);

        test.setDislikes(test.getDislikes()+1);
        iTestRepo.save(test);

    }



}
