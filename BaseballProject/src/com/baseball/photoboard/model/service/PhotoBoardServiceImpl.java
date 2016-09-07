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
		
		int thumb1Size = 800;	// ù��° ����� �ִ� ũ��.
		int thumb2Size = 150;
		String path = "/images/photo";	// ���� ��������.
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String originalName = uploadFile.getOriginalFilename();
		String savePath = PhotoUploader.makeDir(realPath);	// �������� ����
		String saveName = PhotoUploader.savePhoto(savePath, originalName, uploadFile);	// �̹��� ���� �� ���� �̸� ��ȯ.
		String format = saveName.substring(saveName.lastIndexOf("."));
		String imgName = saveName.substring(0, saveName.lastIndexOf("."));
		
		// ����� ����
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
		int photoBoard_id = photoBoard.getPhotoBoard_id();	// ��� ���Ե� ������ �� ��ȯ. 
		
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
