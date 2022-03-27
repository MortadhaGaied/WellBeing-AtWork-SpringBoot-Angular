package com.wellbeignatwork.backend.service.Evaluation;


import com.wellbeignatwork.backend.entity.Evaluation.User;

import java.util.List;

public interface IntPointsAndGiftService {

     void save();
     int CollectPoints(int idUser);
     int UserGift(int idUser);
     long random(List<String> pp);
     void UserBadge(int idUser);
     Iterable<User> PointRanking();




}
