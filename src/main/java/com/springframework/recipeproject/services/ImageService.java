package com.springframework.recipeproject.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	void saveImageFile(String id, MultipartFile file);

}
