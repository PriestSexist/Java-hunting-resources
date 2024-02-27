package me.foxcom.requestresource.repository;

import me.foxcom.requestresource.model.Request;
import me.foxcom.requestresource.model.enums.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByStatusIsNull();

    List<Request> findAllByStatusOrStatus(RequestStatus status1, RequestStatus status2);
}
