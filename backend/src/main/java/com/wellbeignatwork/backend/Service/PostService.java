package com.wellbeignatwork.backend.Service;

import com.wellbeignatwork.backend.Entity.Post;

import java.util.Collection;

public interface PostService {
    public Post createpost(Post post);
    public Collection<Post> getAll();
    public Collection<Post> getAllPaginated(Integer pageNumber);
    public Post updatepost(Post post);
    public void deletepost(int id);
    public Post assignFileToPost(int id_file,int id_post);

}
