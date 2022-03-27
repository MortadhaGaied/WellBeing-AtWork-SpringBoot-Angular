package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Reaction;

public interface ReactionService {
    public void addReactToPost(Reaction reaction,int idPost,Long idUser);
    public void deleteReaction(int idReaction);
}
