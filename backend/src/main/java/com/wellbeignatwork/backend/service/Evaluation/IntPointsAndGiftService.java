package com.wellbeignatwork.backend.service.Evaluation;


import java.util.List;

public interface IntPointsAndGiftService {

     void save();
     int CollectPoints(Long idUser);
     int UserGift(Long idUser);
     long random(List<String> pp);
     void UserBadge(Long idUser);
     Iterable<User> PointRanking();




}
