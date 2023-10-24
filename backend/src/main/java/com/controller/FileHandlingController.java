package com.controller;

import com.model.File;
import com.repository.FileRepository;
import com.service.StorageService;

import org.springframework.core.io.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/files")
public class FileHandlingController {

    private final StorageService storageService;
    private final FileRepository fileRepository;

    @Autowired
    public FileHandlingController(StorageService storageService, FileRepository fileRepository) {
        this.storageService = storageService;
        this.fileRepository = fileRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> streamFile(@PathVariable Long id) throws IOException {

        Optional<File> fileEntity = fileRepository.findById(id);
        
        if(fileEntity.isPresent()) {
            Resource file = (Resource) storageService.serve(fileEntity.get());
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public String handleFileUpload(@RequestParam("file")MultipartFile file) throws IOException {

        File fileEntity = storageService.store(file);
        fileRepository.save(fileEntity);

        return fileEntity.getPath().toString();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> fileUpdate(@RequestParam("file") MultipartFile file, @RequestParam("fileId") Long fileId) throws IOException{
        
        File fileEntity = storageService.updateFile(file, fileId);

        if (fileEntity != null) {
            return ResponseEntity.ok("Datei aktualisiert mit ID: " + fileEntity.getId());
        } else {
            return ResponseEntity.ok("Keine Änderungen an dem Bild vorgenommen.");
        }
    }   
}