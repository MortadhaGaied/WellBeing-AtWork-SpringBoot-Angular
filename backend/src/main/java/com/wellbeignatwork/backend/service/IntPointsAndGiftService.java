package com.wellbeignatwork.backend.service;


import com.wellbeignatwork.backend.entity.User;

import java.util.List;

public interface IntPointsAndGiftService {

     void save();
     int CollectPoints(int idUser);
     int UserGift(int idUser);
     long random(List<String> pp);
     void UserBadge(int idUser);
     Iterable<User> PointRanking();




}
