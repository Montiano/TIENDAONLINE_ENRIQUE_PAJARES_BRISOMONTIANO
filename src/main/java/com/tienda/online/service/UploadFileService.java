package com.tienda.online.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {

	private String folder = "images//";
	
	/**
	 * Método para guardar la imagen asociada a un producto
	 * @param file El fichero de la imagen
	 * @return Devuelve la cadena producto.png que va a ser la imagen por defecto
	 * @throws IOException
	 */
	public String saveImage(MultipartFile file) throws IOException {
		
		if(!file.isEmpty()) {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(folder+file.getOriginalFilename());
			Files.write(path, bytes);
			return file.getOriginalFilename();
		} 
		
		return "producto.png";
	}
		
	/**
	 * Método para eliminar la imagen de la ruta
	 * @param nombre
	 */
	public void deleteImage(String nombre) {
		String ruta = "images//";
		File file = new File(ruta+nombre);
		file.delete();
	}
	
}
