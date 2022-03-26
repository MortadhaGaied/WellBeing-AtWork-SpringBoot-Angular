package com.wellbeignatwork.backend.service;

import com.wellbeignatwork.backend.entity.Reaction;

public interface ReactionService {
    public void addReactToPost(Reaction reaction,int idPost,int idUser);
    public void deleteReaction(int idReaction);
}
