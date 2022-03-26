package com.wellbeignatwork.backend.service;

import com.wellbeignatwork.backend.entity.Comment;

import java.util.Collection;
import java.util.List;

public interface CommentService {
    public Comment createcomment(Comment comment,int idPost,int idUser);
    public Collection<Comment> getAll();
    public Comment updatecomment(Comment comment);
    public void deletecomment(int id);
    public List<Integer> PostSatisfaction(int idPost);
}
