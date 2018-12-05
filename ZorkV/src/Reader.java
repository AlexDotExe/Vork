
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.io.File;
public class Reader {
	
	static Thread t;
	static File notify;
	static File inform;
	static int counter;
	static String saveIn;
	static String synStore[] = new String[50];
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		Thread t = Thread.currentThread();
		File notify = new File("C:/Users/PC/Google Drive/Eclipse Prefs and Dev Artifacts/hi/notify.txt");
		File inform = new File("C:/Users/PC/Google Drive/Eclipse Prefs and Dev Artifacts/hi/inform.txt");
		Scanner input = new Scanner(System.in);
		t = Thread.currentThread();
		String s = ""; 
		boolean qFlag = false;
		for(int x = 0; x<50;x++)
		synStore[x] = " ";
		//Create file for communication and wait for Zork
		wordCompare("north");
		for(String x: synStore)
		System.out.println(x);
		//Begin i/o loop
		saveIn = "";
		while(!qFlag) {
			//check that Zork is done writing to file,read it, print for user  	
		if(waitNotify(notify))
			readInform(inform);
			//get user input
		System.out.println(">");s = input.nextLine();
			//compare user input to known inputs and get actual input
		s = wordCompare(s);  
		if(saveIn.equals("q")&&s.equals("y"))qFlag = true;
		saveIn = s;
			//write input to file
		writeInform(s,inform);
			//tell zork it can read file now
		notifyZork(notify);
		}
		
		System.out.println("The game is over");
		
	}

	private static void writeInform(String s, File inform) throws FileNotFoundException {
		// TODO Auto-generated method stub
		PrintWriter write = new PrintWriter(inform);
		write.print(s);write.close();		
	}

	private static String wordCompare(String s) throws IOException {
		File out = new File("./Sdata.txt");
		
		PrintWriter write = new PrintWriter(out, "UTF-8");
		// Make a URL to the web page
		URL url = new URL("https://www.thesaurus.com/browse/"+s+"?s=t");

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
			int ArrPos = 0;
		   if(i == store.length() - 1)continue;
				if(store.charAt(i) == 't')
					if(store.charAt(i+1) == 'e')
						if(store.charAt(i+2) == 'r')
							if(store.charAt(i+3) == 'm')
								if(store.charAt(i+4) == '"')
									if(store.charAt(i+5) == ':'){
										for(int j = i+7; j>1;j++){
											if(store.charAt(j) == '"'){ArrPos = ArrPos+1;j=0;}											
											else {synStore[ArrPos] += store.charAt(j);}
										}
		} } } catch(Exception e){System.out.println(e.getMessage());}
		write.close();
		return s;
	}

	private static void notifyZork(File notify) throws FileNotFoundException {
		PrintWriter write = new PrintWriter(notify);
		write.print("0");write.close();		
	}

	private static void readInform(File inform) throws FileNotFoundException, InterruptedException {
		t.sleep(100);
		Scanner scanN = new Scanner(inform);
		while(scanN.hasNextLine()) {
			String s = scanN.nextLine();
			s = s.replaceFirst(saveIn, "");
			String[] s2 = s.split("\\.");
			for(String x: s2)
			System.out.println(x);}scanN.close();
	}
	public static boolean waitNotify(File notif) throws FileNotFoundException, InterruptedException{
		Scanner scanN = new Scanner(notif);
		while(scanN.hasNextLine()) {
			String s = scanN.nextLine();
			if(s.charAt(s.length()-1) != '1') {
			scanN.close();scanN = new Scanner(notif);}
			else {scanN.close();return true;}
		}
		return true;
	}

}


