package com.baseball.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baseball.board.model.domain.Comment;
import com.baseball.board.model.domain.CommentDetail;
import com.baseball.board.model.repository.CommentDAO;

import common.Pager;

@Service
public class CommentServiceImpl implements CommentService{
	
	private int pageSize = 10;
	private int blockSize = 10;
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public Map selectAll(int board_id, int commentPage) {
		
		int totalContents = commentDAO.countAll(board_id);
		
		
		Pager commentPager = new Pager(commentPage, pageSize, totalContents, blockSize);
		
		Map<String, Integer> paging = new HashMap<>();
		paging.put("board_id", board_id);
		paging.put("startContent", commentPager.getStartContent()-1);
		paging.put("pageSize", commentPager.getPageSize());
						
		List commentList = commentDAO.commentAll(paging);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("commentList", commentList);
		map.put("commentPager", commentPager);
		
		return map;
	}

	@Override
	public int update(Comment comment) {

		int result = commentDAO.updateComment(comment);
		
		return result;
	}

	@Override
	public int delete(int comment_id) {

		int result = commentDAO.deleteComment(comment_id);
		
		return result;
	}

	@Override
	public int insert(Comment comment) {

		int result = commentDAO.insertComment(comment);	// ´ñ±Û»ðÀÔ
		if(result == 0){
			throw new RuntimeException("µî·Ï ½ÇÆÐ!!");
		}
		
		int totalContents = commentDAO.countAll(comment.getBoard_id());	// »ðÀÔ ÈÄ ÃÑ °¹¼ö ¹ÝÈ¯.
		int totalPage = (int)Math.ceil(totalContents/(double)pageSize);	// ¸¶Áö¸· ÆäÀÌÁö ¹ÝÈ¯.  
		
		return totalPage;
	}

}
