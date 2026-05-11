package kr.ac.hit.spring.common.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.ac.hit.spring.common.service.impl.CommonServiceImpl;
import kr.ac.hit.spring.common.util.FileUtils;
import kr.ac.hit.spring.file.model.FileItem;

@Controller
public class CommonController {

	@Autowired
	CommonServiceImpl commonService;


	
	@RequestMapping(value="/common/download")
	public void fileDownload(@RequestParam(value="fileSeqNo", required = true) String fileSeqNo,
		HttpServletResponse response) throws Exception {
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileSeqNo", fileSeqNo);
		
		FileItem fileItem = commonService.getFileItem(paramMap);
		
		if(fileItem == null) {
			throw new RuntimeException("해당 첨푸파일이 존재하지 않습니다.");
		}
		// 파일을 저장했던 위치에서 파일을 읽어 바이트 byte 형식으로 변환
		byte fileByte[] = org.apache.commons.io.FileUtils.readFileToByteArray(
			new File(FileUtils.filePath + "/" + 
						fileItem.getFilePath() + "/" +
						fileItem.getFileSaveName()));
		
		response.setContentType("application/octet-stream");	// mime-type 지정
		response.setContentLength(fileByte.length);		// 파일 길이
		// 헤더 지정
		response.setHeader("Content-Disposition", "attachment; fileName=\"" 
				+ URLEncoder.encode(fileItem.getFileName(), "utf-8"));
		response.getOutputStream().write(fileByte);
		response.getOutputStream().flush();
		response.getOutputStream().close();
	}
	
	@RequestMapping(value="/common/display")
	@ResponseBody
	public ResponseEntity<byte[]> display(
			@RequestParam(value="imgType", required = false) String imgType,
			@RequestParam(value="fileSeqNo", required = false) int fileSeqNo
			) throws Exception {
		
		InputStream input = null;
		ResponseEntity<byte[]> entity = null;
		
		try {
			HashMap<String, Object>paramMap = new HashMap<String, Object>();
			paramMap.put("fileSeqNo", fileSeqNo);
			FileItem fileItem = commonService.getFileItem(paramMap);
			
			// 확장자 추출
			String formatName = fileItem.getFileName().substring(
					fileItem.getFileName().lastIndexOf(".")+1);
			
			// 확장저에 따른 미디어 타입 설정
			MediaType mType = null;
			if(formatName.equalsIgnoreCase("jpeg") || formatName.equalsIgnoreCase("jpg")) {
				mType = MediaType.IMAGE_JPEG;
			}else if(formatName.equalsIgnoreCase("png")) {
				mType = MediaType.IMAGE_PNG;
			}else if(formatName.equalsIgnoreCase("gif")) {
				mType = MediaType.IMAGE_GIF;
			}
			
			HttpHeaders header = new HttpHeaders();
			if(mType != null) {
				if(!StringUtils.isEmpty(imgType) && imgType.equals("img"))  {
					
					// 원본 이미지인 경우
					input = new FileInputStream(FileUtils.filePath + "/" + 
								fileItem.getFilePath() + "/" +
								fileItem.getFileSaveName());	
				}else {
					// 썸네이룡 이미지
					input = new FileInputStream(FileUtils.filePath + "/" + 
							fileItem.getFilePath() + "/" +
							fileItem.getThumSaveName());	
				}
				header.setContentType(mType);
			}
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(input),header, HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(input != null) input.close();
		}
		return entity;
	}
}