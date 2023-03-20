package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.Organization;
import com.ilyaevteev.shopapp.repository.OrganizationRepository;
import com.ilyaevteev.shopapp.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    @Autowired
    OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public Organization findById(Long id) {
        return organizationRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveOrganization(Organization organization) {
        organizationRepository.saveOrganization(organization);
    }

    @Override
    @Transactional
    public Organization findByName(String name) {
        return organizationRepository.findByName(name);
    }
}
