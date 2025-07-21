package com.easylink.easylink.repositories;

import com.easylink.easylink.entities.AssociativeEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AssociativeEntryRepository extends JpaRepository<AssociativeEntry, UUID> {

  //  List<AssociativeEntry> findAllById(List<UUID> ids);
}
