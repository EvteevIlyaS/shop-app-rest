package com.ilyaevteev.shopapp.service;


import com.ilyaevteev.shopapp.model.Organization;

public interface OrganizationService {
    Organization findById(Long id);

    void saveOrganization(Organization organization);

    Organization findByName(String name);
}
