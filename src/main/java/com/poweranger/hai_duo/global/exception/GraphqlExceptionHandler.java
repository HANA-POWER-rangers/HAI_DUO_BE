package com.poweranger.hai_duo.global.exception;

import graphql.GraphQLError;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;

@Configuration
public class GraphqlExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof GraphQLException graphqlException) {
            return new GraphQLException(graphqlException.getMessage(),
                    graphqlException.getHttpStatus(),
                    graphqlException.getErrorType());
        }

        return null;
    }
}
