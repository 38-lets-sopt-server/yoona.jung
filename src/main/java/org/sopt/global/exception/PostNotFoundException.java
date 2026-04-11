package org.sopt.global.exception;

public class PostNotFoundException extends RuntimeException{
    public PostNotFoundException(Long id) {
        super(id + "번에 해당하는 게시글이 존재하지 않습니다.");
    }
}
