package com.staekframework.test.User;

import com.staekframework.tx.DefaultTxManager;
import com.staekframework.tx.TxManager;

import java.util.List;

public interface UserService {


    void createUser(User user);

    void callwithdrawal_program() throws Exception;

}
