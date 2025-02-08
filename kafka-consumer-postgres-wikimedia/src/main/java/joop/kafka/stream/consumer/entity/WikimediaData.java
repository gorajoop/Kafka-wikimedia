package joop.kafka.stream.consumer.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "wikidata")
@Data
public class WikimediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String message;
}
