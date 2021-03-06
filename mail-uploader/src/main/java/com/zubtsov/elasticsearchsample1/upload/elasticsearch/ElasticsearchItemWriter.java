package com.zubtsov.elasticsearchsample1.upload.elasticsearch;

import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

//TODO: refactor & make writer restartable (bulk operations may partially fail)
public class ElasticsearchItemWriter implements ItemStreamWriter<XContentBuilder> {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchItemWriter.class);

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

    //TODO: add ID (From + Sent Date) (use jackson databind to convert email message to String)
    @Override
    public void write(List<? extends XContentBuilder> items) {
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();

        for (XContentBuilder item : items) {
            bulkRequestBuilder.add(client.prepareIndex(indexName, typeName)
                    .setSource(item)
                    .setOpType(DocWriteRequest.OpType.INDEX)
            );
        }

        //TODO: handle the response
        BulkResponse bulkResponse = bulkRequestBuilder.get();

        logger.debug("{} items has been successfully written to Elasticsearch", items.size());
    }
}
