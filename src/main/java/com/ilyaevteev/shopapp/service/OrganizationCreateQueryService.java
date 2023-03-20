package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.OrganizationCreateQuery;

public interface OrganizationCreateQueryService {
    void saveOrganizationCreateQuery(OrganizationCreateQuery organizationCreateQuery);

    OrganizationCreateQuery findById(Long id);

    void deleteById(Long id);

}
