package com.ilyaevteev.shopapp.service.impl;

import com.ilyaevteev.shopapp.model.OrganizationCreateQuery;
import com.ilyaevteev.shopapp.repository.OrganizationCreateQueryRepository;
import com.ilyaevteev.shopapp.service.OrganizationCreateQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class OrganizationCreateQueryServiceImpl implements OrganizationCreateQueryService {
    @Autowired
    OrganizationCreateQueryRepository organizationCreateQueryRepository;

    @Override
    @Transactional
    public void saveOrganizationCreateQuery(OrganizationCreateQuery organizationCreateQuery) {
        organizationCreateQueryRepository.saveOrganizationCreateQuery(organizationCreateQuery);
    }

    @Override
    @Transactional
    public OrganizationCreateQuery findById(Long id) {
        return organizationCreateQueryRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        organizationCreateQueryRepository.deleteById(id);
    }
}
