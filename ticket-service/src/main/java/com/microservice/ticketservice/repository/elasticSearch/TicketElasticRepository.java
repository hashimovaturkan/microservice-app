package com.microservice.ticketservice.repository.elasticSearch;

import com.microservice.ticketservice.model.elasticSearch.TicketModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

public interface TicketElasticRepository extends ElasticsearchRepository<TicketModel, String> {
}
