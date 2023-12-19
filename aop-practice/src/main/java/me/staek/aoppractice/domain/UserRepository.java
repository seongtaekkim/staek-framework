package me.staek.aoppractice.domain;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {

    public List<User> findAll() {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "kim", "1234", "kim@naver"));
        list.add(new User(2, "seongtki", "1234", "kim@naver"));
        return list;
    }

    public User findById(int id) {
        return new User(1, "kim", "1234", "kim@naver");
    }

    public void save(JoinReqDto user) {

    }

    public void delete(int id) {

    }

    public void update(UptateReqDto dto) {

    }
}
