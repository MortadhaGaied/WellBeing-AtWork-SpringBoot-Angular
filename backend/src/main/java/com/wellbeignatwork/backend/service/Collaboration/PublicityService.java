package com.wellbeignatwork.backend.service.Collaboration;


import com.wellbeignatwork.backend.repository.Collaboration.ICollaboration;
import com.wellbeignatwork.backend.repository.Collaboration.OfferRepository;
import com.wellbeignatwork.backend.repository.Collaboration.IPublicity;
import com.wellbeignatwork.backend.repository.Collaboration.ImageRepo;

import com.wellbeignatwork.backend.exceptions.Collaboration.ResourceNotFoundException;
import com.wellbeignatwork.backend.entity.Collaboration.Image;
import com.wellbeignatwork.backend.entity.Collaboration.Offer;
import com.wellbeignatwork.backend.entity.Collaboration.Publicity;
import com.wellbeignatwork.backend.repository.Collaboration.OfferRepository;
import com.wellbeignatwork.backend.util.FirebaseStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class PublicityService implements IPublicityService {
	@Autowired
	OfferRepository OfferRepo;

	@Autowired
	ICollaboration CollaborationRepo;

	@Autowired
	IPublicity PublicityRepo;

	@Autowired
	ImageRepo imageRepo;

	@Autowired
	FirebaseStorage firebaseStorage;

	@Override
	public List<Publicity> retrieveAllPublicitys() {
		List<Publicity> publicities = PublicityRepo.findAll();
		return publicities;
	}

	@Override
	public Publicity addPublicity(Publicity p , long idOffer) {
		Offer offer = OfferRepo.findById( idOffer).get();
		p.setOffers(offer);
		return PublicityRepo.save(p);
	}

	@Override
	public void deletePublicity(Long id) {
		PublicityRepo.deleteById(id);
	}

	@Override
	public Publicity updatePublicity(Publicity p) {
		return PublicityRepo.save(p);
	}

	@Override
	public Publicity retrievePublicity(Long id) {
		Publicity publicity = PublicityRepo.findById(id).orElse(null);
		return publicity;
	}


	@Override
	public boolean dateOffer(long idPublicity, Date starDateOf, Date finDateOf) {
		boolean b = false;
		LocalDate date = LocalDate.now();
		if(date.equals(starDateOf)) {
			OfferRepo.findById(idPublicity).orElse(null);
			b=true;
		}else if (date.equals(finDateOf)){
			OfferRepo.deleteById(idPublicity);
			b=false;
		}
		return b;
	}

	@Override
	public void saveImage(MultipartFile imageFile,Publicity publicity) throws IOException {
		Path currentPath = Paths.get(".");
		Path absolutePath = currentPath.toAbsolutePath();
		publicity.setPicture(absolutePath + "/src/main/resources/image/");
		byte[] bytes  = imageFile.getBytes();
		Path path =Paths.get(publicity.getPicture() + imageFile.getOriginalFilename() );
		Files.write(path,bytes);
	}

	@Override
	public void uploadImageToPub(MultipartFile img, Long idPublicity) throws IOException {
		Publicity pub = PublicityRepo.findById(idPublicity).orElse(null);
		if (pub == null) {
			throw new ResourceNotFoundException("Event is not exist");
		}
		String name = firebaseStorage.uploadFile(img);
		Image image = new Image();
		image.setName(name);
		image.setPublicity(pub);
		imageRepo.save(image);
	}

	@Override
	public void uploadPubBanner(MultipartFile img, Long eventId) throws IOException {
		Publicity pub = PublicityRepo.findById(eventId).orElse(null);
		if (pub == null) {
			throw new ResourceNotFoundException("Event is not exist");
		}
		String name = firebaseStorage.uploadFile(img);
		pub.setBanner(name);
		PublicityRepo.save(pub);
	}

	@Override
	@Transactional
	public void deleteImage(String imgName) {
		if (!imageRepo.existsByName(imgName)) {
			throw new ResourceNotFoundException("Image doesn't exist");
		}
		if (!firebaseStorage.deleteFile(imgName)) {
			throw new RuntimeException("Something went wrong when deleting image");
		}
		imageRepo.deleteByName(imgName);
	}

	@Override
	public List<Publicity> retrieveByTitle(String title) {
		return PublicityRepo.findByOffersTitle(title);
	}

	@Override
	public List<Publicity> retrieveBeforeOfferStartDate() {
		return PublicityRepo.getPublicityBeforeOfferStartDate(LocalDateTime.now().plusDays(7));
	}

	@Override
	public List<Publicity> retrieveByOfferLocalisation(String loc) {
		return PublicityRepo.findByOffersLocalisation(loc);
	}
}
