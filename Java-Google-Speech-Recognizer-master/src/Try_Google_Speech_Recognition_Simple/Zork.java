package Try_Google_Speech_Recognition_Simple;

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

public class Zork extends Thread{
	static Process pr; 
	static Thread t;
	static File notify = new File("./notify.txt");
	static File inform = new File("./inform.txt");
	static File out = new File("./ALLinputs.txt");
	static int counter;
	static String saveIn = "";;
	static String inputs[] = new String [60];
	static String synStore[] = new String[50];
	 
	//File notify = new File("C:/Users/PC/Google Drive/Eclipse Prefs and Dev Artifacts/hi/notify.txt");
	//File inform = new File("C:/Users/PC/Google Drive/Eclipse Prefs and Dev Artifacts/hi/inform.txt");
	//Thread used for sleeping
	 @Override
	 public void run() {
	 Thread t = Thread.currentThread();
	//Local variables
	String s = "";
	boolean except;
	boolean qFlag = false; 
	Scanner input = new Scanner(System.in);
	t = Thread.currentThread();
	try {
		initialize();
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	//Begin i/o loop
	while(!qFlag) {
		except = true;
		//check that Zork is done writing to file,read it, print for user  	
		try {
			if(waitNotify())readInform();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//get user input
		while(except) {
			try {
				int check =1;
				TryGoogleSpeechRecognitionSimple.zorkout.append(">");
				while(check != 0) {
					check = TryGoogleSpeechRecognitionSimple.tell;
					this.sleep(1000);
					if(check == TryGoogleSpeechRecognitionSimple.tell && check != 1)check =0;}
					s = TryGoogleSpeechRecognitionSimple.output;TryGoogleSpeechRecognitionSimple.tell =1;
		//compare user input to known inputs and get actual input
					s = wordCompare(s);
					except = false;
				}
			catch(Exception e) { TryGoogleSpeechRecognitionSimple.zorkout.append("\n"+"Not even Thesaurus.com knows what you're saying, try again");}}  
			
//			TryGoogleSpeechRecognitionSimple.zorkout.append("\n"+"input passed : "+ s );
			if(saveIn.equals("q")&&s.equals("y"))qFlag = true;
			saveIn = s;
		//write input to file
			try {
				writeInform(s);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//tell zork it can read file now
			try {
				notifyZork();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	TryGoogleSpeechRecognitionSimple.zorkout.append("\n"+"The game is over");
	pr.destroy();
	
}
	 private static void initialize() throws IOException {
			//initialized synonym storage
			for(int x = 0; x<50;x++)
				synStore[x] = " ";
	//initialize acceptable inputs
			Scanner scan = new Scanner(out);
			for(int i = 0 ;scan.hasNextLine(); i++)
				inputs[i] = scan.nextLine();
			scan.close();
	//initialize inform
			writeInform("");
			 Runtime rt = Runtime.getRuntime();
	          pr = rt.exec("./zork.exe");         

		}

		private static void writeInform(String s) throws FileNotFoundException {
			// TODO Auto-generated method stub
			PrintWriter write = new PrintWriter(inform);
			write.print(s);write.close();		
		}

		
		private static void notifyZork() throws FileNotFoundException {
			PrintWriter write = new PrintWriter(notify);
			write.print("0");write.close();		
		}

		private static void readInform() throws FileNotFoundException, InterruptedException {
			t.sleep(500);
			Scanner scanN = new Scanner(inform);
			while(scanN.hasNextLine()) {
				String s = scanN.nextLine();
				s = s.replaceFirst(saveIn, "");
				String[] s2 = s.split("\\.");
				for(String x: s2)
				TryGoogleSpeechRecognitionSimple.zorkout.append("\n"+x);}scanN.close();
		}
		public static boolean waitNotify() throws FileNotFoundException, InterruptedException{
			Scanner scanN = new Scanner(notify);
			while(scanN.hasNextLine()) {
				String s = scanN.nextLine();
				if(s.charAt(s.length()-1) != '1') {
				scanN.close();scanN = new Scanner(notify);}
				else {scanN.close();return true;}
			}
			return true;
		}
		private static String wordCompare(String s) throws IOException {
			//break input into words
			String hold = s;
			String in[] = new String [s.split(" ").length];
			in = s.split(" ");
			//newinput that will be passed
			String newIn = "";

			//start checking input
			for(int x = 0; x <in.length;x++) {
				boolean exists = false;
				boolean flag = false;
				s = in[x];
				//check if already accepted
				for(String match: inputs)
					if(s.equalsIgnoreCase(match))exists = true;
				//if it is, add to newInput and continue
				if(exists) {newIn +=" " + s + " "; continue;}
				//else search web for synonyms
			URL url = new URL("https://www.thesaurus.com/browse/"+s+"?s=t");
			// Get the input stream through URL Connection
			URLConnection con = url.openConnection();
			InputStream is = con.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));

			String line = null;
			String store = "";
			// read each line to and store synonyms in array
			int ArrPos = 0;
			while ((line = br.readLine()) != null) {
				store+= line;}  
			try{for(int i = 0; i< store.length(); i++){	  
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
			} } } catch(Exception e){e.getMessage();}
			
			for(int i = 0; i<inputs.length && !flag;i++)
				for(int j = 0; j<synStore.length && !flag;j++) {
					if(synStore[j].equals(" ")) {j= synStore.length +1; continue;}
					if(inputs[i].length()>4 && synStore[j].contains(inputs[i])) 
					{ newIn += " "+ inputs[i] + " ";flag = true;}}
			if(flag == false)newIn+=" "+in[x]+ " ";
			}
			
			return hold;
		}
	 }