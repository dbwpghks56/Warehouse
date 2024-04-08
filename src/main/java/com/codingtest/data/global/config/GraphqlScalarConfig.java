package com.codingtest.data.global.config;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.Scalars;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;

@DgsComponent
public class GraphqlScalarConfig {
    @DgsRuntimeWiring
    public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
        return builder.scalar(ExtendedScalars.GraphQLLong);
    }
}
