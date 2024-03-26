package com.microservice.ticketservice.repository.elasticSearch;

import com.microservice.ticketservice.model.elasticSearch.TicketModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TicketElasticRepository extends ElasticsearchRepository<TicketModel, String> {
}
