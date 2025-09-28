package com.pm.playerservices.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import score.Answer;
import score.ScoreRequest;
import score.ScoreResponse;
import score.ScoreServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class ScoreServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(ScoreServiceGrpcClient.class);
    //when we call billing service using blocking stub it will wait for a reponse from it first
    private final ScoreServiceGrpc.ScoreServiceBlockingStub blockingStub;

    public ScoreServiceGrpcClient(
        @Value("${score.service.address:localhost}") String serverAddress,
        @Value("${score.service.grpc.port:9001}") int serverPort) {
        log.info("Connecting to Score Service Grpc Service at {}:{}", serverAddress, serverPort);

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();

        blockingStub = ScoreServiceGrpc.newBlockingStub(channel);

    }
    public ScoreResponse createScoreAccount(String userId, String paperId, String paperToken, Answer answer) {
        // Build the request (add single answer into the repeated field)
        ScoreRequest request = ScoreRequest.newBuilder()
                .setUserId(userId)
                .setPaperId(paperId)
                .setPaperToken(paperToken)
                .addAnswers(answer)
                .build();

        ScoreResponse response = blockingStub.createScoreAccount(request);
        log.info("Received response from Score Service via Grpc: {}", response);
        return response;
    }
}
