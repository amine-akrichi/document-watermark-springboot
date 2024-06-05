package org.example.documentwatermark.service;

import com.spire.doc.*;
import com.spire.doc.documents.WatermarkLayout;
import org.example.documentwatermark.model.DocumentEntity;
import org.example.documentwatermark.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    @Autowired
    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }


    public byte[] addWatermark(MultipartFile file) throws IOException {
        Document document = new Document();
        document.loadFromStream(new ByteArrayInputStream(file.getBytes()), FileFormat.Docx);

        Section section = document.getSections().get(0);

//      //Add text watermark
//        TextWatermark txtWatermark = new TextWatermark();
//
//
//        txtWatermark.setText("Confidential");
//        txtWatermark.setFontSize(40);
//        txtWatermark.setColor(Color.red);
//        txtWatermark.setLayout(WatermarkLayout.Diagonal);
//
//
//        section.getDocument().setWatermark(txtWatermark);

        //Add picture watermark
        PictureWatermark picture = new PictureWatermark();
        picture.setPicture("src/main/resources/images/ooredoo.png");
        picture.setScaling(100);
        picture.isWashout(false);
        document.setWatermark(picture);
        document.saveToFile("out/result_" + file.getOriginalFilename(), FileFormat.Docx);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.saveToStream(outputStream, FileFormat.Docx);
        return outputStream.toByteArray();
    }

    public DocumentEntity addDocument(MultipartFile file) {
        try {
            return documentRepository.save(DocumentEntity.builder().content(addWatermark(file)).build());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
