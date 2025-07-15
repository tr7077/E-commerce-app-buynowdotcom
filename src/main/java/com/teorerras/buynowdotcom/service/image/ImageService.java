package com.teorerras.buynowdotcom.service.image;

import com.teorerras.buynowdotcom.dtos.ImageDto;
import com.teorerras.buynowdotcom.model.Image;
import com.teorerras.buynowdotcom.model.Product;
import com.teorerras.buynowdotcom.repository.ImageRepository;
import com.teorerras.buynowdotcom.service.product.IProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements  IImageService{

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    public void deleteImageById(Long imageId) {
        imageRepository.findById(imageId).
                ifPresentOrElse(imageRepository :: delete, () ->{
                    throw new EntityNotFoundException("Image not found");
                });
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image existingImage = getImageById(imageId);
        try {
            existingImage.setFileName(file.getOriginalFilename());
            existingImage.setFileType(file.getContentType());
            existingImage.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(existingImage);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public List<ImageDto> saveImages(Long productId, List<MultipartFile> files) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImages = new ArrayList<>();

        for(MultipartFile file: files){
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/image/download/";
                String downloadUrl = buildDownloadUrl+image.getId();
                image.setDownloadUrl(downloadUrl);

                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(buildDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());

                savedImages.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImages;
    }
}
