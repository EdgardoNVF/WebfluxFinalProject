package com.bancolombia.training.customers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StarterSanityTest {
    @Test
    void applicationClassIsPresent() {
        // Smoke test que no necesita levantar Keycloak ni cargar el contexto Spring.
        assertNotNull(CustomerServiceApplication.class);
    }
}
