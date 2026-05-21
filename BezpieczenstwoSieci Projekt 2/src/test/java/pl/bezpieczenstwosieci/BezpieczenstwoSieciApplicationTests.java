package pl.bezpieczenstwosieci;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bezpieczenstwosieci.model.User;
import pl.bezpieczenstwosieci.repository.UserRepository;
import pl.bezpieczenstwosieci.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = "jasypt.encryptor.password=MojeMasterHaslo2026")
class BezpieczenstwoSieciApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment environment;

    @Test
    void test1_shouldSaveUserToDatabaseAndHashPassword() {
        String username = "test_user_1";
        String rawPassword = "SecurePassword123";

        User savedUser = userService.registerUser(username, rawPassword);

        assertNotNull(savedUser.getId());
        assertEquals(username, savedUser.getUsername());
        assertNotEquals(rawPassword, savedUser.getPassword());
        assertTrue(passwordEncoder.matches(rawPassword, savedUser.getPassword()));
    }

    @Test
    void test2_shouldVerifyThatHashedPasswordsAreDifferentForSameInput() {
        String password = "SamePassword123";

        User user1 = userService.registerUser("user_a", password);
        User user2 = userService.registerUser("user_b", password);

        assertNotEquals(user1.getPassword(), user2.getPassword());
    }

    @Test
    void test3_shouldVerifyPropertiesAreLoadedSuccessfully() {
        String dbUser = environment.getProperty("spring.datasource.username");
        assertNotNull(dbUser);
    }

    @Test
    void test4_shouldConfirmPlaintextDatabasePasswordIsNotExposed() {
        String rawProperty = environment.getProperty("spring.datasource.password");
        assertNotEquals("baza123", rawProperty);
    }

    @Test
    void test5_shouldSuccessfullyConnectToDatabaseAndCount() {
        long countBefore = userRepository.count();
        userRepository.save(new User("connection_test", "hashed_value"));
        assertEquals(countBefore + 1, userRepository.count());
    }
}
