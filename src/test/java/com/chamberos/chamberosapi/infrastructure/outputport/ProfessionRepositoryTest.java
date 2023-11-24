package com.chamberos.chamberosapi.infrastructure.outputport;

import java.util.LinkedList;
import java.util.List;
import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.domain.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class ProfessionRepositoryTest {
    @Autowired
    private ProfessionRepository professionRepository;

    @Test
    public void shouldGetTheProfessionListByFullName() {
        List<Profession> professionList = new LinkedList<>();
        Profession professionA = new Profession("1", "name A", Role.FULLTIME);
        Profession professionB = new Profession("2", "name B", Role.TEMPORAL);
        Profession professionC = new Profession("3", "name C", Role.FULLTIME);
        Profession professionD = new Profession("4", "name D", Role.TEMPORAL);

        professionList.add(professionA);
        professionList.add(professionB);
        professionList.add(professionC);
        professionList.add(professionD);

        professionRepository.saveAll(professionList);

        List<Profession> result = professionRepository.findByNameRegex(professionD.getName(), Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo(professionD.getName());
        assertThat(result.get(0).getId()).isEqualTo(professionD.getId());
        assertThat(result.get(0).getRole().toString()).isEqualTo(professionD.getRole().toString());
    }

    @Test
    public void shouldGetTheProfessionListBySomeCharacters() {
        List<Profession> professionList = new LinkedList<>();
        Profession professionA = new Profession("1", "name A", Role.FULLTIME);
        Profession professionB = new Profession("2", "name B", Role.TEMPORAL);
        Profession professionC = new Profession("3", "name C", Role.FULLTIME);
        Profession professionD = new Profession("4", "name D", Role.TEMPORAL);

        professionList.add(professionA);
        professionList.add(professionB);
        professionList.add(professionC);
        professionList.add(professionD);

        professionRepository.saveAll(professionList);

        List<Profession> result = professionRepository.findByNameRegex("name", Pageable.unpaged());

        assertThat(result).isNotNull();
        assertThat(result).hasSize(professionList.size());
    }

    @Test
    public void shouldGetTheProfessionById() {
        List<Profession> professionList = new LinkedList<>();
        Profession professionA = new Profession("1", "name A", Role.FULLTIME);
        Profession professionB = new Profession("2", "name B", Role.TEMPORAL);
        Profession professionC = new Profession("3", "name C", Role.FULLTIME);
        Profession professionD = new Profession("4", "name D", Role.TEMPORAL);

        professionList.add(professionA);
        professionList.add(professionB);
        professionList.add(professionC);
        professionList.add(professionD);

        professionRepository.saveAll(professionList);

        Profession result = professionRepository.getById(professionB.getId());

        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo(professionB.getName());
        assertThat(result.getId()).isEqualTo(professionB.getId());
        assertThat(result.getRole().toString()).isEqualTo(professionB.getRole().toString());
    }
}
