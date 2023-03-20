package com.ilyaevteev.shopapp.model;

import com.ilyaevteev.shopapp.model.auth.User;
import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Data
public class OrganizationPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "logo")
    private String logo;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
