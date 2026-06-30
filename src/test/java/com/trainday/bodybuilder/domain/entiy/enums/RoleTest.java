package com.trainday.bodybuilder.domain.entiy.enums;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleTest {
    @Test
    void shouldReturnRole_whenValidValue(){
        Role resultAdmin   = Role.fromValue("ADMIN");
        Role resultDoctor  = Role.fromValue("MÉDICO");
        Role resultPersTra = Role.fromValue("EDUCADOR_FISICO");
        Role resultNutr    = Role.fromValue("NUTRICIONISTA");
        Role resultLTec    = Role.fromValue("TEC_LAB");
        Role resultAth     = Role.fromValue("ATLETA");

        assertEquals(Role.ADMIN,            resultAdmin);
        assertEquals(Role.DOCTOR,           resultDoctor);
        assertEquals(Role.PERSONAL_TRAINER, resultPersTra);
        assertEquals(Role.NUTRITIONIST,     resultNutr);
        assertEquals(Role.LAB_TECHNIC,      resultLTec);
        assertEquals(Role.ATHLETE,          resultAth);
    }

    @Test
    void shouldThrowException_whenInvalidValue() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Role.fromValue("invalid"); // ← valor inválido que vai lançar a exceção
        });
        assertEquals("Role inválida: invalid", exception.getMessage());
    }

    @Test
    void shouldReturnState_whenGetState(){
        String result = Role.ATHLETE.getState();
        assertEquals("ATLETA", result);
    }
}
