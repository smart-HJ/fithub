package com.fithub.smart.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fithub.smart.model.dao.BoardDao;
import com.fithub.smart.model.dto.Board;
import com.fithub.smart.model.dto.HashTag;
import com.fithub.smart.model.dto.Like;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;

	@Override
	public List<Board> boardList() {
		return boardDao.boardList();
	}

	@Override
	public Board boardDetail(Like like) {
		return boardDao.boardDetail(like);
	}

	@Override
    public boolean boardRegist(Board board, List<String> hashTagList) {
        int result = boardDao.boardRegist(board);
        if (result > 0) {
            for (String tag : hashTagList) {
                HashTag hashtag = new HashTag(tag, board.getBoardId());
                int result2=boardDao.hashTagRegist(hashtag);
                if(result2<=0)return false;
            }
            return true;
        }
        return false;
    }

	@Override
	public boolean boardDelete(int id) {
		int result =boardDao.boardDelete(id);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean boardUpdate(@RequestBody Board board) {
		int result =boardDao.boardUpdate(board);
		if (result > 0) {
			boardDao.hashTagClear(board.getBoardId());
			for (String tag : board.getHashTags()) {
                HashTag hashtag = new HashTag(tag, board.getBoardId());
                int result2=boardDao.hashTagRegist(hashtag);
                if(result2<=0)return false;
            }
			return true;
		}
		return false;
	}
	
	@Override
    public List<Board> boardSearch(String tag) {
        List<Board> list=boardDao.boardSearch(tag);
        return list;
    }

    @Override
    public List<String> boardHashTags(int boardId) {
        return boardDao.boardHashTags(boardId);
    }

}
