package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.entity.Forum.Reaction;
import com.wellbeignatwork.backend.repository.Forum.PostRepository;
import com.wellbeignatwork.backend.repository.Forum.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionServiceImpl implements ReactionService{
    private ReactionRepository reactionRepository;
    private PostRepository postRepository;
    @Autowired
    public ReactionServiceImpl(ReactionRepository reactionRepository,PostRepository postRepository){
        this.reactionRepository=reactionRepository;
        this.postRepository=postRepository;
    }
    @Override
    public void addReactToPost(Reaction reaction, int idPost, int idUser) {
        boolean isReacted=false;
        Post p=postRepository.findById(idPost).orElse(null);
        reaction.setIdUser(idUser);
        reaction.setPost(p);
        for(Reaction r:reactionRepository.findAll()){
            if(r.getIdUser()==idUser && r.getPost().getId()==idPost && r.getReactionType().equals(reaction.getReactionType())){
                isReacted=true;
            }
        }
        if(isReacted){
            System.out.println("You have already reacted to this post");
        }
        else{
            reactionRepository.save(reaction);
        }


    }

    @Override
    public void deleteReaction(int idReaction) {
        Reaction reaction=reactionRepository.findById(idReaction).orElse(null);
        reactionRepository.delete(reaction);
    }
}
