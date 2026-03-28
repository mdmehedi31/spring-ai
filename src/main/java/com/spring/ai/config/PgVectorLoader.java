package com.spring.ai.config;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;

@Configuration
public class PgVectorLoader {

    private VectorStore vectorStore;
    private JdbcClient jdbcClient;

    @Value("classpath:/bdconstitutions.pdf")
    private Resource pdfResource;


    public PgVectorLoader(VectorStore vectorStore, JdbcClient jdbcClient) {
        this.vectorStore = vectorStore;
        this.jdbcClient = jdbcClient;
    }

    @PostConstruct
    public void init(){

        Integer count = jdbcClient.sql("select COUNT(*) from vector_store").query(Integer.class).single();
        System.out.println("Total added value to the pgvector: " + count);
        if(count == 0){
            System.out.println("No vector store found");

            PdfDocumentReaderConfig config = PdfDocumentReaderConfig.
                    builder()
                    .withPagesPerDocument(1)
                    .build();

            PagePdfDocumentReader reader= new PagePdfDocumentReader(pdfResource, config);
            var textSplitter = new TokenTextSplitter();

            var documents =textSplitter.apply(reader.get());

            var cleanedDocs = documents.stream()
                    .map(doc -> new org.springframework.ai.document.Document(
                            sanitize(doc.getFormattedContent()),
                            doc.getMetadata()
                    ))
                    .toList();

            vectorStore.accept(cleanedDocs);

            System.out.println("Vector store loaded sucessfully");
        }else{
            System.out.println("Vector store already loaded");
        }

    }

    private String sanitize(String input) {
        if (input == null) return null;

        return input
                .replace("\u0000", "")
                .replaceAll("[\\p{C}&&[^\n\t]]", "");
    }
}
