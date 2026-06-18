package com.trainday.bodybuilder.domain.entiy.enums;

import com.trainday.bodybuilder.domain.model.enums.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RoleTest {
    @Test
    void shouldReturnRole_whenValidValue(){
        Role resultAdmin = Role.formValue("ADMIN");
        Role resultDoctor = Role.formValue("MÉDICO");
        Role resultPersTra = Role.formValue("EDUCADOR_FISICO");
        Role resultNutr = Role.formValue("NUTRICIONISTA");
        Role resultLTec = Role.formValue("TEC_LAB");
        Role resultAth = Role.formValue("ATLETA");

        assertEquals(Role.ADMIN, resultAdmin);
        assertEquals(Role.DOCTOR, resultDoctor);
        assertEquals(Role.DOCTOR, resultDoctor);
        assertEquals(Role.PERSONAL_TRAINER, resultPersTra);
        assertEquals(Role.NUTRITIONIST, resultNutr);
        assertEquals(Role.LAB_TECHNIC, resultLTec);
        assertEquals(Role.ATHLETE, resultAth);
    }

    @Test
    void shouldThrowException_whenInvalidValue(){
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Role.formValue("invalid");
        });
        assertEquals("Identity invalid: invalid", exception.getMessage());
    }

    @Test
    void shouldReturnState_whenGetState(){
        String result = Role.ATHLETE.getState();
        assertEquals("ATLETA", result);
    }

}
