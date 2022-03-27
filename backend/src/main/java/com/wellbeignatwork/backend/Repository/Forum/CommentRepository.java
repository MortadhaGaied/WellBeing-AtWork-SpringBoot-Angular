package com.wellbeignatwork.backend.repository.Forum;

import com.wellbeignatwork.backend.entity.Forum.Comment;
import com.wellbeignatwork.backend.entity.Forum.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends CrudRepository<Comment,Integer> {
    @Query("SELECT count(c) FROM Comment c where c.idUser=:idUser")
    public int NbrCommentByUser(Long idUser);
    @Query("SELECT count(c) FROM Comment c where c.post_comment=:post")
    public int NbrCommentByPost(Post post);
}
