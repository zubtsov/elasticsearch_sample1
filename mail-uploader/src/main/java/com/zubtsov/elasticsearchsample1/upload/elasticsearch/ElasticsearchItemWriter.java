package com.zubtsov.elasticsearchsample1.upload.elasticsearch;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

//TODO: refactor & make writer restartable (bulk operations may partially fail)
public class ElasticsearchItemWriter implements ItemWriter<XContentBuilder>, ItemStream {

    @Autowired
    private TransportClient client;

    private @Value("${elasticsearch.index.name}") String indexName;
    private @Value("${elasticsearch.type.name}") String typeName;

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {

    }

    @Override
    public void close() throws ItemStreamException {

    }

    @Override
    public void write(List<? extends XContentBuilder> items) throws Exception {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        for (XContentBuilder item : items) {
            bulkRequestBuilder.add(client.prepareIndex(indexName, typeName)
                    .setSource(item)
                    .setOpType(DocWriteRequest.OpType.INDEX)
            );
        }

        //TODO: handle the response
        BulkResponse bulkResponse = bulkRequestBuilder.get();
    }
}