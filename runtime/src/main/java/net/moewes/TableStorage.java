package net.moewes;

import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class TableStorage {

    @ConfigProperty(name = "quarkus.storage-account.account-name")
    String accountName;
    @ConfigProperty(name = "quarkus.storage-account.account-key")
    String accountKey;
    @ConfigProperty(name = "quarkus.storage-account.use-azurite")
    boolean devMode;

    private CloudTableClient tableClient;

    @PostConstruct
    public void init() {
        try {
            CloudStorageAccount storageAccount =
                    CloudStorageAccount.parse(storageConnectionString());
            tableClient = storageAccount.createCloudTableClient();
        } catch (Exception e) {
            // Output the stack trace.
            e.printStackTrace();
        }
    }

    public CloudTable getCloudTable(String tableName) throws StorageException, URISyntaxException {
        CloudTable cloudTable = tableClient.getTableReference(tableName);
        cloudTable.createIfNotExists();
        return cloudTable;
    }

    private String storageConnectionString() {

        if (devMode) {
            return "UseDevelopmentStorage=true";
        } else {
            return "DefaultEndpointsProtocol=https;" +
                    "AccountName=" + accountName + ";" +
                    "AccountKey=" + accountKey;
        }
    }
}
