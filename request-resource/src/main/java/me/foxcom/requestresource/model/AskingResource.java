package me.foxcom.requestresource.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.foxcom.requestresource.model.enums.ResourceStatus;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "asking_resource")
@NoArgsConstructor
@AllArgsConstructor
public class AskingResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    private Resource resource;
    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
    @Column(name = "count")
    private int count;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ResourceStatus status;
}
