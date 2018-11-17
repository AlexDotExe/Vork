import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class Synonyms {

	   public static void main(String[] args) throws Exception {
		   File out = new File("./Sdata.txt");
			
			PrintWriter write = new PrintWriter(out, "UTF-8");
	        // Make a URL to the web page
	        URL url = new URL("https://www.thesaurus.com/browse/north?s=t");

	        // Get the input stream through URL Connection
	        URLConnection con = url.openConnection();
	        InputStream is =con.getInputStream();
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));

	        String line = null;
	        String store = "";
	        // read each line and write to File
	        
	        while ((line = br.readLine()) != null) {
	        	store+= line;}System.out.println(store.length());  
	       try{for(int i = 0; i< store.length(); i++){	  
	    	   if(i == store.length() - 1)continue;
	        		if(store.charAt(i) == 't')
	        			if(store.charAt(i+1) == 'e')
	        				if(store.charAt(i+2) == 'r')
	        					if(store.charAt(i+3) == 'm')
	        						if(store.charAt(i+4) == '"')
	        							if(store.charAt(i+5) == ':'){
	        								write.print("synonym: ");
	        								for(int j = i+7; j>1;j++){
	        									if(store.charAt(j) == '"'){write.println(""); j = 0;}
	        									else write.print(store.charAt(j));
	        											 
	        									}
	        } } } catch(Exception e){System.out.println(e.getMessage());}
			write.close();
	        }
	}

