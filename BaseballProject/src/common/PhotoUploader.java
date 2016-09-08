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

/* ���� ���ε� �� */
public class PhotoUploader {
	
	public static String savePhoto(String savePath, String originalName, MultipartFile uploadFile){	// ���� ����
		
		UUID uuid = UUID.randomUUID();
		String saveName = uuid+"_"+originalName;
		File originalFile = new File(savePath+"/"+saveName);
		try {
			uploadFile.transferTo(originalFile);	// �������� ����.
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return saveName;
	}
	
	public static String makeDir(String realPath){	// �������� ����
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String savePath = realPath+"/"+format.format(cal.getTime());
		
		File dateFile = new File(savePath);
		if(!dateFile.exists()){
			dateFile.mkdirs();	// �������� ������ ������������ ����.
		}
		
		return savePath;
	}
	
	public static int imgResize (String savePath, String imgName, String thumbName, int maxResizingSize) {	// �̹��� ���������ؼ� ����.
				
		File image = new File(savePath+"/"+imgName);
		File thumb = new File(savePath+"/"+thumbName);
		
		int[] size = getResizingSize(image, maxResizingSize);
		
		if(size == null){	// �̹��� ����� �������� �� ������� �۰ų� �������.
			return 0;
		}
		
		try {
			Thumbnails.of(image).size(size[0], size[1]).toFile(thumb);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return 1;
	}
	
	public static int[] getResizingSize(File image, int maxResizingSize){	// width, height ������ ���������� ����� �迭�� ��ȯ. 
		int[] size = new int[2];
		try {
			
			BufferedImage img = ImageIO.read(image);
			int width = img.getWidth();
			int height = img.getHeight();
			int gcd = getGCD(width, height);
			int widthScale = width/gcd;	// �̹��� ���κ���
			int heightScale = height/gcd;	// �̹��� ���κ���
			
			if(width == height){
				if(width <= maxResizingSize){
					return null;
				}else{
					size[0] = maxResizingSize;
					size[1] = maxResizingSize;
				}
				
			}else if(width > height) {	// ���ΰ� �� Ŭ��
				if(width <= maxResizingSize){
					return null;
				}else{
					size[0] = maxResizingSize;
					size[1] = (width*heightScale)/widthScale;
				}
				
			}else if(width < height){	// ���ΰ� �� Ŭ��
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
	
	public static void deletePhoto(String savePath, String filename, String ext){	// ���� ����.
		
		File deleteFile = new File(savePath+"/"+filename+ext);
		deleteFile.delete();
		
	}
	
	public static boolean deleteDir(String deletePath){	// ���丮 ����
		
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
