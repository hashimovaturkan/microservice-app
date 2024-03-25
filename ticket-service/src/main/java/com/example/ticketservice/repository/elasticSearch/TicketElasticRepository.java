package com.example.ticketservice.repository.elasticSearch;

import com.example.ticketservice.model.elasticSearch.TicketModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TicketElasticRepository extends ElasticsearchRepository<TicketModel, String> {
}
