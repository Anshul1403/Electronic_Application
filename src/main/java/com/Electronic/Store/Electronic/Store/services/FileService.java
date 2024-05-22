package com.Electronic.Store.Electronic.Store.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.print.DocFlavor.INPUT_STREAM;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public String uploadFile(MultipartFile file,String path) throws IOException;
	
    InputStream getResource(String path,String name) throws FileNotFoundException;

}
