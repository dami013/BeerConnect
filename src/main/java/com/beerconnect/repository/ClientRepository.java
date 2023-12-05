package com.beerconnect.repository;

import com.beerconnect.assignment.ClientRecord;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientRecord, String> {

}
