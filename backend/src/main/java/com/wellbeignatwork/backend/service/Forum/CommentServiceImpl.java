package com.wellbeignatwork.backend.service.Forum;

import com.wellbeignatwork.backend.entity.Forum.Comment;
import com.wellbeignatwork.backend.entity.Forum.Post;
import com.wellbeignatwork.backend.repository.Forum.CommentRepository;
import com.wellbeignatwork.backend.repository.Forum.PostRepository;
import com.wellbeignatwork.backend.exceptions.PostException;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    private StanfordCoreNLP pipeline;
    private PostRepository postRepository;
    private CommentRepository commentRepository;
    @PostConstruct
    public void init() {
        pipeline = new StanfordCoreNLP("MyPropFile.properties");
    }
    @Autowired
    public CommentServiceImpl(PostRepository postRepository,CommentRepository commentRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;

    }
    @Override
    public Comment createcomment(Comment comment,int idPost,int idUser) {
        Post p=postRepository.findById(idPost).orElse(null);
        if(p.getComments()==null){
            p.setComments(Collections.singletonList( comment ));
        }
        else{
            p.getComments().add(comment);
        }
        comment.setPost_comment(p);
        comment.setIdUser(idUser);
        return commentRepository.save(comment);

    }

    @Override
    public Collection<Comment> getAll() {
        return (Collection<Comment>) commentRepository.findAll();
    }


    //@PreAuthorize("hasRole('USER')")
    @Override
    public Comment updatecomment(Comment comment) {
        commentRepository.findById(comment.getIdComment()).orElseThrow(
                () -> new PostException("Can't update. Comment not found!")
        );
        comment.setModifyDate(LocalDateTime.now());
        return this.commentRepository.save(comment);
    }
    //@PreAuthorize("hasRole('USER')")
    @Override
    public void deletecomment(int id) {
        commentRepository.delete(commentRepository.findById(id).orElse(null));
    }
    public List<Integer> PostSatisfaction(int idPost){
        Post p =postRepository.findById(idPost).orElse(null);
        List<Integer> result=new ArrayList<>();
        if(p.getComments()==null){
            return null;
        }
        else{
            for(Comment c:p.getComments()){
                result.add(findSentiment(c.getCommentContent()));
            }
            return result;
        }

    }
    public int findSentiment(String commentContent) {
        int mainSentiment = 0;
        String cleanComment = cleanComment(commentContent);
        if (cleanComment != null && cleanComment.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(cleanComment);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        return mainSentiment;
    }
    private String cleanComment(String commentContent) {
        commentContent = commentContent.toLowerCase();
        commentContent = commentContent.replaceAll("(@[A-Za-z0-9_]+)|([^0-9A-Za-z \\t])|(\\w+:\\/\\/\\S+)", " ");
        System.out.println(commentContent);
        return commentContent;
    }


}
