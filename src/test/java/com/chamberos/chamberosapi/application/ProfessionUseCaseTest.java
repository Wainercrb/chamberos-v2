package com.chamberos.chamberosapi.application;

import java.util.LinkedList;
import java.util.List;

import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.domain.Role;
import com.chamberos.chamberosapi.infrastructure.inputadapter.http.ProfessionAPI;
import com.chamberos.chamberosapi.infrastructure.outputport.ProfessionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProfessionAPI.class)
public class ProfessionUseCaseTest {
    @MockBean
    private ProfessionUseCase professionUseCase;

    @Mock
    private ProfessionRepository professionRepository;

    public static List<Profession> professionList = new LinkedList<>();

    @BeforeAll
    public static void init() {
        Profession profession1 = new Profession("1", "name A", Role.FULLTIME);
        Profession profession2 = new Profession("2", "name B", Role.FULLTIME);

        professionList.add(profession1);
        professionList.add(profession2);
    }

    @Test
    @DisplayName("Should Get The Repository Profession List From Profession Use Case")
    void shouldGetTheProfessionList() throws Exception {
        ProfessionUseCase professionUseCaseMock = mock(ProfessionUseCase.class);
        Pageable pageable = PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "name"));

        Mockito.when(professionUseCaseMock.getAll(Mockito.anyString(), Mockito.any(Pageable.class)))
                .thenReturn(professionList);

        Assertions.assertThat(professionUseCaseMock.getAll("", pageable))
                .isEqualTo(professionList);
    }
}
