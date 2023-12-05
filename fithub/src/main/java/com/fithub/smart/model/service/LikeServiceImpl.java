package com.fithub.smart.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fithub.smart.model.dao.LikeDao;
import com.fithub.smart.model.dto.Like;

@Service
public class LikeServiceImpl implements LikeService {
	@Autowired
	private LikeDao likeDao;

	@Override
	public boolean registLike(Like like) {
		int result = likeDao.registLike(like);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteLike(Like like) {
		int result = likeDao.deleteLike(like);
		if (result > 0)
			return true;
		return false;
	}

}
