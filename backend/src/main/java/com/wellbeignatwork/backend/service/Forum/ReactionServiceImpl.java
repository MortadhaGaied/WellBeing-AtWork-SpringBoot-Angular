package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.entity.Forum.Reaction;
import com.wellbeignatwork.backend.entity.Forum.ReactionType;
import com.wellbeignatwork.backend.repository.Forum.PostRepository;
import com.wellbeignatwork.backend.repository.Forum.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public void addReactToPost(Reaction reaction, int idPost, Long idUser) {
        Reaction isReacted=null;
        Reaction isReactedSame=null;
        Post p=postRepository.findById(idPost).orElse(null);
        reaction.setIdUser(idUser);
        reaction.setPost(p);
        for(Reaction r:reactionRepository.findAll()){
            if(r.getPost()!=null){
                if(r.getIdUser()==idUser && r.getPost().getId()==idPost ){
                    if( r.getReactionType().equals(reaction.getReactionType())){
                        isReactedSame=r;
                    }
                    isReacted=r;

                }

            }
        }
        if(isReactedSame!=null){
            System.out.println("**********************************************************************************************id="+isReactedSame.getIdReaction()+"*****************************************************************************************************************************");

            deleteReaction(isReactedSame.getIdReaction());
        }
        else if(isReacted!=null){
            isReacted.setReactionType(reaction.getReactionType());
            reactionRepository.save(isReacted);
        }
        else{
            reactionRepository.save(reaction);
        }


    }

    @Override
    public void deleteReaction(int idReaction) {

        Reaction reaction=reactionRepository.findById(idReaction).orElse(null);
        Post post=postRepository
                .findById(
                        reaction.getPost().getId())
                .orElse(null);
        post.getReactions().remove(reaction);
        postRepository.save(post);
        reactionRepository.delete(reaction);
    }
    @Override
    public int getNbrReactionByPost(int idPost){
        Post p=postRepository.findById(idPost).orElse(null);
        return reactionRepository.NbrReactionByPost(p);
    }
    @Override
    public Map<ReactionType,Integer> getNbrReactionByReactionTypeAndPost(int idPost){
        Post p=postRepository.findById(idPost).orElse(null);
        Map<ReactionType,Integer> result=new HashMap<>();
        List<Integer> nbr=new ArrayList<>(Collections.nCopies(5, 0));
        for(Reaction r :p.getReactions()){
            if(r.getReactionType().equals(ReactionType.LIKE)){
                nbr.set(0,nbr.get(0)+1);
            }
            else if(r.getReactionType().equals(ReactionType.LOVE)){
                nbr.set(1,nbr.get(1)+1);
            }
            else if(r.getReactionType().equals(ReactionType.HAHA)){
                nbr.set(2,nbr.get(2)+1);
            }
            else if(r.getReactionType().equals(ReactionType.SAD)){
                nbr.set(3,nbr.get(3)+1);
            }
            else{
                nbr.set(4,nbr.get(4)+1);
            }
        }

        result.put(ReactionType.LIKE,nbr.get(0));
        result.put(ReactionType.LOVE,nbr.get(1));
        result.put(ReactionType.HAHA,nbr.get(2));
        result.put(ReactionType.SAD,nbr.get(3));
        result.put(ReactionType.ANGRY,nbr.get(4));
        return result;

    }

}
