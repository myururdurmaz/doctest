/*
 * Copyright 2013, devbliss GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.devbliss.doctest.httpfactory;

import static com.devbliss.doctest.httpfactory.HttpConstants.HANDLE_REDIRECTS;

import com.google.inject.Inject;
import de.devbliss.apitester.factory.PutFactory;
import de.devbliss.apitester.factory.impl.EntityBuilder;
import java.io.IOException;
import java.net.URI;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

/**
 * Implements a PUT HTTP request which does not handle any redirect.
 * 
 * @author bmary, hschuetz
 * 
 */
public class PutWithoutRedirectImpl implements PutFactory {

    private final EntityBuilder entityBuilder;

    @Inject
    public PutWithoutRedirectImpl(EntityBuilder entityBuilder) {
        this.entityBuilder = entityBuilder;
    }

    public HttpPut createPutRequest(URI uri, Object payload) throws IOException {
        HttpPut httpPut = new HttpPut(uri);
        HttpParams params = new BasicHttpParams();
        params.setParameter(HANDLE_REDIRECTS, false);
        httpPut.setParams(params);

        if (payload != null) {
            httpPut.setEntity(entityBuilder.buildEntity(payload));
        }

        return httpPut;
    }

}
