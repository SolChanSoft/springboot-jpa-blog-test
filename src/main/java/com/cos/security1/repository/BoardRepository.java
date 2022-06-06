package com.cos.security1.repository;

import com.cos.security1.domain.Board;
import com.cos.security1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Integer> {

}
