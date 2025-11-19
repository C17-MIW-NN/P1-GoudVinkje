package nl.miwnn.ch17.pixeldae.goudvinkje.service;

import nl.miwnn.ch17.pixeldae.goudvinkje.model.Image;
import nl.miwnn.ch17.pixeldae.goudvinkje.repositories.ImageRepository;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

/**
 * @author Annelies Hofman
 * process actions that require hte imageRepository
 */

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void saveImage(MultipartFile file) throws IOException {
        if (imageRepository.existsByFileName(file.getOriginalFilename())) {
            throw new IllegalIdentifierException(file.getOriginalFilename() + "already exists");
        }

        MediaType contentType = MediaType.IMAGE_JPEG;
        if (file.getContentType() != null) {
            contentType = MediaType.parseMediaType(file.getContentType());
        }

        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setContentType(contentType);
        image.setData(file.getBytes());

        imageRepository.save(image);
    }

    public void saveImage(ClassPathResource imageResource) throws IOException {
        Image image = new Image();
        image.setFileName(imageResource.getFilename());
        image.setContentType(MediaType.IMAGE_JPEG);
        image.setData(imageResource.getInputStream().readAllBytes());
        imageRepository.save(image);
    }

    public Image getImage(String fileName) {
        return imageRepository.findByFileName(fileName).orElseThrow(() -> new NoSuchElementException(fileName));
    }
}
