package com.wellbeignatwork.backend.service;

import com.wellbeignatwork.backend.entity.*;
import com.wellbeignatwork.backend.repository.CommentRepository;
import com.wellbeignatwork.backend.repository.FileRepository;
import com.wellbeignatwork.backend.repository.PostRepository;
import com.wellbeignatwork.backend.repository.UserRepository;
import com.wellbeignatwork.backend.exceptions.PostException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;
    private FileRepository fileRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository,FileRepository fileRepository,UserRepository userRepository,CommentRepository commentRepository){
        this.fileRepository=fileRepository;
        this.postRepository=postRepository;
        this.userRepository=userRepository;
        this.commentRepository=commentRepository;
    }
    public List<String> getAllSubjects(){
        List<String> resultat=new ArrayList<>();
        for(Post p:postRepository.findAll()){
            resultat.add(p.getSubject());
        }
        return resultat;
    }
    public List<Post> getPostsforUser(int iduser){
        int n;
        List<Post> list=new ArrayList<>();

        User u=userRepository.findById(iduser).orElse(null);
        for(Post p:postRepository.findAll()){
            List<Tags> tags=new ArrayList<>(p.getTags());
            n=tags.size()-1;
            while(n<0){
                if(tags.containsAll(u.getTags())){
                    list.add(p);
                    tags.remove(n);
                    n--;
                }

            }

        }
        return list;

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
        post.setModifiedAt(LocalDateTime.now());
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
    @Override
    public Post assignUserToPost(int id_user,int id_post){
        Post p=postRepository.findById(id_post).orElse(null);
        User u=userRepository.findById(id_user).orElse(null);
        u.getPosts().add(p);
        p.setUser(u);

        postRepository.save(p);
        return p;
    }
    @Override
    public List<Post> groupByPreference(int idUser)
    {
        User u=userRepository.findById(idUser).orElse(null);
        List<Tags> userTags=new ArrayList<>(u.getTags());
        Map<Post,Integer> prefMap=new HashMap<>();

        for(Post p:postRepository.findAll()){
            List<Tags> postTags=new ArrayList<>(p.getTags());
            List<Tags> commonTags=new ArrayList<>(postTags);
            commonTags.retainAll(userTags);

            prefMap.put(p,commonTags.size());
        }
        LinkedHashMap<Post, Integer> reverseSortedMap = new LinkedHashMap<>();

        prefMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
        return new ArrayList<Post>(reverseSortedMap.keySet());


    }
    public List<Double> postInteraction(List<Post> posts){
        List<Double> result=new ArrayList<>();
        for(Post p:posts){
            for(Comment c:commentRepository.findAll()){
                if(c.getCreateDate().isAfter(LocalDateTime.now().minus(7,ChronoUnit.DAYS))){
                    result.add((double)(p.getComments().size()));
                }
            }

        }
        return result;
    }
    public HashMap<String, List> analyzeDataForSignals(List<Double> data, Double threshold, Double influence) {
        int lag=0;
        if(data.size()<10){
            lag=1;
        }
        else{
            lag=5;
        }
        SummaryStatistics stats = new SummaryStatistics();
        List<Integer> signals = new ArrayList<Integer>(Collections.nCopies(data.size(), 0));
        List<Double> filteredData = new ArrayList<Double>(data);

        List<Double> avgFilter = new ArrayList<Double>(Collections.nCopies(data.size(), 0.0d));

        List<Double> stdFilter = new ArrayList<Double>(Collections.nCopies(data.size(), 0.0d));
        System.out.println(data);
        for (int i = 0; i < lag; i++) {
            stats.addValue(data.get(i));
        }

        avgFilter.set(lag - 1, stats.getMean());

        stdFilter.set(lag - 1, Math.sqrt(stats.getPopulationVariance()));
        stats.clear();

        for (int i = lag; i < data.size(); i++) {

            if (Math.abs((data.get(i) - avgFilter.get(i - 1))) > threshold * stdFilter.get(i - 1)) {
                if (data.get(i) > avgFilter.get(i - 1)) {
                    signals.set(i, 1);
                } else {
                    signals.set(i, -1);
                }
                filteredData.set(i, (influence * data.get(i)) + ((1 - influence) * filteredData.get(i - 1)));
            } else {
                signals.set(i, 0);
                filteredData.set(i, data.get(i));
            }

            for (int j = i - lag; j < i; j++) {
                stats.addValue(filteredData.get(j));
            }
            avgFilter.set(i, stats.getMean());
            stdFilter.set(i, Math.sqrt(stats.getPopulationVariance()));
            stats.clear();
        }

        HashMap<String, List> returnMap = new HashMap<String, List>();
        returnMap.put("signals", signals);
        returnMap.put("filteredData", filteredData);
        returnMap.put("avgFilter", avgFilter);
        returnMap.put("stdFilter", stdFilter);

        return returnMap;

    }
    @Override
    public List<Post> getTrendingPost(){
        DecimalFormat df = new DecimalFormat("#0.000");
        List<Post> allPosts=new ArrayList<>();
        postRepository.findAll().forEach(allPosts::add);
        ArrayList<Double> data =new ArrayList<Double>(postInteraction(allPosts));


        double threshold = 5;
        double influence = 0;
        List<Integer> signalsList=new ArrayList<>();
        Map<Post,Integer> sortTrendPost=new HashMap<>();



            HashMap<String, List> resultsMap = analyzeDataForSignals(data, threshold, influence);
            signalsList = resultsMap.get("signals");
            for(int i=0;i<allPosts.size();i++){
                sortTrendPost.put(allPosts.get(i),signalsList.get(i));
            }



        for (int i : signalsList) {
            System.out.print(df.format(i) + "\t");
        }
        System.out.println();


        System.out.println();
        for (int i = 0; i < signalsList.size(); i++) {
            if (signalsList.get(i) != 0) {
                System.out.println("Point " + i + " gave signal " + signalsList.get(i));
            }
        }
        System.out.println();
        sortTrendPost.forEach((key, value) -> System.out.println(key.getSubject() + ":" + value));
        return null;
    }

}
