package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;

import java.util.List;

public interface OpinionService {
    public void AddOpinion(Opinion opinion);
    public List<Opinion> getAllOpinions();
    public void DeleteOpinion(Long id);
    public void UpdateOpinion(Opinion opinion);
    public void assignOpinionToUser(Long idOpenion, Long idUser);
}
