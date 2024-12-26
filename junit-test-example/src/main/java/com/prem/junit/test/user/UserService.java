package com.prem.junit.test.user;

public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public String getUsername(int id) {
    User user = userRepository.findUserById(id);
    return user != null ? user.getName() : "Unknown";
  }
}
