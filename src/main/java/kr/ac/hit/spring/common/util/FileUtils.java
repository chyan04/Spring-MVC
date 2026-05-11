package kr.ac.hit.spring.common.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.ac.hit.spring.board.model.Board;
import kr.ac.hit.spring.file.model.FileItem;

@Component("fileUtils")
public class FileUtils {
	
	public static final String filePath = "D:\\SpringUpload\\upload";
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public List<FileItem> uploadFiles(Board board, MultipartHttpServletRequest mRequest)
		throws Exception{
		
		List<FileItem> fileList = new ArrayList<FileItem>();
		
		List<MultipartFile> mFList = mRequest.getFiles("uploadFiles");
		
		File file = null;
		
		for(MultipartFile parts : mFList) {
			if(parts.isEmpty() == false ) {		// 저장할 파일이 있는지 체크
				FileItem fileItem = new FileItem();
				
				fileItem.setRefSeqNo(board.getBoSeqNo());	// 게시판의 글 번호
				fileItem.setRegUser(board.getBoWriter());		// 작성자 id
				fileItem.setBizType(board.getBoType());			// 게시판 타입
				fileItem.setFileSize(parts.getSize());				// 파일의 사이즈(byte단위)
				fileItem.setFileFancySize(getFancySize(parts.getSize()));			// 사용자에게 보여줄 파일 사이즈
				fileItem.setFileName(parts.getOriginalFilename());				// 파일의 실제 이름 (업로드한 파일명)
				fileItem.setFileSaveName(getSaveName() + "_" + parts.getOriginalFilename());	// 서버에 저장할 파일명
				
				// 파일 저장 경로
				fileItem.setFilePath(board.getBoType() + "/" + dateFormat.format(new Date()));	
				
				try {
					
					file = new File(filePath + "/" + fileItem.getFilePath() + "/" + fileItem.getFileSaveName());
					if(file.exists() == false) {
						file.mkdirs();
					}
					parts.transferTo(file); // 위에서 지정한 위치에 파일 저장
					
					// 확장자에 따라 이미지 썸네일 생성
					String ext = parts.getOriginalFilename().substring(parts.getOriginalFilename().lastIndexOf(".") + 1);
					
					if(MediaUtils.getMediaType(ext) != null && ("GALLERY").equals(board.getBoType())) {
						String thumSaveName = createThumbnail(fileItem.getFilePath(), fileItem.getFileSaveName(), ext);
						fileItem.setThumSaveName(thumSaveName);
					}
					
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				fileList.add(fileItem);
			}
		}
		
		
		return fileList;
	}

	private String createThumbnail(String path, String fileSaveName, String ext) {
		BufferedImage sourceImg;
		String thumbnailName;
		
		try {
			
			// 업로드 된 원본 이미지 읽기
			// "D:\\SpringUpload\\upload\GALLERY/20251017/96f453ed39eb4279a99377b16c59c513_1.jpg
			sourceImg = ImageIO.read(new File(filePath + File.separator + path + File.separator + fileSaveName ));
			
			BufferedImage destImg = 
					Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 100);
			
			thumbnailName = filePath + File.separator + path + File.separator + "thum_" + fileSaveName;
			
			// 썸네일 이미지 파일 객체 생성
			File newFile = new File(thumbnailName);
			
			// 썸네일 이미지 파일 저장
			ImageIO.write(destImg, ext, newFile);
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "thum_" + fileSaveName;
	}

	private String getSaveName() {			// 파일이 중복되지 않게
		return UUID.randomUUID().toString().replace("-", "");
	}

	private String getFancySize(long size) {
		String fancy = "";
		
		DecimalFormat deciamlFormat = new DecimalFormat();
		if(size < 1024) {
			fancy = deciamlFormat.format(size) + "bytes";
		}else if(size < (1024 * 1024)) {
			fancy = deciamlFormat.format(size / 1024.0) + "KB";
		}else {
			fancy = deciamlFormat.format(size / (1024.0 * 1024.0 )) + "MB";
		}
		
		
		return fancy;
	}

}