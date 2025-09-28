package com.pm.scoreservice.grpc;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import score.QuestionResult;
import score.ScoreResponse;
import score.ScoreServiceGrpc.ScoreServiceImplBase;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ScoreGrpcService extends ScoreServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(ScoreGrpcService.class);

    @Override
    public void createScoreAccount(score.ScoreRequest scoreRequest,
                                   StreamObserver<ScoreResponse> responseObserver) {
        log.info("CreateScoreAccount request received {}",  scoreRequest.toString());

        //Business logic - save to db,  perform calc
        QuestionResult result = QuestionResult.newBuilder()
                .setQuestionId("q1")
                .setCorrect(true)
                .setPointsAwarded(10)
                .setMaxPoints(10)
                .build();

        ScoreResponse response = ScoreResponse.newBuilder()
                .setTotalScore(34)
                .setMaxScore(100)
                .setBoardKey("science:easy")
                .addResults(result)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();

    }
}
