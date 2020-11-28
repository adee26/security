package com.example.security.services;

import com.example.security.entities.Resolution;
import com.example.security.repositories.ResolutionRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResolutionServiceImpl implements ResolutionService{
    private final ResolutionRepository resolutionRepository;

    public ResolutionServiceImpl(ResolutionRepository resolutionRepository) {
        this.resolutionRepository = resolutionRepository;
    }

    public List<Resolution> findAll(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long owner = ((UserRepositoryUserDetailsService.ResolutionsUserSpringSecurityUserDetails) userDetails).getId();
        return this.findByOwner(owner);
    }

    @Override
    public List<Resolution> findByOwner(Long owner) {
        return resolutionRepository.findByOwner(owner);
    }

    @Override
    public Optional<Resolution> revise(Long id, String text, Authentication authentication) {
        List<Resolution> ownerResolutions = this.findAll(authentication);
        for(Resolution resolution: ownerResolutions){
            if (resolution.getId() == id){
                resolutionRepository.revise(id, text);
                return resolutionRepository.findById(id);
            }
        }

        return null;
    }

    public Optional<Resolution> complete(Long id, Authentication authentication) {
        List<Resolution> ownerResolutions = this.findAll(authentication);
        for(Resolution resolution: ownerResolutions){
            if (resolution.getId() == id){
                resolutionRepository.complete(id);
                return resolutionRepository.findById(id);
            }
        }
        return null;
    }

    @Override
    public Resolution findById(long id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long owner = ((UserRepositoryUserDetailsService.ResolutionsUserSpringSecurityUserDetails) userDetails).getId();
        List<Resolution> resolutionList = resolutionRepository.findByOwner(owner);
        for(Resolution resolution : resolutionList){
            if(resolution.getId() == id){
                return resolutionRepository.findById(id);
            }
        }
        return null;
    }

    @Override
    public Resolution save(String text, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Long ownerId = ((UserRepositoryUserDetailsService.ResolutionsUserSpringSecurityUserDetails) userDetails).getId();

        Resolution resolution = new Resolution(text, ownerId);
        resolution.setId(ownerId);

        resolutionRepository.save(resolution);
        return resolution;
    }




}
