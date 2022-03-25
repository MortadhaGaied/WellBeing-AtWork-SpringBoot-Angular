package com.wellbeignatwork.backend.Service;

import tn.pidev.spring.version0backend.Entity.Sujet;
import tn.pidev.spring.version0backend.Entity.VoteIdea;

import java.util.List;

public interface IntVoteService {
     int AddYes(VoteIdea v, int sujetId, int userId);
     int AddNo(VoteIdea v, int sujetId, int userId);
     VoteIdea getVote(int sujetId, int userId);
     void addSujet(Sujet sujet);
     String VerifYourChoice(int userId, int sujetId);
     int countYes(int sujetId);
     int countNo(int sujetId);
     List<String> findNomdesUsersVoter(int sujetId);
     void deletevoteById(int sujetId, int userId);
     void UpdateYes(int sujetId, int userId);
     void UpdateNo(int sujetId, int userId);
     Boolean verificationvote(int sujetId, int userId);

}
