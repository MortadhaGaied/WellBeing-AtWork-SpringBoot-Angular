package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public interface PostService {
    public Post createpost(Post post, MultipartFile file) throws IOException;
    Post addPost(Post post,Long idUser);
    public Collection<Post> getAll();
    public Collection<Post> getAllPaginated(Integer pageNumber);
    public Post updatepost(Post post);
    public void deletepost(int id);
    public List<Post> groupByPreference(Long idUser);
    public Post assignUserToPost(Long idUser,int idPost);
    public List<Post> getTrendingPost();

}
