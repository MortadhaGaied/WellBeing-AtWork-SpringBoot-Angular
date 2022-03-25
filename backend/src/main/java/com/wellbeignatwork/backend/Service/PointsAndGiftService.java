package com.wellbeignatwork.backend.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tn.pidev.spring.version0backend.Entity.*;
import tn.pidev.spring.version0backend.Repository.*;

import java.util.List;
import java.util.Random;

@Service
public class PointsAndGiftService implements IntPointsAndGiftService {

    @Autowired
    IntUserRepo intUserRepo;
    @Autowired
    IntEventsRepo intEventsRepo;

    @Autowired
    IntCommentRepo intCommentRepo;
    @Autowired
    intLikesRepo intLikesRepo;
    @Autowired
    IntGiftUserRepo intGiftUserRepo;

    @Override
    public int CollectPoints(int idUser) {
        User user = intUserRepo.findById(idUser).orElse(null);
        int scoreEvent=0;
        int scoreComment=0;
        int scoreLikes=0;
        if(user.getEvents()!=null)
        {
            scoreEvent=user.getEvents().size()*50;

            }

        if(user.getComments()!=null)
        {
            scoreComment=user.getComments().size()*10;

        }
        if(user.getLikes()!=null)
        {
            scoreLikes=user.getLikes().size()*10;

        }
        int total = scoreComment+scoreEvent+scoreLikes;
        user.setPointFidelite(total);
        UserBadge(idUser);
        intUserRepo.save(user);
        return total;
    }

    @Autowired
    public void save ()
    {
     if(intGiftUserRepo.nombreCodeValidee()<10)
        {
            for(int i=0;i<=50;i++)
            {
                UserGift c = new UserGift();
                c.setMontant((int) (Math.random() * (1000 - 0 )));
                c.setCode(generateRandomCODEGift());
                intGiftUserRepo.save(c);
            }

        }
    }


    public String generateRandomCODEGift()
    {
        String characters="AZERTYUIOPQSDFGHJKLMWXCVBNazertyuiopqsdfghjklmwxcvbn";
        String randomString="";

        Random rand =new Random();
        int length=5;

        char[] text = new char[length];

        for (int i=0;i<length;i++)
        {
            text[i]=characters.charAt(rand.nextInt(characters.length()));
        }
        for (int i =0;i<text.length;i++)
        {
            randomString+=text[i];
        }
        return randomString;

    }








    @Override
    public int UserGift(int idUser) {
     User user =intUserRepo.findById(idUser).orElse(null);
     int a = 0;
        float montant =0;
     if (user.getPointFidelite()<=299)
        {
            List<String> x = intGiftUserRepo.Gift300();
            System.out.println("variable x = " + x );
            long random1 =  random(intGiftUserRepo.Gift300());
            System.out.println("randoml = "+ " " + random1);
            if(random1==0)
            {
                System.out.println("Sorry You Are Not In The Game ");

            }
            else
            {
                UserGift cd1 = intGiftUserRepo.findById((int) random1).orElse(null);
                cd1.setIdUser(user);
                if(cd1.getMontant()>user.getPointFidelite())
                {
                    System.out.println("this gift cost= "+cd1.getMontant() +",And You Have Only" +" " +user.getPointFidelite()+"Points" );
                    System.out.println("Sorry Try Again");


                }
                else {
                    user.setPointFidelite((int) (user.getPointFidelite()-cd1.getMontant()));
                    System.out.println("congratulations!");

                }
                UserBadge(idUser);
                intUserRepo.save(user);
                intGiftUserRepo.save(cd1);
               // a= cd1.getCode();
                a=cd1.getId();
                montant =cd1.getMontant();
                System.out.println("Montant" + montant );
            }

        }

         else
            if ((user.getPointFidelite()>=300)&&(user.getPointFidelite()<=499))
        {
            long random1 =  random(intGiftUserRepo.Gift500());
            if(random1==0)
            {
                System.out.println("Sorry You Are Not In The Game ");

            }
            else
            {
                UserGift cd2 = intGiftUserRepo.findById((int) random1).orElse(null);
                cd2.setIdUser(user);
                if(cd2.getMontant()>user.getPointFidelite())
                {
                    System.out.println("this gift cost= "+cd2.getMontant() +",And You Have Only" +" " +user.getPointFidelite()+"Points" );
                    System.out.println("Sorry Try Again");
                }
                else {
                    user.setPointFidelite((int) (user.getPointFidelite()-cd2.getMontant()));
                    System.out.println("congratulations!");
                    UserBadge(idUser);
                    intUserRepo.save(user);
                    intGiftUserRepo.save(cd2);
                }
                a=cd2.getId();
                montant =cd2.getMontant();
                System.out.println("Montant" + montant );
            }
        }



        else
            {
                long random3 =  random(intGiftUserRepo.Gift1000());
                if(random3==0){System.out.println("Sorry You Are Not In The Game ");}
                else
                {
                    UserGift cd3 = intGiftUserRepo.findById((int) random3).orElse(null);

                    cd3.setIdUser(user);

                    if(cd3.getMontant()>user.getPointFidelite()){
                        System.out.println("this gift cost= "+cd3.getMontant() +",And You Have Only" +" " +user.getPointFidelite()+"Points" );
                        System.out.println("Sorry Try Again"); }
                    else {
                        user.setPointFidelite((int) (user.getPointFidelite()-cd3.getMontant()));
                        System.out.println("congratulations!");
                        UserBadge(idUser);
                        intUserRepo.save(user);
                        intGiftUserRepo.save(cd3);

                    }
                    a=cd3.getId();
                    montant =cd3.getMontant();
                    System.out.println("Montant" + montant );

                }
            }

        return a;
    }



    @Override
    public long random(List<String> pp) {

        long random=0;
        String delim2 = ",";
        String res2 = String.join(delim2, pp);
        System.out.println("la variable res2"+" "+res2);
        String motcommentaire2[] = res2.split(",");

        int r2 = (int) (Math.random() * (motcommentaire2.length));
        System.out.println("r2: "+" "+r2);
        String name2 = motcommentaire2[r2];
        System.out.println("name2: "+" "+name2);
        if(name2.equals(""))
            random=0;
        else
            random = Long.parseLong(name2);
        return random;
    }

    @Override
    public void UserBadge(int idUser) {
        User user = intUserRepo.findById(idUser).orElse(null);
        String badge;
        if (user.getPointFidelite()==0)
        {
           user.setBadge(Badge.None);
        }
        else if((user.getPointFidelite()!=0)&&(user.getPointFidelite()<=299))
        {
            user.setBadge(Badge.Bleu);
        }
        else
        if ((user.getPointFidelite()>=300)&&(user.getPointFidelite()<=499))
        {
            user.setBadge(Badge.Golde);
        }
        else user.setBadge(Badge.Platinum);

        intUserRepo.save(user);
    }

    @Override
    public Iterable<User> PointRanking() { return intUserRepo.findAll(Sort.by("pointFidelite").descending()); }


}
