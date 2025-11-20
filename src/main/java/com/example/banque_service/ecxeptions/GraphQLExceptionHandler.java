package com.example.banque_service.ecxeptions;



import graphql.ErrorClassification;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        return new GraphQLError() {
            @Override
            public String getMessage() {
                return ex.getMessage(); // message de l'exception
            }

            @Override
            public List<SourceLocation> getLocations() {
                return null; // emplacement dans la requête (optionnel)
            }

            @Override
            public List<Object> getPath() {
                return null; // chemin de la requête (optionnel)
            }

            @Override
            public ErrorClassification getErrorType() {
                return null; // type d'erreur (optionnel)
            }
        };
    }
}
