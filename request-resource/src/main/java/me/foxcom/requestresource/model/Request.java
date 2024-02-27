package me.foxcom.requestresource.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.foxcom.requestresource.model.enums.RequestStatus;
import me.foxcom.requestresource.model.enums.RequestType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Entity
@Table(name = "request")
@NoArgsConstructor
@AllArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "sure_name")
    private String sureName;
    @Column(name = "patronymic")
    private String patronymic;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private RequestType type;
    @Column(name = "issue_date")
    private LocalDate issueDate;
    @Column(name = "series")
    private Integer series;
    @Column(name = "number")
    private Integer number;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<AskingResource> askingResourceList;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @Column(name = "request_date")
    private LocalDate requestDate;

}
