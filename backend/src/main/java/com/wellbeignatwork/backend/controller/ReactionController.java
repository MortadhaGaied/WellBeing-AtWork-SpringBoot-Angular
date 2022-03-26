package com.wellbeignatwork.backend.controller;

import com.wellbeignatwork.backend.entity.Reaction;
import com.wellbeignatwork.backend.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/React")
public class ReactionController {
    private ReactionService reactionService;
    @Autowired
    public ReactionController(ReactionService reactionService){
        this.reactionService=reactionService;
    }
    @PostMapping("/addReaction/{idPost}/{idUser}")
    public void addReactToPost(@RequestBody Reaction reaction, @PathVariable int idPost,@PathVariable int idUser){
        reactionService.addReactToPost(reaction,idPost,idUser);
    }
    @DeleteMapping("/deleteReaction/{idReaction}")
    public void deleteReaction(@PathVariable int idReaction){
        reactionService.deleteReaction(idReaction);
    }

}
