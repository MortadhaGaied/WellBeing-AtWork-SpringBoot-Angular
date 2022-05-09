package com.wellbeignatwork.backend.controller.Evaluation;

import com.wellbeignatwork.backend.entity.Evaluation.Sujet;
import com.wellbeignatwork.backend.entity.Evaluation.VoteIdea;
import com.wellbeignatwork.backend.repository.Evaluation.IntVoteIdeaRepo;
import com.wellbeignatwork.backend.repository.Evaluation.SujetRepo;
import com.wellbeignatwork.backend.service.Evaluation.IntVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.multipart.MultipartFile;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;

@RestController
@RequestMapping("/Vote")
@CrossOrigin("*")
public class VoteController {

    @Autowired
    IntVoteService intVoteService;




    @Autowired
    SujetRepo MyREpo;

    @PostMapping("/ajouterPour/{sujetId}/{userId}")
    @ResponseBody
    public ResponseEntity<?> ajouterPour(@PathVariable(value = "sujetId") int sujetid,
                                         @PathVariable(value = "userId") Long userid,
                                        @RequestBody VoteIdea v )
    {

        intVoteService.AddYes( v,sujetid, userid);
        return ResponseEntity.created(null).body(v);

    }
    @PostMapping("/ajouterContre/{sujetId}/{userId}")
    @ResponseBody
    public ResponseEntity<?> ajouterContre(@PathVariable(value = "sujetId") int sujetid,
                                         @PathVariable(value = "userId") Long userid,
                                         @RequestBody VoteIdea v )
    {

        intVoteService.AddNo( v,sujetid, userid);
        return ResponseEntity.created(null).body(v);

    }


    @GetMapping("/AffichageSujet")
    @ResponseBody
    public List<Sujet> FindSujet()
    {
       return intVoteService.FindSujet();
    }

    @PostMapping("/addsujet")
    @ResponseBody
    public void addSujet(@RequestBody Sujet sujet)
    {
        intVoteService.addSujet(sujet);
    }


    @GetMapping("/GetNbrYes/{sujetId}")
    @ResponseBody
    public int countYes(@PathVariable("sujetId") int sujetId)
    {
        return intVoteService.countYes(sujetId);
    }



    @GetMapping("/GetNbrNo/{sujetId}")
    @ResponseBody
    public int countNo(@PathVariable("sujetId") int sujetId)
    {
        return intVoteService.countNo(sujetId);
    }



    @GetMapping("/GetVote/{sujetId}/{iduser}")
    @ResponseBody
    public ResponseEntity<?> getVote(@PathVariable(value = "sujetId") int sujetId,
                                          @PathVariable("iduser") Long iduser) {
        VoteIdea vote;
        vote=intVoteService.getVote(sujetId, iduser) ;
        return 	ResponseEntity.ok().body(vote);
    }





    @GetMapping("/findNameUsersVoter/{sujetId}")
    @ResponseBody
    public List<String> findNomdesUsersVoter(@PathVariable("sujetId") int sujetId)
    {return intVoteService.findNomdesUsersVoter(sujetId);}













    @PutMapping("/UpDateYes/{sujetId}/{userId}")
    @ResponseBody
    public void UpdateYes(@PathVariable("sujetId") int sujetId,@PathVariable("userId") Long userId)
    {
        intVoteService.UpdateYes(sujetId,userId);

    }
    @PutMapping("/UpdateNo/{sujetId}/{userId}")
    @ResponseBody
    public void UpdateNo(@PathVariable("sujetId") int sujetId,@PathVariable("userId") Long userId)
    {
        intVoteService.UpdateNo(sujetId,userId);
    }



    @PutMapping(value = "/delete/{idsujet}/{iduser}")
    @ResponseBody
    public ResponseEntity<?> deleteVote(@PathVariable("idsujet") int idsujet, @PathVariable("iduser") Long iduser) {
        intVoteService.deletevoteById(idsujet, iduser);
        return ResponseEntity.ok().build();
    }



    @GetMapping("/Verification/{userId}/{sujetId}")
    @ResponseBody
    public String verificationvoteChoix(@PathVariable("userId") Long userId,@PathVariable("sujetId") int sujetId)
    { return intVoteService.VerifYourChoice(userId,sujetId); }

    @GetMapping("/verification/{sujetId}/{iduser}")
    @ResponseBody
    public Boolean verificationvote(@PathVariable("sujetId") int sujetId,@PathVariable("iduser") Long userId)
    { return intVoteService.verificationvote(sujetId,userId);}


    @GetMapping("/YesAndNo/{sujetId}")
    @ResponseBody
    public List<Sujet> countYesandNo( @PathVariable("sujetId")int sujetId)
    {
        return  intVoteService.countYesandNo(sujetId);
    }

    @DeleteMapping(value = "/delete/{idsujet}")
    public ResponseEntity<?> DeleteSujet(@PathVariable("sujetId") int sujetId)
    {
        Optional<Sujet> sujetOptional=MyREpo.findById(sujetId);
        MyREpo.deleteById(sujetId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PutMapping(value = "/Update/{sujetId}")
    @ResponseBody
    public ResponseEntity<?> updateSujet(@PathVariable("sujetId") int sujetId,@RequestBody Sujet sujet)
    {
        Optional<Sujet> sujetOptional=MyREpo.findById(sujetId);
        if(!sujetOptional.isPresent())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            sujetOptional.get().setDateAjout(sujet.getDateAjout());
            sujetOptional.get().setNomSujet(sujet.getNomSujet());
            sujetOptional.get().setDescription(sujet.getDescription());
            MyREpo.save(sujet);
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }











    @GetMapping("/getSujetById/{sujetId}")
    @ResponseBody
    public Sujet getSujetById(@PathVariable("sujetId")int sujetId)
    {
        return intVoteService.getSujetById(sujetId);
    }




/*
    @PutMapping(value = "/affecterpoint/{idsujet}")
    @ResponseBody
    public ResponseEntity<?> affecterpoint(@PathVariable("idsujet") int idsujet) {
        intVoteService.affecterdespoints(idsujet);
        return ResponseEntity.ok().build();
    }

 */





}


