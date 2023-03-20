package com.ilyaevteev.shopapp.dto;


import com.ilyaevteev.shopapp.model.Organization;
import lombok.Data;

@Data
public class OrganizationDto {
    private Long id;

    private String name;

    private String description;

    private String logo;

    private Long user;

    public static OrganizationDto fromOrganization(Organization organization) {
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setId(organization.getId());
        organizationDto.setName(organization.getName());
        organizationDto.setDescription(organization.getDescription());
        organizationDto.setLogo(organization.getLogo());
        organizationDto.setUser(organization.getUser().getId());

        return organizationDto;
    }
}
