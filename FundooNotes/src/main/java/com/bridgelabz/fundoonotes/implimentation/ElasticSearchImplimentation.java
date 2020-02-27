package com.bridgelabz.fundoonotes.implimentation;

import java.io.IOException;
import java.util.Map;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ElasticSearchImplimentation implements ElasticSearchService{
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ElasticSearchConfig elasticSearchConfig;
	private String INDEX="springboot";
	private String TYPE="NotesInfo";
	@Override
	public String CreateNote(NoteInformation note) {
		Map<String, Object> notemapper=objectMapper.convertValue(note,Map.class);
		IndexRequest indexRequest=new IndexRequest(INDEX,TYPE,String.valueOf(note.getId())).source(notemapper);
		IndexResponse indexResponse=null;
		try {
			indexResponse=elasticSearchConfig.client().index(indexRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indexResponse.getResult().name();
	}

}
