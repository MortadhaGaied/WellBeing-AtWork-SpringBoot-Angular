package com.wellbeignatwork.backend.controller.Collaboration;


import com.wellbeignatwork.backend.entity.Collaboration.Collaboration;
import com.wellbeignatwork.backend.service.Collaboration.ICollaborationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/Collaboration")
public class CollaborationController {
	@Autowired
	ICollaborationService collaborationService;

	//http://localhost:8081/Collaboration/updateCollaboration
	@PutMapping("/addCollaboration")
	@ResponseBody
	public Collaboration addCollaboration(@RequestBody Collaboration c){
		return collaborationService.addCollaboration(c);
	}

	//http://localhost:8081/Collaboration/UpdateCollaboration/1
	@PostMapping("/UpdateCollaboration/{idUser}")
	@ResponseBody
	public void UpdateCollaboration(@RequestBody Collaboration c, @PathVariable long idUser)
	{
		collaborationService.updateCollaboration(c,idUser);
	}

	//http://localhost:8080/Collaboration/deleteCollaboration/id
	@DeleteMapping("/Collaboration/deleteCollaboration/{id}")
	@ResponseBody
	public void deleteCollaboration(@PathVariable Long id){
		collaborationService.deleteCollaboration(id);
	}

	//http://localhost:8080/Collaboration/retrieveAllCollaborations
	@GetMapping("/Collaboration/retrieveAllCollaborations")
	@ResponseBody
	public List<Collaboration> retrieveAllCollaborations() {

		return collaborationService.retrieveAllCollaborations();
	}


	//http://localhost:8080/Collaboration/retrieveCollaboration
	@GetMapping("/Collaboration/retrieveCollaboration")
	@ResponseBody
	public Collaboration retrieveCollaboration(@PathVariable Long id){
		return collaborationService.retrieveCollaboration(id);
	}
}
