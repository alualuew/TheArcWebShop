package service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.model.User;
import repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;}

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

            public Optional<User> getUsersById(Long id) {
                return userRepository.findById(id);
            }
public User save(User user) {
    return userRepository.save(user);
}

@PostMapping
public User createUser (@RequestBody User user) {
    return userRepository.save(user);
}
@PostMapping("/update")
    public User updateUser (@RequestBody User user) {
        return userRepository.save(user);
    }

    @PostMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userRepository.deleteById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
