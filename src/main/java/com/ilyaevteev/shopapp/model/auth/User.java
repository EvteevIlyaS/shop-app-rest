package com.ilyaevteev.shopapp.model.auth;

import com.ilyaevteev.shopapp.model.History;
import com.ilyaevteev.shopapp.model.Notification;
import com.ilyaevteev.shopapp.model.Organization;
import com.ilyaevteev.shopapp.model.OrganizationCreateQuery;
import lombok.Data;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private Long balance;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Organization> organizations;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<OrganizationCreateQuery> organizationsCreateQueries;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Notification> notifications;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<History> history;

}
