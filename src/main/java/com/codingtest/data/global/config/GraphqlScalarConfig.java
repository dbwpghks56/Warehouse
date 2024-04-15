package com.codingtest.data.global.config;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.Scalars;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;

@DgsComponent
public class GraphqlScalarConfig {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(ExtendedScalars.GraphQLLong);
    }

    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalarUpload(RuntimeWiring.Builder builder) {
        return builder.scalar(ApolloScalars.Upload);
    }
}
