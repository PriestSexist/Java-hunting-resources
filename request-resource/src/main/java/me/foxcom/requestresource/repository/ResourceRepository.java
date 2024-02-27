package me.foxcom.requestresource.repository;

import me.foxcom.requestresource.model.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {

    Resource getResourceByAreaAndName(String area, String name);
}
