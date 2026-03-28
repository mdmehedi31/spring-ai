package com.spring.ai.chat.service;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BDConstitutionService {

    private ChatClient chatClient;
    private VectorStore vectorStore;

    public BDConstitutionService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String askToBgc(String question) {
        Advisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .queryTransformers(RewriteQueryTransformer.builder()
                        .chatClientBuilder(chatClient.mutate())
                        .build())
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                        .similarityThreshold(0.50)
                        .vectorStore(vectorStore)
                        .build())
                .build();

        return  chatClient.prompt().advisors(
                retrievalAugmentationAdvisor).user(question).call().content();
    }

    public String simplfy(String prompt) {
        PromptTemplate template
                = new PromptTemplate(prompt);

        Map<String, Object> promptParams
                = new HashMap<>();

        promptParams.put("input", prompt);
        promptParams.put("documents", findSimilarData(prompt));

        return chatClient
                .prompt(template.create(promptParams))
                .call()
                .content();
    }

    private String findSimilarData(String q) {

        List<Document> documents =
                vectorStore.similaritySearch(SearchRequest.builder().query(q).topK(5).build());

        return documents
                .stream()
                .map(document -> document.getContentFormatter().toString())
                .collect(Collectors.joining());
    }
}
