package eu.europeana.redirects.service.config;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import eu.europeana.corelib.lookup.impl.CollectionMongoServerImpl;
import eu.europeana.corelib.lookup.impl.EuropeanaIdMongoServerImpl;
import eu.europeana.corelib.tools.lookuptable.CollectionMongoServer;
import eu.europeana.corelib.tools.lookuptable.EuropeanaIdMongoServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;

import java.io.IOException;
import java.util.Properties;

/**
 * Configuration class for Service redirects class
 *
 * Created by ymamakis on 1/13/16.
 */

public class ServiceConfig {

    private Properties props;
    private EuropeanaIdMongoServer mongoServer;
    private CloudSolrServer productionSolrServer;




    private CollectionMongoServer collectionMongoServer;


    public ServiceConfig(){
        try {
            props = new Properties();
            props.load(ServiceConfig.class.getClassLoader().getResourceAsStream("redirects.properties"));
            Mongo mongo = new MongoClient(props.getProperty("mongo.host"),
                    Integer.parseInt(props.getProperty("mongo.port")));
            mongoServer = new EuropeanaIdMongoServerImpl(mongo,props.getProperty("mongo.db"),
                    props.getProperty("mongo.username"),props.getProperty("mongo.password"));
            collectionMongoServer = new CollectionMongoServerImpl(mongo,props.getProperty("mongo.collections.db"),
                    props.getProperty("mongo.username"),props.getProperty("mongo.password"));


            LBHttpSolrServer lbTargetProduction = new LBHttpSolrServer(props.getProperty("solr.production"));
            productionSolrServer = new CloudSolrServer(props.getProperty("zookeeper.production"), lbTargetProduction);
            productionSolrServer.setDefaultCollection(props.getProperty("solr.production.core"));
            productionSolrServer.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public EuropeanaIdMongoServer getMongoServer() {
        return mongoServer;
    }

    public void setMongoServer(EuropeanaIdMongoServer mongoServer) {
        this.mongoServer = mongoServer;
    }



    public CloudSolrServer getProductionSolrServer() {
        return productionSolrServer;
    }

    public void setProductionSolrServer(CloudSolrServer productionSolrServer) {
        this.productionSolrServer = productionSolrServer;
    }


    public CollectionMongoServer getCollectionMongoServer() {
        return collectionMongoServer;
    }

    public void setCollectionMongoServer(CollectionMongoServer collectionMongoServer) {
        this.collectionMongoServer = collectionMongoServer;
    }
}
