
/**
*
* @author Yusuf Sedat SAĞALTICI yusuf.sagaltici@ogr.sakarya.edu.tr B201210031
* @since 22.04.2023
* <p>
* Yorumları sayarak ekrana yazdıran ve bir dosyaya kaydeden program
* </p>
*/





package pdpOdev;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
 
	public static void main(String[] args) throws IOException {
		
	    String fileContent = readFile(args[0]); // okunan dosyanın içeriği
	    
	    BufferedWriter javadocWriter = null;
        BufferedWriter singleLineWriter = null;
        BufferedWriter multiLineWriter = null;
	    
        try {
            javadocWriter = new BufferedWriter(new FileWriter("javadoc.txt"));
        } catch (IOException e) {
            System.err.println("Dosya açma hatası: " + e.getMessage());
        }

        try {
            singleLineWriter = new BufferedWriter(new FileWriter("teksatir.txt"));
        } catch (IOException e) {
            System.err.println("Dosya açma hatası: " + e.getMessage());
        }

        try {
            multiLineWriter = new BufferedWriter(new FileWriter("coksatir.txt"));
        } catch (IOException e) {
            System.err.println("Dosya açma hatası: " + e.getMessage());
        }
        
	    Pattern pattern = Pattern.compile("(?s)\\b(\\w+)\\s*\\([^\\)]*\\)\\s*\\{([^{}]*|.*?)\\}");
	    Matcher matcher = pattern.matcher(fileContent);
	    while (matcher.find()) {
	    	
	    	
	        String methodName = matcher.group(1);
	        String methodContent = matcher.group(0);
	        
	        int singleLineComments = countRegexMatches(methodContent, "//[^\\n]*");
	        int multiLineComments = countRegexMatches(methodContent, "/\\*(?s).*?\\*/");
	        int javadocComments = countRegexMatches(methodContent, "/\\*\\*([^\\*]|\\*(?!/))*?@deprecated.*?\\*/");
	        
	        javadocWriter.newLine();
	        javadocWriter.newLine();
            javadocWriter.write("Fonksiyon Adı: " + methodName);
            javadocWriter.newLine();
            javadocWriter.write("Yorum Tipi: Javadoc");
            javadocWriter.newLine();
            javadocWriter.write("Yorum İçeriği:");
            javadocWriter.newLine();
            javadocWriter.write(getCommentContent(methodContent, "/\\*\\*", "\\*/"));
            javadocWriter.newLine();
            javadocWriter.write("-------------------");
            javadocWriter.newLine();
        
            singleLineWriter.newLine();
            singleLineWriter.newLine();
            singleLineWriter.write("Fonksiyon Adı: " + methodName);
            singleLineWriter.newLine();
            singleLineWriter.write("Yorum Tipi: Tek Satır");
            singleLineWriter.newLine();
            singleLineWriter.write("Yorum İçeriği:");
            singleLineWriter.newLine();
            singleLineWriter.write(getCommentContent(methodContent, "//", "\n"));
            singleLineWriter.newLine();
            singleLineWriter.write("-------------------");
            singleLineWriter.newLine();
        
            multiLineWriter.newLine();
            multiLineWriter.newLine();
            multiLineWriter.write("Fonksiyon Adı: " + methodName);
            multiLineWriter.newLine();
            multiLineWriter.write("Yorum Tipi: Çok Satırlı");
            multiLineWriter.newLine();
            multiLineWriter.write("Yorum İçeriği:");
            multiLineWriter.newLine();
            multiLineWriter.write(getCommentContent(methodContent, "/\\*", "\\*/"));
            singleLineWriter.newLine();
            multiLineWriter.write("-------------------");
            multiLineWriter.newLine();
            multiLineWriter.newLine();
	        
	        System.out.println("\t"+"Fonksiyon: " + methodName);
	        System.out.println("\t"+"\t"+"Tek satır yorum sayısı: "+ singleLineComments);
	        System.out.println("\t"+"\t"+"Çok satır yorum sayısı: "+ multiLineComments );
	        System.out.println("\t"+"\t"+"Javadoc yorum sayısı: "+ javadocComments);
	        System.out.println("----------------------------------");
	        
	        
	        
	     // Yorumları ilgili dosyalara kaydet
            
                
                
	   } 
	    javadocWriter.flush();
        javadocWriter.close();
        singleLineWriter.flush();
        singleLineWriter.close();
        multiLineWriter.flush();
        multiLineWriter.close();
	}
	
	public static String getCommentContent(String content, String startToken, String endToken) {
	    // İçeriği startToken ve endToken arasından al
	    Pattern pattern = Pattern.compile("(?s)" + Pattern.quote(startToken) + "(.*?)" + Pattern.quote(endToken));
	    Matcher matcher = pattern.matcher(content);
	    if (matcher.find()) {
	        content = matcher.group(1);
	    }

	    // Yorumun başındaki "*" karakterlerini silme
	    content = content.replaceAll("(?m)^[ \t]*\\* ?", "");

	    // Yorumun sonundaki boşlukları silme
	    content = content.replaceAll("\\s+$", "");

	    // Yorumun başındaki ve sonundaki boşlukları silme
	    content = content.trim();

	    return content;
	}


	

	public static String readFile(String path) {
	    try {
	        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
	    } catch (IOException e) {
	        System.err.println("Error reading file: " + e.getMessage());
	        return null;
	    }
	}
	
	

	public static int countRegexMatches(String input, String regex) {
	    Pattern pattern = Pattern.compile(regex);
	    Matcher matcher = pattern.matcher(input);
	    int count = 0;
	    while (matcher.find()) {
	        count++;
	    }
	    return count;
	}

	
}


