package org.dreamcat.daily.script;

import org.apache.http.HttpHost;
import org.elasticsearch.client.Node;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Create by tuke on 2021/3/22
 */
@Configuration
public class MultipleElasticsearchConfig {

    @Value("${elastic.source.host}")
    private String sourceHost;

    @Value("${elastic.source.port:9200}")
    private int sourcePort;

    @Value("${elastic.target.host}")
    private String targetHost;

    @Value("${elastic.target.port:9200}")
    private int targetPort;

    @Bean(name = "sourceRestHighLevelClient")
    public RestHighLevelClient sourceRestHighLevelClient() {
        Node node = new Node(new HttpHost(sourceHost, sourcePort));
        RestClientBuilder restClientBuilder = RestClient.builder(node);
        return new RestHighLevelClient(restClientBuilder);
    }

    @Bean(name = "targetRestHighLevelClient")
    public RestHighLevelClient targetRestHighLevelClient() {
        Node node = new Node(new HttpHost(targetHost, targetPort));
        RestClientBuilder restClientBuilder = RestClient.builder(node);
        return new RestHighLevelClient(restClientBuilder);
    }
}

