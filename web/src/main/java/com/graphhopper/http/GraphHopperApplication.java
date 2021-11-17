/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.http;

import com.graphhopper.http.cli.ImportCommand;
import com.graphhopper.http.cli.MatchCommand;
import com.graphhopper.http.resources.RootResource;
import com.graphhopper.navigation.NavigateResource;
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import javax.servlet.DispatcherType;
import java.util.EnumSet;

public final class GraphHopperApplication extends Application<GraphHopperServerConfiguration> {

    public static void main(String[] args) throws Exception {
        new GraphHopperApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<GraphHopperServerConfiguration> bootstrap) {
        bootstrap.addBundle(new GraphHopperBundle());
        bootstrap.addBundle(new RealtimeBundle());
        bootstrap.addCommand(new ImportCommand());
        bootstrap.addCommand(new MatchCommand());
        bootstrap.addBundle(new AssetsBundle("/com/graphhopper/maps/", "/maps/", "index.html"));
        // see this link even though its outdated?! // https://www.webjars.org/documentation#dropwizard
        bootstrap.addBundle(new AssetsBundle("/META-INF/resources/webjars", "/webjars/", null, "webjars"));
    }

    @Override
    public void run(GraphHopperServerConfiguration configuration, Environment environment) {
        ModelConverters.getInstance().addConverter(new ObjectNodeConverter());
        environment.jersey().register(new RootResource());
        environment.jersey().register(new OpenApiResource());
        environment.jersey().register(NavigateResource.class);
        environment.servlets().addFilter("cors", CORSFilter.class).addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "*");
    }
}
