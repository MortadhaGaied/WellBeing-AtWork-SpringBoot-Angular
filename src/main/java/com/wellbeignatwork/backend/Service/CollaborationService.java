package com.wellbeignatwork.backend.Service;


import com.wellbeignatwork.backend.Repository.ICollaboration;
import com.wellbeignatwork.backend.Repository.OfferRepository;
import com.wellbeignatwork.backend.Repository.IPublicity;
import com.wellbeignatwork.backend.Repository.UserRepository;
import com.wellbeignatwork.backend.ServiceImp.ICollaborationService;
import com.wellbeignatwork.backend.model.Collaboration;
import com.wellbeignatwork.backend.model.User;
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
	public void addCollaboration(Collaboration c,long idUser) {
		User user = userRepo.findById(idUser).get();
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
