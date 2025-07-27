package com.poweranger.hai_duo.global.exception;

import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;
import java.util.List;
import java.util.Map;

@Getter
public class GraphQLException extends RuntimeException implements GraphQLError {

    private final String message;
    private final int httpStatus;
    private final ErrorClassification errorType;

    public GraphQLException(String message, int httpStatus, ErrorClassification errorType) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
        this.errorType = errorType;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorClassification getErrorType() {
        return errorType;
    }

    @Override
    public List<Object> getPath() {
        return GraphQLError.super.getPath();
    }

    @Override
    public Map<String, Object> toSpecification() {
        return GraphQLError.super.toSpecification();
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of(
                "httpStatus", httpStatus,
                "errorType", errorType.toString()
        );
    }

}
