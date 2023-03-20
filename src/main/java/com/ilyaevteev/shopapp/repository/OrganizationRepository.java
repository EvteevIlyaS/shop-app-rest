package com.ilyaevteev.shopapp.repository;


import com.ilyaevteev.shopapp.model.Organization;

public interface OrganizationRepository {
    Organization findByName(String name);

    Organization findById(Long id);

    public void saveOrganization(Organization organization);



}
