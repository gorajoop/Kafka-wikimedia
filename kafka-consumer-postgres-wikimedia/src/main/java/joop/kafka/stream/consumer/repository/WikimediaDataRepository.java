package joop.kafka.stream.consumer.repository;

import joop.kafka.stream.consumer.entity.WikimediaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikimediaDataRepository extends JpaRepository<WikimediaData,Long> {
}
