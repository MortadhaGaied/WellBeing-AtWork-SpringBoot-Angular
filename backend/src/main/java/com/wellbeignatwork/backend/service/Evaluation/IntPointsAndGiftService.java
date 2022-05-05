package com.wellbeignatwork.backend.service.Evaluation;


import com.wellbeignatwork.backend.entity.User.Userr;

import java.util.List;

public interface IntPointsAndGiftService {

     void save();
     int CollectPoints(Long idUser);
     int UserGift(Long idUser);
     long random(List<String> pp);
     void UserBadge(Long idUser);
     Iterable<Userr> PointRanking();




}
