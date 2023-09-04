package com.staekframework.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 기존의 템플릿 메서드 패턴은 UserDao dml개수만큼 UserDao 구현클래스, DaoFactory 함수 가 늘어나는 문제점이 있었다.
 * UserDao 의 여러 dml 함수의 컨텍스트 중 변경이되는 부분을 전략패턴으로 분리하였다.
 * add, get, delete 등은 userdao 라는 비지니스 로직에 묶이지 않고,
 * PreparedStatementStrategy 인터페이스를 구현한 독립적인 class로서 동작하도록 만들었다. (DeleteAllStrategy ...)
 * 이 클래스는 오로지 Connection 객체를 받아 PreparedStatement 를 생성하는 용도로만 사용된다.
 *
 */
public interface PreparedStatementStrategy {
    PreparedStatement newStatement(Connection conn) throws SQLException;

}
