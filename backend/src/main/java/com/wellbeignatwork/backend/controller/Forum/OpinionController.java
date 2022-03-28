package com.wellbeignatwork.backend.controller.Forum;

import com.wellbeignatwork.backend.entity.Forum.Opinion;
import com.wellbeignatwork.backend.service.Forum.OpinionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Opinion")
public class OpinionController {
    private OpinionService opinionService;


    @PostMapping("/add-opinion")
    public void AddOpinion(@RequestBody Opinion opinion) {
        opinionService.AddOpinion(opinion);
    }
    @PutMapping("/update-opinion")
    public void UpdateOpinion(@RequestBody Opinion opinion) {
        opinionService.UpdateOpinion(opinion);
    }
    @GetMapping("/get-all-opinion")
    public List<Opinion> getAllOpinions() {
        return opinionService.getAllOpinions();
    }
    @DeleteMapping("/delete/{id}")
    public void DeleteOpinion(@PathVariable("id") Long id) {
        opinionService.DeleteOpinion(id);
    }
    @PostMapping("/assignOpinionToUser/{idOpinion}/{idUser}")
    public void AddOpinionToUser(@PathVariable Long idOpinion, @PathVariable("idUser") Long idUser) {
        opinionService.assignOpinionToUser(idOpinion, idUser);
    }
}
