package com.baseball.photoboard.model.service;

import java.io.File;
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
		
		int thumb1Size = 800;	// ù��° ����� �ִ� ũ��.
		int thumb2Size = 150;
		String path = "/images/photo";	// ���� ��������.
		String realPath = request.getSession().getServletContext().getRealPath(path);
		String originalName = uploadFile.getOriginalFilename();
		String savePath = PhotoUploader.makeDir(realPath);	// �������� ����
		String saveName = PhotoUploader.savePhoto(savePath, originalName, uploadFile);	// �̹��� ���� �� ���� �̸� ��ȯ.
		String ext = saveName.substring(saveName.lastIndexOf("."));
		String imgName = saveName.substring(0, saveName.lastIndexOf("."));
		
		// ����� ����
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
		int photoBoard_id = photoBoard.getPhotoBoard_id();	// ��� ���Ե� ������ �� ��ȯ. 
		
		return photoBoard_id;
	}

	@Override
	public PhotoDetail photoLoad(int photoBoard_id) {
		
		photoBoardDAO.photoBoardHitUp(photoBoard_id); // ��ȸ�� ����.
		
		return photoBoardDAO.photoLoad(photoBoard_id);
	}

	@Override
	public void photoEdit(HttpServletRequest request, MultipartFile uploadFile, PhotoBoard photoBoard) {
		
		if(!uploadFile.getOriginalFilename().equals("")){	// ���� �ٲ�����,
			
			/* �������� ���� */
			SimpleDateFormat format = new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd");
			String realPath = request.getSession().getServletContext().getRealPath("/images/photo");
			String datePath = format.format(photoBoard.getRegdate());
			String savePath = realPath+File.separator+datePath;
			String saveName = photoBoard.getSaveName();
			String filename = saveName.substring(0,saveName.lastIndexOf("."));
			String ext = saveName.substring(saveName.lastIndexOf("."));
			
			boolean del = PhotoUploader.deletePhoto(savePath, filename, ext);	// ���� ���� ����.
			if(photoBoard.getThumb2().equals("Y")){	// ���� ���� ����� ����������,
				PhotoUploader.deletePhoto(savePath, filename+"+thumb2", ext);	// ���� ���� ����� ���� ����.
				if(photoBoard.getThumb1().equals("Y")){	// �߰� ����� ���� ������,
					PhotoUploader.deletePhoto(savePath, filename+"+thumb1", ext);	// �߰� ����� ���� ����.
				}				
			}
			
			/* �ٲ� ���� ���ε�!! */
			int thumb1Size = 800;	// ù��° ����� �ִ� ũ��.
			int thumb2Size = 150;
			String originalName = uploadFile.getOriginalFilename();
			saveName = PhotoUploader.savePhoto(savePath, originalName, uploadFile);	// ���ο� �������� ����.
			filename = saveName.substring(0,saveName.lastIndexOf("."));
			ext = saveName.substring(saveName.lastIndexOf("."));
			
			// ����� ����
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
			
		}else{
			
			photoBoard.setSaveName(null);
			
		}// if(saveName)
		
		photoBoardDAO.photoEdit(photoBoard);
		
	}

	@Override
	public void photoDelete(HttpServletRequest request, PhotoBoard photoBoard) {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy"+File.separator+"MM"+File.separator+"dd");
		String realPath = request.getSession().getServletContext().getRealPath("/images/photo");	// ������ �ֻ��� ����.
		String datePath = format.format(photoBoard.getRegdate());	// ������ �� ��¥ ���.
		String savePath = realPath+File.separator+datePath;	// Ǯ ���� ���.
		String saveName = photoBoard.getSaveName();	// DB�� ����� �̸�.
		String filename = saveName.substring(0,saveName.lastIndexOf("."));
		String ext = saveName.substring(saveName.lastIndexOf("."));
		
		PhotoUploader.deletePhoto(savePath, filename, ext);	// ���� ���� ����.
		if(photoBoard.getThumb2().equals("Y")){	// ���� ���� ����� ����������,
			PhotoUploader.deletePhoto(savePath, filename+"_thumb2", ext);	// ���� ���� ����� ���� ����.
			if(photoBoard.getThumb1().equals("Y")){	// �߰� ����� ���� ������,
				PhotoUploader.deletePhoto(savePath, filename+"_thumb1", ext);	// �߰� ����� ���� ����.
			}				
		}
		
		PhotoUploader.deleteDir(savePath);	// ���� ���� �� ���� ��������. �����ȿ� ���� �������� ���� ���� ��.
		
		photoBoardDAO.photoDelete(photoBoard.getPhotoBoard_id());
		
	}

}
