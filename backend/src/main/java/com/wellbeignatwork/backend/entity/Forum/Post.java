package com.wellbeignatwork.backend.entity.Forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.entity.Tags;
import lombok.*;
import com.wellbeignatwork.backend.entity.User;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String subject;
    private String content;
    private LocalDateTime createdAt=LocalDateTime.now();
    private LocalDateTime modifiedAt;
    @ElementCollection(targetClass = Tags.class)
    @CollectionTable(name = "post_tags", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "tags")
    @Enumerated(EnumType.STRING)
    private Set<Tags> tags = new HashSet<>();
    @OneToMany(mappedBy = "post_attachment",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<File> fileAttachments;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "post_comment",cascade = CascadeType.ALL)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<Reaction> reactions;
}
