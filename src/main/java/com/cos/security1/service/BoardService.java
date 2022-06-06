package com.cos.security1.service;

import com.cos.security1.domain.Board;
import com.cos.security1.domain.User;
import com.cos.security1.repository.BoardRepository;
import com.cos.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public int 글쓰기(Board board, User user) {
        try {
            board.setCount(0);
            board.setUser(user);
            boardRepository.save(board);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("BoardService:글쓰기() = " + e.getMessage());
        }
        return -1;
    }
}
