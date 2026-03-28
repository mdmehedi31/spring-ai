package com.spring.ai.config;


import org.apache.pdfbox.multipdf.PDFCloneUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.List;

@Configuration
public class VectorLoader {

    @Value("classpath:/bdconstitutions.pdf")
    private Resource pdfResource;

    private static final Logger log = LoggerFactory.getLogger(VectorLoader.class);
    @Bean
    SimpleVectorStore simpleVectorStore(EmbeddingModel embeddingModel) {

        SimpleVectorStore vectorStore= SimpleVectorStore.builder(embeddingModel).build();

        //Here will store the vectore file
        File vectoreStoreFile =
                new File("C:\\myfile\\Code\\All java Code\\spring AI learning\\springAI\\springAI\\src\\main\\resources\\vector_resource.json");

        if(vectoreStoreFile.exists()) {
            log.debug("vector store exists");
         vectorStore.load(vectoreStoreFile);
        }else{
            log.debug("vector store not exists");
            log.debug("File process starting.....");
            PdfDocumentReaderConfig config = PdfDocumentReaderConfig.
                    builder()
                    .withPagesPerDocument(1)
                    .build();

            PagePdfDocumentReader reader= new PagePdfDocumentReader(pdfResource, config);

            var textSplitter = new TokenTextSplitter();
            List<Document> apply = textSplitter.apply(reader.get());

            vectorStore.add(apply);
            vectorStore.save(vectoreStoreFile);
            log.debug("vector store saved");
            log.debug("File process completed.....");
        }

        return vectorStore;
    }

}
