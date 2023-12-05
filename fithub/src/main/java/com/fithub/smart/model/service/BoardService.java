package com.fithub.smart.model.service;

import java.util.List;

import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.Like;

public interface BoardService {

	List<Board> boardList();

	Board boardDetail(Like like);

	boolean boardRegist(Board board, List<String> hashTagList);

	boolean boardDelete(int id);

	boolean boardUpdate(Board board);

	List<Board> boardSearch(String tag);

	List<String> boardHashTags(int boardId);

}
