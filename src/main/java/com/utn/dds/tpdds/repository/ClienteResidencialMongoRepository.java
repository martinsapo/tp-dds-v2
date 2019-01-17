package com.utn.dds.tpdds.repository;

import com.utn.dds.tpdds.model.ClienteResidencial;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClienteResidencialMongoRepository extends MongoRepository<ClienteResidencial, String> {
}
