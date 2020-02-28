package com.bridgelabz.fundoonotes.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * 
 * @author pradeep
 *
 */
@Configuration
//configuration file has been created to create a Transport client
//which connects to the Elasticsearch cluster node
public class ElasticSearchConfig {
	@Bean(destroyMethod = "close")
	public RestHighLevelClient client() {
		RestHighLevelClient restclient = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http")));
		return restclient;
	}
}
