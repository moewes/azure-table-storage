/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.moewes.azure.table.storage.it;

import com.microsoft.azure.storage.StorageException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import net.moewes.TableStorage;

import java.net.URISyntaxException;
import java.util.UUID;

@Path("/")
@ApplicationScoped
public class AzureTableStorageResource {
    // add some rest methods here

    @Inject
    TableStorage tableStorage;

    String tableName = "X" + UUID.randomUUID().toString().replace('-', 'X');

    @GET
    @Path("/azure-table-storage")
    public String hello() {
        return "Hello azure-table-storage";
    }


    @Path("/tablestorage")
    @GET
    public String getTableStorage() {

        try {
            tableStorage.getCloudTable(tableName);
        } catch (StorageException | URISyntaxException e) {
            return e.getMessage();
        }
        return "OK";
    }
}
