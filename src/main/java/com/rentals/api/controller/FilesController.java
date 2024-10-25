package com.rentals.api.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.rentals.api.Exceptions.ResourceNotFoundException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@Tag(name = "Images API", description = "Contains operations to download images. (the upload is done througth the Rental API)")
public class FilesController {

    private final Path imageLocation = Paths.get("files");

    @Operation(summary = "Public endpoint to dowload an image, from his unique id")
    @GetMapping("/files/{imageName}")
    public ResponseEntity<Resource> getImage(
            @Parameter(description = "the filename with the extension of the target image", example = "filename.png") @PathVariable String imageName)
            throws MalformedURLException {

        Path filePath = imageLocation.resolve(imageName).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists() || !resource.isReadable())
            throw new ResourceNotFoundException("Can't retreive img " + imageName);

        HttpHeaders headers = new HttpHeaders();

        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
