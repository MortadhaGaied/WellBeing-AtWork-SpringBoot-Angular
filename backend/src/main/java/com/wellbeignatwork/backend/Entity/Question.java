package com.wellbeignatwork.backend.Entity;

public class Question {


    private int id;
    private String description;



    public Question() {

    }

    public Question(Integer id, String description) {
        super();
        this.id = id;
        this.description = description;

    }


    public String getDescription() {
        return description;
    }


    @Override
    public String toString() {
        return String
                .format("Question [id=%s, description=%s]",
                        id, description);
    }




}
