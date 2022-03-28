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
import java.time.ZoneId;
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
    public List<Test> SearchMultiple(String key) {
        return null;
    }

    @Override
    public Integer getNbrEmployeeByTest(String title) {
        return null;
    }

    @Override
    public Integer getNbrTestByEmployee(Long idApp, Domain domain, Date dateDebut, Date dateFin) {
        return null;
    }

    @Override
    public List<User> getEmployeeByTest(Integer idF) {
        return null;
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
    public void affecterEmployeeWithMaxTest(Long idApprenant, Integer idFormation) {

        Test formation = iTestRepo.findById(idFormation).orElse(null);

        User apprenant = iUserRepo.findById(idApprenant).orElse(null);

        LocalDate currentdDate1 =  LocalDate.now();
        User user = new User();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusMonths(3).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusMonths(3).atStartOfDay(defaultZoneId).toInstant());

        ///User with gifts Free for MAx Score


       for(Test form : iTestRepo.listEmployeeParTest(idApprenant)) {
          if(iUserRepo.getEmployeeWithScoreForGifts(form.getIdTest()).size()!=0)
           {
               user = iUserRepo.getEmployeeWithScoreForGifts(form.getIdTest()).get(0);
                //}


                if (iTestRepo.getNbrApprenantByTestId(idFormation) < formation.getNbrMaxParticipant() && apprenant.getProfession() == Profession.LEARNER) {

                    if (iTestRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df) < 2 || apprenant.getId().equals(user.getId())) {
                        if (iTestRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df) < 3) {

                            log.info("nbr "+iTestRepo.getNbrFormationByApprenant(idApprenant, formation.getDomain(), dd, df));
                            formation.getApprenant().add(apprenant);
                            iTestRepo.save(formation);
                        } else {
                            log.info("this apprenant we have 3 (MAX formation in this domain ");
                            this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + apprenant.getName() + " .");
                        }

                    } else {
                        this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "we don't have acces to add two coursus in same domain ", "we have 2 (MAX formation in this domain) NAME : " + apprenant.getName() + " .");
                        log.info("this apprenant we have 2 (MAX formation in this domain ");
                    }
                } else {
                    this.emailSenderService.sendSimpleEmail(apprenant.getEmail(), "Learner list complete  ", " We have in this courses " + formation.getNbrMaxParticipant() + " number of learner Maximum" + apprenant.getName() + " .");
                    log.info(" Learner list complete Max learner " + formation.getNbrMaxParticipant());
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
    public Integer nbrCoursesParFormateur(Long idF,Date dateDebut, Date dateFin) {
        return this.iTestRepo.nbrCoursesParFormateur(idF, dateDebut, dateFin);
    }

    @Override
    public Integer getNbrApprenantByFormation(String title) {
        return  iTestRepo.getNbrApprenantByTest(title);
    }


    @Override
   // @Scheduled(cron = "*/30 * * * * *")
    public void getNbrApprenantByFormationn() {

        log.info("La formation : Spring contient : " +iFormationRepo.getNbrApprenantByFormation("Spring") + " Apprenant ");
        log.info("La formation : Devops contient : " +iFormationRepo.getNbrApprenantByFormation("DevOps") + " Apprenant ");

    }

    @Override
    public Integer getNbrFormationByApprenant(Long idApp , Domain domain, Date dateDebut, Date dateFin) {
        return iFormationRepo.getNbrFormationByApprenant(idApp,domain, dateDebut, dateFin);
    }

    @Override
    public List<Object[]> getNbrApprenantByFormation() {

        return iUserRepo.getNbrApprenantByFormation();
    }

    @Override
    public List<User> getApprenantByFormation(Integer idF) {
        return iUserRepo.getApprenantByFormation(idF);
    }

    @Override
    public Integer getFormateurRemunerationByDate(Long idFormateur, Date dateDebut, Date dateFin) {

        return iFormationRepo.getFormateurRemunerationByDate(idFormateur, dateDebut, dateFin);

    }


    @Override
    public Integer getRevenueByFormation(Integer idFormation) {
        Test f = iFormationRepo.findById(idFormation).orElse(null);

        Integer revenue =  (f.getFrais()*iUserRepo.getRevenueByFormation(idFormation).size());
        return  revenue;
    }

    @Override
    public void likeTest(Integer idF) {
        Test test = iTestRepo.findById(idF).orElse(null);

        test.setLikes(test.getLikes()+1);
        iTestRepo.save(test);


    }

    @Override
    public void dislikeTest(Integer idF) {

    }

    @Override
    public void dislikeFormation(Integer idF) {
        Test test = iTestRepo.findById(idF).orElse(null);

        test.setDislikes(test.getDislikes()+1);
        iTestRepo.save(test);

    }




    //////////////// Search historique ////////////////

    @Override
    public void SearchHistorique(String keyword) {

        if(keyword!=null)
        {
            String s = iSearchRepo.keyWord(keyword);
            Search search = new Search();

            search.setKeyword(s);
            search.setAtDate(new Date());

            iSearchRepo.save(search);
        }


    }




}
