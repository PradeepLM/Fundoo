package com.bridgelabz.fundoonotes.implimentation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfiguration;
import com.bridgelabz.fundoonotes.entity.NoteInformation;
import com.bridgelabz.fundoonotes.service.IElasticSearchService;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 
 * @author pradeep
 * @purpose: its used for create delete and search by title using elastic search
 */
@Service
public class ElasticSearchImplimentation implements IElasticSearchService{
	@Autowired
	private ObjectMapper objectMapper;//provides functionality for reading and writing JSON
	@Autowired
	private ElasticSearchConfiguration elasticSearchConfig;
	private String INDEX="springboot";
	private String TYPE="NotesInfo";
	
	@Override//its creates notes using elastic search
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
	@Override
	//its delete notes under elastic search
	public String deleteNote(NoteInformation noteinf) {
		Map<String, Object> notemapper=objectMapper.convertValue(noteinf, Map.class);
		DeleteRequest deleteRequest=new DeleteRequest(INDEX,TYPE,String.valueOf(noteinf.getId()));
		DeleteResponse deleteResponse=null;
		try {
			deleteResponse=elasticSearchConfig.client().delete(deleteRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();
	}
	@Override//its search by id notes under elastic search
	public NoteInformation searchByNoteId(Long noteId) throws IOException {
		
		GetRequest getRequest = new GetRequest(INDEX, TYPE, noteId.toString());

        GetResponse getResponse = elasticSearchConfig.client().get(getRequest, RequestOptions.DEFAULT);
        Map<String, Object> resultMap = getResponse.getSource();

        return objectMapper.convertValue(resultMap, NoteInformation.class);
	}
	@Override////its search by title notes under elastic search
	public List<NoteInformation> searchByTitle(String title) throws IOException {
		SearchRequest searchRequest = new SearchRequest("springboot");
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(QueryBuilders.matchQuery("title",title));
		searchRequest.source(searchSourceBuilder);
		SearchResponse searchResponse=null;
		try {
			searchResponse= elasticSearchConfig.client().search(searchRequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return getResult(searchResponse);
	}
	private List<NoteInformation> getResult(SearchResponse searchResponse) {
		SearchHit[] searchhits = searchResponse.getHits().getHits();
		List<NoteInformation> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), NoteInformation.class)));
		}
		return notes;
	}
	


}
