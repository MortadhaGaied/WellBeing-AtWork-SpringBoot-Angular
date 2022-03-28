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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;


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
    public List<User> afficherFormateur() {

       return iUserRepo.getFormateur();
    }

    @Override
    public List<User> afficherApprenant() {
        return iUserRepo.getApprenant();
    }

    @Override
    public User FormateurwithMaxHo() {
        return iUserRepo.FormateurwithMaxHo();
    }


    @Override
    @Scheduled(cron = "0 0/20 * * * *")
  //  @Scheduled(cron = "0 0 9 28 * ?")
    public User getFormateurRemunerationMaxSalaire() {


        int max = 0;

        TreeMap<Integer, String> map = new TreeMap<>();

        User u = new User();

        LocalDate currentdDate1 = LocalDate.now();
        Date date = new Date();

        boolean status = false;


        Calendar calLast = Calendar.getInstance();
        Calendar calFirst = Calendar.getInstance();
        calLast.set(Calendar.DATE, calLast.getActualMaximum(Calendar.DATE));
        calFirst.set(Calendar.DATE, calFirst.getActualMinimum(Calendar.DATE));

        Date lastDayOfMonth = calLast.getTime();
        Date firstDayOfMonth = calFirst.getTime();


        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd = Date.from(currentdDate1.minusDays(15).atStartOfDay(defaultZoneId).toInstant());
        Date df = Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());


        log.info("start : " + firstDayOfMonth);
        log.info("last : " + lastDayOfMonth);

        if (firstDayOfMonth.equals(firstDayOfMonth)) {
            status = true;
        }


      //   if(lastDayOfMonth.equals(lastDayOfMonth))
       //  {
        for (Test f : this.iTestRepo.findAll()) {
            if (f.getStart().after(firstDayOfMonth) && f.getEnd().before(lastDayOfMonth)) {

                map.put(this.iTestRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth), f.getFormateur().getId().toString());
                if (this.iTestRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth) > max) {
                    max = this.iTestRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth);
                }
            }
        }

        if (status) {


            for (Test f : this.iTestRepo.findAll()) {

                for (User user : iUserRepo.getFormateurByTest(f.getIdTest())) {
                    user.setSalary(this.iTestRepo.getFormateurRemunerationByDate(user.getId(), firstDayOfMonth, lastDayOfMonth));
                    iUserRepo.save(user);
                }
            }
            log.info(" liste" + map);
            log.info(" Max Salaire " + max);

            for (Test f : this.iTestRepo.findAll()) {
                if (f.getStart().after(firstDayOfMonth) && f.getEnd().before(lastDayOfMonth)) {
                    if (this.iTestRepo.getFormateurRemunerationByDate(f.getFormateur().getId(), firstDayOfMonth, lastDayOfMonth) == max) {

                        u = this.iUserRepo.findById(f.getFormateur().getId()).orElse(null);
                    }
                }
            }

            u.setSalary(max + 200 );
            iUserRepo.save(u);
            this.emailSenderService.sendSimpleEmail(u.getEmail(), "we have max houre of travel ", "we have max houre of travel we elevate salary with 200 $  : " + u.getSalary()+ "$  Name " + u.getName() + " . ");
            return u;
        }


     // }
      return null;

    }


    public TreeMap<Integer, User> getFormateurRemunerationMaxSalaireTrie() {

        TreeMap<Integer, User> map = new TreeMap<>();



        LocalDate currentdDate1 =  LocalDate.now();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusDays(15).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());

        for (Test f: this.iTestRepo.findAll()) {
            if (f.getStart().after(dd) && f.getEnd().before(df) )
            {
                map.put(this.iTestRepo.getFormateurRemunerationByDate(f.getFormateur().getId(),dd,df),f.getFormateur());

            }

        }
      //  List<Map.Entry<Integer, User>> singleList = map.entrySet().stream().collect(Collectors.toList());
        return map;
    }

    @Override
    public List<Object> getFormateurRemunerationByDateTrie() {
        LocalDate currentdDate1 =  LocalDate.now();



        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusDays(15).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusDays(15).atStartOfDay(defaultZoneId).toInstant());

        return this.iTestRepo.getFormateurRemunerationByDateTrie(dd,df);
    }

    @Override
   // @Scheduled(cron = "0 0/1 * * * *")
    public void CertifactionStudents() {

        boolean status = false;
        boolean fin;


        try {


            for (Test f : iTestRepo.findAll())
            {
                for (User u : iUserRepo.getApprenantByFormation(f.getIdTest()))
                {
                    if(iResultRepo.getScore(u.getId()) >= 200 && iResultRepo.getScore(u.getId()) <=250 && iResultRepo.getNbrQuiz(u.getId()) == 5 )
                    {
                        fin=false;

                        for (Result r : iResultRepo.getResultByIdUAndAndIdF(u.getId(),f.getIdTest()))
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
    public List<Test> SearchMultiple(String key) {

        if (key.equals(""))
        {
            return (List<Test>) iTestRepo.findAll();
        }else
        {
            return iTestRepo.rech(key);
        }

    }



    @Override
    public void ajouterApprenant(User apprenant) {
            iUserRepo.save(apprenant);
    }

    @Override
    public void ajouterEtAffecterFormationAFormateur(Test formation, Long idFormateur) {

        User formateur = iUserRepo.findById(idFormateur).orElse(null);

        LocalDate currentdDate1 =  LocalDate.now();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusMonths(3).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusMonths(3).atStartOfDay(defaultZoneId).toInstant());

            if (this.iTestRepo.nbrCoursesParFormateur(idFormateur,dd,df) <2 && formateur.getProfession() == Profession.FORMER.FORMER)
            {
                formation.setLikes(0);
                formation.setDislikes(0);
                formation.setFormateur(formateur);
                iTestRepo.save(formation);
            }else
            {
                this.emailSenderService.sendSimpleEmail(formateur.getEmail(),"we don't have acces to have two coursus in same semester " ,"we have 2 (MAX formation in this semester) NAME : "+formateur.getName() +" .");
                log.info("we have 2 (MAX formation in this Semester ");
            }

    }


    public Test getFile(Integer fileId) throws FileNotFoundException {
        return iTestRepo.findById(fileId).orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }


    @Override
   // @Scheduled(cron = "*/30 * * * * *")
    public void affecterApprenantFormationWithMax(Long idApprenant, Integer idFormation) {

        Test formation = iTestRepo.findById(idFormation).orElse(null);

        User apprenant = iUserRepo.findById(idApprenant).orElse(null);

        LocalDate currentdDate1 =  LocalDate.now();
        User user = new User();

        ZoneId defaultZoneId = ZoneId.systemDefault();

        Date dd =Date.from(currentdDate1.minusMonths(3).atStartOfDay(defaultZoneId).toInstant());
        Date df =Date.from(currentdDate1.plusMonths(3).atStartOfDay(defaultZoneId).toInstant());

        ///User with gifts Free for MAx Score


       for(Test form : iTestRepo.listFormationParApprenant(idApprenant)) {
          if(iUserRepo.getApprenantWithScoreForGifts(form.getIdTest()).size()!=0)
           {
               user = iUserRepo.getApprenantWithScoreForGifts(form.getIdTest()).get(0);
                //}


                if (iTestRepo.getNbrApprenantByFormationId(idFormation) < formation.getNbrMaxParticipant() && apprenant.getProfession() == Profession.LEARNER) {

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
/*
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail()
    {
        emailSenderService.sendEmail("mahdijr2015@gmail.com","we don't add two coursus in same domain " ,"this apprenant we have 2 (MAX formation in this domain");
    }


 */
    ///////////////  Affectation 3adiya  ////////////////////
    @Override
    public void affecterApprenantFormation(Long idApprenant, Integer idFormation) {
        User apprenant = iUserRepo.findById(idApprenant).orElse(null);
        Test formation = iTestRepo.findById(idFormation).orElse(null);

        formation.getApprenant().add(apprenant);
        iTestRepo.save(formation);
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
