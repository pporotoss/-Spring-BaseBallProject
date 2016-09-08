package com.baseball.photoboard.model.service;

import java.text.SimpleDateFormat;
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
		String ext = saveName.substring(saveName.lastIndexOf("."));
		String imgName = saveName.substring(0, saveName.lastIndexOf("."));
		
		// 썸네일 저장
		int thumb2 = PhotoUploader.imgResize(savePath, saveName, imgName+"_thumb2"+ext, thumb2Size);
		if(thumb2 == 0){
			photoBoard.setThumb2("N");
			photoBoard.setThumb1("N");
		}else{
			int thumb1 = PhotoUploader.imgResize(savePath, saveName, imgName+"_thumb1"+ext, thumb1Size);
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
		
		photoBoardDAO.photoBoardHitUp(photoBoard_id); // 조회수 증가.
		
		return photoBoardDAO.photoLoad(photoBoard_id);
	}

	@Override
	public void photoEdit(HttpServletRequest request, MultipartFile uploadFile, PhotoBoard photoBoard) {
		
		if(photoBoard.getSaveName() != null){	// 사진 바꿨으면,
			
			/* 기존파일 삭제 */
			String realPath = request.getSession().getServletContext().getRealPath("/images/photo");
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String datePath = format.format(photoBoard.getRegdate());
			String savePath = realPath+"/"+datePath;
			
			String saveName = photoBoard.getSaveName();
			String filename = saveName.substring(0,saveName.lastIndexOf("."));
			String ext = saveName.substring(saveName.lastIndexOf("."));
			
			PhotoUploader.deletePhoto(savePath, filename, ext);	// 원래 파일 삭제.
			if(photoBoard.getThumb2().equals("Y")){	// 제일 작은 썸네일 생성했으면,
				PhotoUploader.deletePhoto(savePath, filename+"+thumb2", ext);	// 제일 작은 썸네일 파일 삭제.
				if(photoBoard.getThumb1().equals("Y")){	// 중간 썸네일 생성 했으면,
					PhotoUploader.deletePhoto(savePath, filename+"+thumb1", ext);	// 중간 썸네일 파일 삭제.
				}				
			}
			
			/* 바꾼 사진 업로드!! */
			int thumb1Size = 800;	// 첫번째 썸네일 최대 크기.
			int thumb2Size = 150;
			String originalName = uploadFile.getOriginalFilename();
			saveName = PhotoUploader.savePhoto(savePath, originalName, uploadFile);	// 새로운 원본사진 저장.
			filename = saveName.substring(0,saveName.lastIndexOf("."));
			ext = saveName.substring(saveName.lastIndexOf("."));
			
			// 썸네일 저장
			int thumb2 = PhotoUploader.imgResize(savePath, saveName, filename+"_thumb2"+ext, thumb2Size);
			if(thumb2 == 0){
				photoBoard.setThumb2("N");
				photoBoard.setThumb1("N");
			}else{
				int thumb1 = PhotoUploader.imgResize(savePath, saveName, filename+"_thumb1"+ext, thumb1Size);
				if(thumb1 == 0){
					photoBoard.setThumb1("N");
				}
			}
			
			photoBoard.setSaveName(saveName);
			
		}// if(saveName)
		
		photoBoardDAO.photoEdit(photoBoard);
		
	}

	@Override
	public void photoDelete(int photoBoard_id) {
		// TODO Auto-generated method stub
		
	}

}
