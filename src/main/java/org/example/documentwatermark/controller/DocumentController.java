package org.example.documentwatermark.controller;

import org.example.documentwatermark.model.DocumentEntity;
import org.example.documentwatermark.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;

    @Autowired
    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> addWatermark(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(documentService.addDocument(file));
    }
}
