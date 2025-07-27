package com.poweranger.hai_duo.global.exception;

import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import lombok.Getter;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Getter
public class CustomGraphQLException extends RuntimeException implements GraphQLError {

    private final ErrorStatus errorStatus;
    private final ErrorClassification errorType;
    private final String detailMessage;
    private final String timestamp;

    public CustomGraphQLException(ErrorStatus errorStatus, ErrorClassification errorType) {
        this(errorStatus, errorType, null);
    }

    public CustomGraphQLException(ErrorStatus errorStatus, ErrorClassification errorType, String detailMessage) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.errorType = errorType;
        this.detailMessage = detailMessage;
        this.timestamp = OffsetDateTime.now().toString();
    }

    @Override
    public String getMessage() {
        return errorStatus.getMessage();
    }

    @Override
    public ErrorClassification getErrorType() {
        return errorType;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Map.of(
                "httpStatus", errorStatus.getHttpStatus().value(),
                "errorCode", errorStatus.getCode(),
                "errorType", errorType.toString(),
                "exceptionMessage", detailMessage != null ? detailMessage : super.getMessage(),
                "exception", this.getClass().getName(),
                "timestamp", timestamp
        );
    }

    @Override
    public List<Object> getPath() {
        return GraphQLError.super.getPath();
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
