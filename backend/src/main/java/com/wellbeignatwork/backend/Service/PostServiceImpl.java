package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Entity.File;
import com.wellbeignatwork.backend.Entity.Post;
import com.wellbeignatwork.backend.Entity.User;
import com.wellbeignatwork.backend.Repository.FileRepository;
import com.wellbeignatwork.backend.Repository.PostRepository;
import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.exceptions.PostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private FileRepository fileRepository;
    private UserRepository userRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,FileRepository fileRepository,UserRepository userRepository){
        this.fileRepository=fileRepository;
        this.postRepository=postRepository;
        this.userRepository=userRepository;
    }
    public List<String> getAllSubjects(){
        List<String> resultat=new ArrayList<>();
        for(Post p:postRepository.findAll()){
            resultat.add(p.getSubject());
        }
        return resultat;
    }
    @Override
    public Post createpost(Post post) {
        if(getAllSubjects().contains(post.getSubject())){
            return postRepository.findPostBySubject(post.getSubject());
        }
        else{
            return postRepository.save(post);
        }

    }

    @Override
    public Collection<Post> getAll() {
        return (Collection<Post>) postRepository.findAll();
    }

    @Override
    public Collection<Post> getAllPaginated(Integer pageNumber) {
        Integer index = pageNumber - 1;
        Page<Post> posts = this.postRepository.findAll(PageRequest.of(index, 20));
        return posts.stream().collect(Collectors.toList());
    }
    //@PreAuthorize("hasRole('USER')")
    @Override
    public Post updatepost(Post post) {
        postRepository.findById(post.getId()).orElseThrow(
                () -> new PostException("Can't update. Post not found!")
        );
        return this.postRepository.save(post);
    }
    //@PreAuthorize("hasRole('USER')")
    @Override
    public void deletepost(int id) {
       postRepository.delete(postRepository.findById(id).orElse(null));
    }

    @Override
    public Post assignFileToPost(int id_file, int id_post) {
        Post p=postRepository.findById(id_post).orElse(null);
        File f=fileRepository.findById(id_file).orElse(null);
        List<File> list=new ArrayList<>();
        f.setPost_attachment(p);
        fileRepository.save(f);
        if(p.getFileAttachments()!=null){
            p.getFileAttachments().add(f);
        }
        else{
            p.setFileAttachments(list);
        }
        postRepository.save(p);
        return p;
    }
    public Post assignUserToPost(int id_user,int id_post){
        Post p=postRepository.findById(id_post).orElse(null);
        User u=userRepository.findById(id_user).orElse(null);
        p.setUser(u);
    return p;
    }
}
