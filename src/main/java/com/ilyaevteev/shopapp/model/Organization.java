package com.ilyaevteev.shopapp.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "organizations")
public class Organization extends OrganizationPattern {
    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;
}
