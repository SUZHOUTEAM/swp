package com.mlsc.solr;

import com.mlsc.waste.utils.PropertyUtil;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.UpdateResponse;

import java.io.IOException;

/**
 * 全文索引类
 */
public class SimpleSolr {

    private String solrUrl;
    private SolrClient client;
    private int num = 10;
    private String zkUrl;
    private String collectionName;
    private Logger logger = Logger.getLogger(SimpleSolr.class);

    /**
     * 创建索引
     * @return
     */
    private SolrClient createNewSolrClient() {
        try {
            System.out.println("server address:" + solrUrl);

            HttpSolrClient client = new HttpSolrClient.Builder(solrUrl).build();
            client.setConnectionTimeout(30000);
            client.setDefaultMaxConnectionsPerHost(100);
            client.setMaxTotalConnections(100);
            client.setSoTimeout(30000);
            return client;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private SolrClient createCouldSolrClient() {
        CloudSolrClient client = new CloudSolrClient.Builder().withZkHost(zkUrl).build();
        client.setZkClientTimeout(30000);
        client.setZkConnectTimeout(50000);
        client.setDefaultCollection(collectionName);
        return client;
    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleSolr(String enterType) {
        this.solrUrl = PropertyUtil.getValue("SOLR_URL_" + enterType);
        this.client = createNewSolrClient();
        this.num = 2;
    }

    public SimpleSolr(String zkUrl, int num, String collection) {
        this.zkUrl = zkUrl;
        this.num = num;
        collectionName = collection;
        this.client = createCouldSolrClient();
    }


    public SolrClient getClient() {
        return client;
    }

    public void deleteById(String id) {
        try {
            UpdateResponse rsp = client.deleteById(id);
            client.commit();
        } catch (SolrServerException | IOException e) {
            logger.error("solr 删除失败" + id, e);
        }
    }


}
