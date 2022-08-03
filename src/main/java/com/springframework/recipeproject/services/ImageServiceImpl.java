package com.springframework.recipeproject.services;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
	private final RecipeRepository recipeRepository;

	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public void saveImageFile(Long id, MultipartFile file) {
		
		try {
			Recipe recipe = recipeRepository.findById(id).get();
			Byte[] byteObjs = new Byte[file.getBytes().length];
			
			int i = 0;
			for(byte b : file.getBytes()) {	//loop over byte array
				byteObjs[i++] = b;
			}
			
			recipe.setImage(byteObjs);
			recipeRepository.save(recipe); //lastly, save the recipe
		
		} catch (IOException e) {
			log.debug("Error occured " + e);
			
			e.printStackTrace();
		}
		
		
		
	}

}
