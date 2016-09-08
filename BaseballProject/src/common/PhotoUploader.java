package common;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

/* 사진 업로드 용 */
public class PhotoUploader {
	
	public static String savePhoto(String savePath, String originalName, MultipartFile uploadFile){	// 사진 저장
		
		UUID uuid = UUID.randomUUID();
		String saveName = uuid+"_"+originalName;
		File originalFile = new File(savePath+"/"+saveName);
		try {
			uploadFile.transferTo(originalFile);	// 원본파일 저장.
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return saveName;
	}
	
	public static String makeDir(String realPath){	// 저장폴더 생성
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String savePath = realPath+"/"+format.format(cal.getTime());
		
		File dateFile = new File(savePath);
		if(!dateFile.exists()){
			dateFile.mkdirs();	// 상위폴더 없으면 상위폴더까지 생성.
		}
		
		return savePath;
	}
	
	public static int imgResize (String savePath, String imgName, String thumbName, int maxResizingSize) {	// 이미지 리사이즈해서 저장.
				
		File image = new File(savePath+"/"+imgName);
		File thumb = new File(savePath+"/"+thumbName);
		
		int[] size = getResizingSize(image, maxResizingSize);
		
		if(size == null){	// 이미지 사이즈가 리사이즈 할 사이즈보다 작거나 같은경우.
			return 0;
		}
		
		try {
			Thumbnails.of(image).size(size[0], size[1]).toFile(thumb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public static int[] getResizingSize(File image, int maxResizingSize){	// width, height 순으로 리사이즈할 사이즈를 배열로 반환. 
		int[] size = new int[2];
		try {
			
			BufferedImage img = ImageIO.read(image);
			int width = img.getWidth();
			int height = img.getHeight();
			int gcd = getGCD(width, height);
			int widthScale = width/gcd;	// 이미지 가로비율
			int heightScale = height/gcd;	// 이미지 세로비율
			
			if(width == height){
				if(width <= maxResizingSize){
					return null;
				}else{
					size[0] = maxResizingSize;
					size[1] = maxResizingSize;
				}
				
			}else if(width > height) {	// 가로가 더 클때
				if(width <= maxResizingSize){
					return null;
				}else{
					size[0] = maxResizingSize;
					size[1] = (width*heightScale)/widthScale;
				}
				
			}else if(width < height){	// 세로가 더 클때
				if(height <= maxResizingSize){
					return null;
				}else{
					size[1] = maxResizingSize;
					size[0] = (height*widthScale)/heightScale;
				}
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return size;
	}	
	
	public static void deletePhoto(String savePath, String filename, String ext){	// 사진 삭제.
		
		File deleteFile = new File(savePath+"/"+filename+ext);
		deleteFile.delete();
		
	}
	
	public static boolean deleteDir(String deletePath){	// 디렉토리 삭제
		
		File deleteDir = new File(deletePath);
		
		return deleteDir.delete();
	}
	
	public static int getGCD(int first, int second){
		int GCD = 0;
		int big = 0;
		int small = 0;
		
		if(first > second){
			big = first;
			small = second;
		}else{
			big = second;
			small = first;
		}
		
		while(true){
			if(big % small == 0){
				GCD = small;
				break;
			}else{
				int tmp = big % small;
				big = small;
				small = tmp;
			}
		}
		
		return GCD;
	}
}
