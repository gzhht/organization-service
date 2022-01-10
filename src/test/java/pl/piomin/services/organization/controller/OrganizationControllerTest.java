package pl.piomin.services.organization.controller;

import com.mongodb.assertions.Assertions;
import org.junit.jupiter.api.Test;
import pl.piomin.services.organization.model.Organization;

class OrganizationControllerTest {

    @Test
    void test_for_report() {
        Organization organization = new Organization();
        organization.setId("999");
        Assertions.assertNotNull(organization);
    }
}