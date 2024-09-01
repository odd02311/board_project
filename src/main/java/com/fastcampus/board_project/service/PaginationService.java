package com.fastcampus.board_project.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {

    private static final int BAR_LENGTH = 5;    // 고정 상태값

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages){
        // 시작, 끝 번호 계산
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0); // 0 이랑 비교 큰수를 넣음.
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);
        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength() {
        return BAR_LENGTH;
    }
}

/**
 * 페이징 로직
 * 리스트 형태 숫자로 구현
 * 숫자 리스트를 받아서 view에서 그려준다
 */