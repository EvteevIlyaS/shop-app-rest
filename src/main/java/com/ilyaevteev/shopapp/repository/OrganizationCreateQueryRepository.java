package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.OrganizationCreateQuery;

public interface OrganizationCreateQueryRepository {
    public void saveOrganizationCreateQuery(OrganizationCreateQuery organizationCreateQuery);

    OrganizationCreateQuery findById(Long id);

    void deleteById(Long id);


}
