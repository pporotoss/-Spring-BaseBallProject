package com.baseball.photoboard.model.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baseball.photoboard.model.domain.PhotoBoard;
import com.baseball.photoboard.model.domain.PhotoDetail;
import com.baseball.photoboard.model.repository.PhotoBoardDAO;

import common.PhotoUploader;
import common.Searching;

@Service
public class PhotoBoardServiceImpl implements PhotoBoardService{

	@Autowired
	PhotoBoardDAO photoBoardDAO;
	
	@Override
	public Map photoBoardList(String page, Searching searching) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int photoUpload(HttpServletRequest request, MultipartFile uploadFile, PhotoBoard photoBoard) {
		
		int thumb1Size = 800;	// 첫번째 썸네일 최대 크기.
		int thumb2Size = 150;
		String path = "/images/photo";	// 저장 폴더지정.
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String originalName = uploadFile.getOriginalFilename();
		String savePath = PhotoUploader.makeDir(realPath);	// 저장폴더 생성
		String saveName = PhotoUploader.savePhoto(savePath, originalName, uploadFile);	// 이미지 저장 후 저장 이름 반환.
		String format = saveName.substring(saveName.lastIndexOf("."));
		String imgName = saveName.substring(0, saveName.lastIndexOf("."));
		
		// 썸네일 저장
		int thumb2 = PhotoUploader.imgResize(savePath, saveName, imgName+"_thumb2"+format, thumb2Size);
		if(thumb2 == 0){
			photoBoard.setThumb2("N");
			photoBoard.setThumb1("N");
		}else{
			int thumb1 = PhotoUploader.imgResize(savePath, saveName, imgName+"_thumb1"+format, thumb1Size);
			if(thumb1 == 0){
				photoBoard.setThumb1("N");
			}
		}
		
		photoBoard.setSaveName(saveName);
		
		photoBoardDAO.photoUpload(photoBoard);
		int photoBoard_id = photoBoard.getPhotoBoard_id();	// 방금 삽입된 시퀀스 값 반환. 
		
		return photoBoard_id;
	}

	@Override
	public PhotoDetail photoLoad(int photoBoard_id) {
		
		return photoBoardDAO.photoLoad(photoBoard_id);
	}

	@Override
	public void photoEdit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void photoDelete(int photoBoard_id) {
		// TODO Auto-generated method stub
		
	}

}
