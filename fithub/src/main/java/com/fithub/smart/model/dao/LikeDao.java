package com.fithub.smart.model.dao;

import com.fithub.smart.model.dto.Like;

public interface LikeDao {

	int registLike(Like like);
	
	int deleteLike(Like like);
}
