package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Post;

import java.util.Collection;
import java.util.List;

public interface PostService {
    public Post createpost(Post post);
    public Collection<Post> getAll();
    public Collection<Post> getAllPaginated(Integer pageNumber);
    public Post updatepost(Post post);
    public void deletepost(int id);
    public Post assignFileToPost(int id_file,int id_post);
    public List<Post> groupByPreference(Long idUser);
    public Post assignUserToPost(Long idUser,int idPost);
    public List<Post> getTrendingPost();

}
