package com.happycoding.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.happycoding.model.Provider;

@Repository
public interface ProviderRepository extends MongoRepository<Provider, String> {
}
