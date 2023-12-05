package com.fithub.smart.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.HashTag;
import com.fithub.smart.model.dto.Like;

public interface BoardDao {

	List<Board> boardList();

	Board boardDetail(Like like);

	int boardRegist(Board board);
	
	int getUserId(String id);

	int boardDelete(int id);

	int boardUpdate(Board board);

	int hashTagRegist(HashTag hashtag);

	List<Board> boardSearch(String tag);

	List<String> boardHashTags(int boardId);

	void hashTagClear(int boardId);

}
