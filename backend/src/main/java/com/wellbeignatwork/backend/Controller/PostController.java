package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.Entity.Post;
import com.wellbeignatwork.backend.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController


@RequestMapping("/Post")
public class PostController {
    private PostService postService;
    @Autowired
    public PostController(PostService postService){
        this.postService=postService;
    }
    @PostMapping("/add-post")
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        return new ResponseEntity<>(this.postService.createpost(post), HttpStatus.OK);
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
}
