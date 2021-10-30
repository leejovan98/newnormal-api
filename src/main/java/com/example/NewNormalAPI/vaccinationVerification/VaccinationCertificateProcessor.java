package com.example.NewNormalAPI.vaccinationVerification;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.LoadLibs;

@Component
@ConfigurationProperties(prefix="img.proc")
@Data
public class VaccinationCertificateProcessor {
	
	private static String root;
	
	private static final String TMP_SUBFOLDER = "tmp";
	private static final String ERR_SUBFOLDER = "err";
	
	public Date process(MultipartFile img) {
		
		File tmpDir = new File(root + "/" + TMP_SUBFOLDER);
		if(!tmpDir.exists()) tmpDir.mkdir();
		
		String imageFileName = img.getOriginalFilename();
		File file = new File(tmpDir, imageFileName);
		
		String data;
		
		proc:{
			
			try (OutputStream os = new FileOutputStream(file)) {
			    os.write(img.getBytes());  
			    
			    Tesseract t = new Tesseract();
				File tessDataFolder = LoadLibs.extractTessResources("tessdata");
				t.setDatapath(tessDataFolder.getAbsolutePath());

			    t.setLanguage("eng");
			    data = t.doOCR(file);  
			} catch(Exception e) {
				break proc;
			}
			
			List<String> tokens = Arrays.asList(data.split("\n"));
			Pattern linePattern = Pattern.compile("EFFECTIVE FROM",Pattern.CASE_INSENSITIVE);
			Pattern datePattern = Pattern.compile("\\d\\d\\s\\S\\S\\S\\s\\d\\d\\d\\d");
			
			String strDate = null;
			for(String token: tokens) {
				Matcher matcher = linePattern.matcher(token);
				if(matcher.find()) {
					Matcher dateMatcher = datePattern.matcher(token);
					if(dateMatcher.find()) {
						strDate = dateMatcher.group();
					}
				}
			}
			
			DateFormat format = new SimpleDateFormat("dd MMM yy");
			if(strDate != null) {
				try {
					Date date = format.parse(strDate);
					file.delete();
					return date;
				} catch (ParseException e) {
					break proc;
				}
			}			
		}
		
		File errDir = new File(root + "/" + ERR_SUBFOLDER);
		if(!errDir.exists()) errDir.mkdir();
		
		File newFileName = new File(errDir, imageFileName);
		file.renameTo(newFileName);
		return null;
		
	}
	
	

}
