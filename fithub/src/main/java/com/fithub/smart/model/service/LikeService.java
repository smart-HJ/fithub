package com.fithub.smart.model.service;

import com.fithub.smart.model.dto.Like;

public interface LikeService {

	boolean registLike(Like like);

	boolean deleteLike(Like like);

}
