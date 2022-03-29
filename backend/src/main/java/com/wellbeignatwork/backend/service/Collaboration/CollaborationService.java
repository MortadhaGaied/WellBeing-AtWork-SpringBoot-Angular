package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.repository.Collaboration.ICollaboration;
import com.wellbeignatwork.backend.repository.Collaboration.OfferRepository;
import com.wellbeignatwork.backend.repository.Collaboration.IPublicity;
import com.wellbeignatwork.backend.repository.User.UserRepository;

import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;
import com.wellbeignatwork.backend.entity.User.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CollaborationService implements ICollaborationService {
	@Autowired
	OfferRepository OfferRepo;
	
	@Autowired
	ICollaboration CollaborationRepo;
	
	@Autowired
	IPublicity PublicityRepo;

	@Autowired
	UserRepository userRepo;


	@Override
	public List<Collaboration> retrieveAllCollaborations() {
		List<Collaboration> collaborations = (List<Collaboration>) CollaborationRepo.findAll();
		return collaborations;
	}

	@Override
	public void addCollaboration(Collaboration c,Long idUser) {
		User user = userRepo.findById(idUser).orElse(null);
		c.setUsers(user);
		CollaborationRepo.save(c);
	}

	@Override
	public void deleteCollaboration(Long id) {
		CollaborationRepo.deleteById(id);

	}

	@Override
	public Collaboration updateCollaboration(Collaboration c) {
		return CollaborationRepo.save(c);
	}

	@Override
	public Collaboration retrieveCollaboration(Long id) {
		Collaboration collaboration = CollaborationRepo.findById(id).orElse(null);
		return collaboration;
	}
}
