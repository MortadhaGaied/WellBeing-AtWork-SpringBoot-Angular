package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;

import java.util.List;

public interface ICollaborationService {
    List<Collaboration> retrieveAllCollaborations();

    void addCollaboration(Collaboration c,Long idUser);

    void deleteCollaboration(Long id);

    Collaboration updateCollaboration(Collaboration c);

    Collaboration retrieveCollaboration(Long id);

}
