package com.wellbeignatwork.backend.controller.Forum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wellbeignatwork.backend.entity.Forum.File;
import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.service.Forum.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/Post")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }
    @PostMapping("/add-post")
    public ResponseEntity<Post> createPost(@RequestParam("file") MultipartFile file, @RequestParam("post")String post) throws IOException {

        Post p = new ObjectMapper().readValue(post, Post.class);
        log.info("post : "+p.getContent());

        return new ResponseEntity<>(this.postService.createpost(p,file), HttpStatus.OK);
    }
    @GetMapping("/all-post")
    public ResponseEntity<Collection<Post>> getAllPosts() {
        return new ResponseEntity<>(this.postService.getAll(), HttpStatus.OK);
    }
    @PutMapping("/update-post")
    public ResponseEntity<Post> updatePost(@RequestBody Post post) {
        return new ResponseEntity<>(this.postService.updatepost(post), HttpStatus.OK);
    }
    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity deletePostById(@PathVariable int id) {
        this.postService.deletepost(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @GetMapping("/assignFileToPost/{id_post}/{id_file}")
    public ResponseEntity<Post> assignFileToPost(@PathVariable int id_post,@PathVariable int id_file){
        return new ResponseEntity<>(this.postService.assignFileToPost(id_post, id_file),HttpStatus.OK);
    }
    @GetMapping("/groupByPreference/{idUser}")
    public List<Post> groupByPreference(@PathVariable Long idUser){
        return this.postService.groupByPreference(idUser);
    }
    @GetMapping("assignUserToPost/{idUser}/{idPost}")
    public Post assignUserToPost(@PathVariable Long idUser,@PathVariable int idPost){
        return postService.assignUserToPost(idUser, idPost);
    }
    @GetMapping("/TrendingPost")
    public void getTrendingPost(){
        postService.getTrendingPost();
    }
}
