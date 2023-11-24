package com.chamberos.chamberosapi.infrastructure.outputport;

import java.util.LinkedList;
import java.util.List;
import com.chamberos.chamberosapi.domain.Profession;
import com.chamberos.chamberosapi.domain.User;
import com.chamberos.chamberosapi.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Point;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldGetTheUserById() {
        Point point = new Point(0, 0);
        List<Profession> professionList = new LinkedList<>();
        List<UserRole> roleList = new LinkedList<>();
        List<User> userList = new LinkedList<>();
        User userA = new User("1", "user A", "email1@gmail.com", "pass1", true, point, professionList, roleList);
        User userB = new User("2", "user B", "email2@gmail.com", "pass2", true, point, professionList, roleList);
        User userC = new User("3", "user C", "email3@gmail.com", "pass3", true, point, professionList, roleList);
        User userD = new User("4", "user D", "email4@gmail.com", "pass4", true, point, professionList, roleList);

        userList.add(userA);
        userList.add(userB);
        userList.add(userC);
        userList.add(userD);

        userRepository.saveAll(userList);

        User result = userRepository.getById(userC.getId());

        assertThat(result).isNotNull();
        assertThat(result.getFullName()).isEqualTo(userC.getFullName());
        assertThat(result.getId()).isEqualTo(userC.getId());
        assertThat(result.getPassword()).isEqualTo(null);
    }

    @Test
    public void shouldGetTheUserByEmail() {
        Point point = new Point(0, 0);
        List<Profession> professionList = new LinkedList<>();
        List<UserRole> roleList = new LinkedList<>();
        List<User> userList = new LinkedList<>();
        User userA = new User("1", "user A", "email1@gmail.com", "pass1", true, point, professionList, roleList);
        User userB = new User("2", "user B", "email2@gmail.com", "pass2", true, point, professionList, roleList);
        User userC = new User("3", "user C", "email3@gmail.com", "pass3", true, point, professionList, roleList);
        User userD = new User("4", "user D", "email4@gmail.com", "pass4", true, point, professionList, roleList);

        userList.add(userA);
        userList.add(userB);
        userList.add(userC);
        userList.add(userD);

        userRepository.saveAll(userList);

        User result = userRepository.findByEmail(userC.getEmail());

        assertThat(result).isNotNull();
        assertThat(result.getFullName()).isEqualTo(userC.getFullName());
        assertThat(result.getId()).isEqualTo(userC.getId());
    }

    @Test
    public void shouldGetTheUserByFullName() {
        Point point = new Point(0, 0);
        List<Profession> professionList = new LinkedList<>();
        List<UserRole> roleList = new LinkedList<>();
        List<User> userList = new LinkedList<>();
        User userA = new User("1", "user A", "email1@gmail.com", "pass1", true, point, professionList, roleList);
        User userB = new User("2", "user B", "email2@gmail.com", "pass2", true, point, professionList, roleList);
        User userC = new User("3", "user C", "email3@gmail.com", "pass3", true, point, professionList, roleList);
        User userD = new User("4", "user D", "email4@gmail.com", "pass4", true, point, professionList, roleList);

        userList.add(userA);
        userList.add(userB);
        userList.add(userC);
        userList.add(userD);

        userRepository.saveAll(userList);

        List<User> result = userRepository.findByFullName(userC.getFullName(), Mockito.any(Pageable.class));

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getPassword()).isEqualTo(null);
    }

    @Test
    void shouldTestTheUserListPageableSizeAndSortKey() {
        Point point = new Point(0, 0);
        List<Profession> professionList = new LinkedList<>();
        List<UserRole> roleList = new LinkedList<>();
        List<User> userList = new LinkedList<>();
        String nameToFind = "user";
        int pageablePageSize = 10;

        Pageable pageable = PageRequest.of(1, pageablePageSize, Sort.by(Sort.Direction.ASC, "id"));

        for (int i = 0; i < 100; i++) {
            String fullName = String.format("%s", nameToFind);
            String email = String.format("user%s@gmail.com", i);
            String password = String.format("pass%s", i);

            userList.add(new User(null, fullName, email, password, true, point, professionList, roleList));
        }

        userRepository.saveAll(userList);

        List<User> result = userRepository.findByFullName(nameToFind, pageable);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(pageablePageSize);
        assertThat(result.get(0).getId()).isEqualTo(userList.get(pageablePageSize).getId());
    }

    @Test
    void shouldTestTheUserListPageablePageAndSortKey() {
        Point point = new Point(0, 0);
        List<Profession> professionList = new LinkedList<>();
        List<UserRole> roleList = new LinkedList<>();
        List<User> userList = new LinkedList<>();
        String nameToFind = "user";
        int pageablePageSize = 10;
        int pageablePage = 2;

        Pageable pageable = PageRequest.of(pageablePage, pageablePageSize, Sort.by(Sort.Direction.ASC, "id"));

        for (int i = 0; i < 100; i++) {
            String fullName = String.format("%s", nameToFind);
            String email = String.format("user%s@gmail.com", i);
            String password = String.format("pass%s", i);

            userList.add(new User(null, fullName, email, password, true, point, professionList, roleList));
        }

        userRepository.saveAll(userList);

        List<User> result = userRepository.findByFullName(nameToFind, pageable);

        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(pageablePageSize);
        assertThat(result.get(0).getId()).isEqualTo(userList.get(pageablePageSize * pageablePage).getId());
    }
}