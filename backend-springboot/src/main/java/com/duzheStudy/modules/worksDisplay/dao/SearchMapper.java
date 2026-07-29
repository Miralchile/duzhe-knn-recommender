package com.duzheStudy.modules.worksDisplay.dao;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SearchMapper {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    public List<String> suggestSearch(String prefix) throws IOException {
        SearchRequest request = new SearchRequest("works");

        SearchSourceBuilder builder = new SearchSourceBuilder();//生成构建器
        CompletionSuggestionBuilder suggestion  =
                SuggestBuilders.completionSuggestion("title.suggest").prefix(prefix).skipDuplicates(true).size(5);

        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("my_suggest", suggestion);
        builder.suggest(suggestBuilder);
        request.source(builder);
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        Suggest suggest = response.getSuggest();

        List<String> keywords = null;
        if (suggest != null) {
            keywords = new ArrayList<>();
            List<? extends Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option>> entries =
                    suggest.getSuggestion("my_suggest").getEntries();
            for (Suggest.Suggestion.Entry<? extends Suggest.Suggestion.Entry.Option> entry: entries) {
                for (Suggest.Suggestion.Entry.Option option: entry.getOptions()) {
                    String keyword = option.getText().string();
                    if (!StringUtils.isEmpty(keyword)) {
                        if (keywords.contains(keyword)) {
                            continue;
                        }
                        keywords.add(keyword);
                    }
                }
            }
        }
        return keywords;
    }

    public List<Map<String, Object>> conditionSearch(String keywords,Integer from,Integer size,Integer type,String sortField) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title",keywords);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(matchQueryBuilder);

        if(type==1||type==2){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("type", type);
            boolQueryBuilder.must(termQueryBuilder);
        }

        if(!sortField.equals("relation")&&sortField!=null){
            searchSourceBuilder.sort(SortBuilders.fieldSort(sortField).order(SortOrder.DESC));
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        String preTag = "<em style=\"color:#ff0000\">";
        String postTag = "</em>";
        highlightBuilder.field("title").preTags(preTag).postTags(postTag);

        searchSourceBuilder.query(boolQueryBuilder).highlighter(highlightBuilder).from(from).size(size);

        SearchRequest searchRequest = new SearchRequest(new String[]{"works"},searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (int i = 0; i < hits.length; i++) {
            Map<String,Object> map = hits[i].getSourceAsMap();
            HighlightField titleField = hits[i].getHighlightFields().get("title");
            if (titleField != null) {
                map.put("title",titleField.getFragments()[0].toString());
                map.put("_id",hits[i].getId());
            }
            result.add(map);
        }
        return result;
    }

    public Integer conditionSearchTotal(String keywords,Integer type) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title",keywords);

        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(matchQueryBuilder);

        if(type==1||type==2){
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("type", type);
            boolQueryBuilder.must(termQueryBuilder);
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        String preTag = "<em style=\"color:#ff0000\">";
        String postTag = "</em>";
        highlightBuilder.field("title").preTags(preTag).postTags(postTag);

        searchSourceBuilder.query(boolQueryBuilder).highlighter(highlightBuilder);

        SearchRequest searchRequest = new SearchRequest(new String[]{"works"},searchSourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHit[] hits = searchResponse.getHits().getHits();
        for (int i = 0; i < hits.length; i++) {
            Map<String,Object> map = hits[i].getSourceAsMap();
            HighlightField titleField = hits[i].getHighlightFields().get("title");
            if (titleField != null) {
                map.put("title",titleField.getFragments()[0].toString());
            }
            result.add(map);
        }
        return result.size();
    }
}
