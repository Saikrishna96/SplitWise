package com.krish.SplitWise.repositories;

import com.krish.SplitWise.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {

    private final Map<String, User> userMap;

    public UserRepository() {
        this.userMap = new HashMap<>();
    }

    public void save(User user) {
        if (user != null) {
            userMap.put(user.getId(), user);
        }
        log.info("User added : {}", user.getId());
    }

    public User getUser(String id) {
        return userMap.get(id);
    }
}
