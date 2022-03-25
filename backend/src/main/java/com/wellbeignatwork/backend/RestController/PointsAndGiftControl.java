package com.wellbeignatwork.backend.RestController;

import com.wellbeignatwork.backend.Entity.User;
import com.wellbeignatwork.backend.Service.IntPointsAndGiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/PointsAndGift")
public class PointsAndGiftControl {
    @Autowired
    IntPointsAndGiftService intPointsAndGiftService;

    @GetMapping("/points/{idUser}")
    public int CollectPoints(@PathVariable("idUser") int idUser)
    {
        return intPointsAndGiftService.CollectPoints(idUser);
    }


    @PostMapping("/saveGift")
    public void save ()
    {
        intPointsAndGiftService.save();

    }

    @GetMapping("/Gift/{idUser}")
    public int UserGift(@PathVariable("idUser") int idUser)
    {
        return intPointsAndGiftService.UserGift(idUser);
    }
    @GetMapping("/PointRanking")
    public Iterable<User> PointRanking()
    {
        return intPointsAndGiftService.PointRanking();
    }





}
