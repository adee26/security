package com.example.security.controllers;


import com.example.security.entities.Resolution;
import com.example.security.services.ResolutionService;
import com.example.security.services.UserRepositoryUserDetailsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
public class ResolutionController {
	private final ResolutionService resolutionService;

	public ResolutionController(ResolutionService resolutionService) {
		this.resolutionService = resolutionService;
	}

	@GetMapping("/resolutions")
	public List<Resolution> read(Authentication principal) {
	//	Long owner = 1L;
		UserDetails userDetails = (UserDetails) principal.getPrincipal();
		Long owner = ((UserRepositoryUserDetailsService.ResolutionsUserSpringSecurityUserDetails) userDetails).getId();

		return this.resolutionService.findByOwner(owner);
	}

	/** added @PathVariable
	 * @return*/

	@GetMapping("/resolution/{id}")
	public Resolution read(@PathVariable Long id, Authentication authentication) {
		return resolutionService.findById(id, authentication);
	}

	@PostMapping("/resolution")
	public Resolution make(@RequestBody String text, Authentication authentication) {
		return this.resolutionService.save(text, authentication);
	}

	@PutMapping(path="/resolution/{id}/revise")
	@Transactional
	public Optional<Resolution> revise(@PathVariable("id") Long id, @RequestBody String text, Authentication authentication) {
	 	return resolutionService.revise(id, text, authentication);
	}

	@PutMapping("/resolution/{id}/complete")
	@Transactional
	public Optional<Resolution> complete(@PathVariable("id") Long id, Authentication authentication) {
	 	return resolutionService.complete(id, authentication);
	}
}
