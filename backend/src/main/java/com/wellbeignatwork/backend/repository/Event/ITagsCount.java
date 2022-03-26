package com.wellbeignatwork.backend.repository.Event;


import com.wellbeignatwork.backend.entity.Event.Tag;

public interface ITagsCount {
    Tag getTag();
    Long getTotalTag();

}
