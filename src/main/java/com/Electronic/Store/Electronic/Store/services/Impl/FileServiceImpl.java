package com.Electronic.Store.Electronic.Store.services.Impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Electronic.Store.Electronic.Store.exceptions.BadApiRequest;
import com.Electronic.Store.Electronic.Store.services.FileService;

import ch.qos.logback.classic.Logger;

@Service
public class FileServiceImpl implements FileService {
	
	
	@Override
	public String uploadFile(MultipartFile file, String path) throws IOException {
		
		String originalFilenameString = file.getOriginalFilename();
		String filename = UUID.randomUUID().toString();
		String extension = originalFilenameString.substring(originalFilenameString.lastIndexOf("."));
		String filenamewithExtension = filename + extension;
		String fullpathwithfilename = path+filenamewithExtension;
		
		if(extension.equalsIgnoreCase(".png") || extension.equalsIgnoreCase(".jpg") || extension.equalsIgnoreCase(".jpeg"))
		{
			File folder = new File(path);
			if(!folder.exists()) {
				//create
				folder.mkdirs();
			}
			
			//upload

			Files.copy(file.getInputStream(), Paths.get(fullpathwithfilename));
			return filenamewithExtension;
		}
		else {
			throw new BadApiRequest("File With This" + extension + " Not Allowed");
		}
		
	}

	@Override
	public InputStream getResource(String path, String name) throws FileNotFoundException {
	
		String fullPath = path + File.separator +name;
		InputStream inputStream = new FileInputStream(fullPath);
		
		return inputStream;
	}

}
