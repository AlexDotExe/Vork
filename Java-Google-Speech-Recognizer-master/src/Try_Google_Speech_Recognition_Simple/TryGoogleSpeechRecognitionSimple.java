package Try_Google_Speech_Recognition_Simple;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.io.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.*;
import org.apache.log4j.chainsaw.Main;

import com.darkprograms.speech.microphone.Microphone;
import com.darkprograms.speech.recognizer.GSpeechDuplex;
import com.darkprograms.speech.recognizer.GSpeechResponseListener;
import com.darkprograms.speech.recognizer.GoogleResponse;

import net.sourceforge.javaflacencoder.FLACFileWriter;

public class TryGoogleSpeechRecognitionSimple implements GSpeechResponseListener {	
	static JTextArea zorkout = new JTextArea();
	static String output = "";
	static int tell = 1;
	public static void main(String[] args) throws IOException, InterruptedException {
		Zork z = new Zork();
		z.start();
			
		final Microphone mic = new Microphone(FLACFileWriter.FLAC);
		//Don't use the below google api key , make your own !!! :) 
		GSpeechDuplex duplex = new GSpeechDuplex("AIzaSyBOti4mM-6x9WDnZIjIeyEU21OpBXqWBgw");
		
		duplex.setLanguage("en");
		
		JFrame frame = new JFrame("Zork 2018 Edition");
		frame.setDefaultCloseOperation(3);
		
		zorkout.setEditable(false);
		zorkout.setWrapStyleWord(true);
		zorkout.setLineWrap(true);
		
		JTextArea response = new JTextArea();
		response.setEditable(false);
		response.setWrapStyleWord(true);
		response.setLineWrap(true);
		
		final JButton record = new JButton("Play Game");
		final JButton stop = new JButton("Stop");
		stop.setEnabled(false);
		
		record.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				new Thread(() -> {
					try {
						duplex.recognize(mic.getTargetDataLine(), mic.getAudioFormat());
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
				}).start();
				record.setEnabled(false);
				stop.setEnabled(true);
			}
		});
		stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mic.close();
				duplex.stopSpeechRecognition();
				record.setEnabled(true);
				stop.setEnabled(false);
			}
		});
		JLabel infoText = new JLabel(
				"<html><div style=\"text-align: center;\">Talk to the game.\n<br>On.<center></html>",
				
				0);
		frame.getContentPane().add(infoText);
		infoText.setAlignmentX(0.5F);
		JScrollPane scroll = new JScrollPane(response);
		JScrollPane scroll2 = new JScrollPane(zorkout);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), 1));
		frame.getContentPane().add(scroll2);
		frame.getContentPane().add(scroll);
		JPanel recordBar = new JPanel();
		frame.getContentPane().add(recordBar);
		recordBar.setLayout(new BoxLayout(recordBar, 0));
		recordBar.add(record);
		recordBar.add(stop);
		frame.setVisible(true);
		frame.pack();
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		
		duplex.addResponseListener(new GSpeechResponseListener() {
			String old_text = "";
			
			public void onResponse(GoogleResponse gr) {
				output = gr.getResponse();
				if (gr.getResponse() == null) {
					this.old_text = response.getText();
					if (this.old_text.contains("(")) {
						this.old_text = this.old_text.substring(0, this.old_text.indexOf('('));
					}
					System.out.println("Paragraph Line Added");
					this.old_text = ( response.getText() + "\n" );
					this.old_text = this.old_text.replace(")", "").replace("( ", "");
					response.setText(this.old_text);
					return;
				}
				if (output.contains("(")) {
					output = output.substring(0, output.indexOf('('));
				}
				if (!gr.getOtherPossibleResponses().isEmpty()) {
					output = output + " (" + (String) gr.getOtherPossibleResponses().get(0) + ")";
				}
				System.out.println(output);
				response.setText("");
				response.append(this.old_text);
				response.append(output);
				tell+=1;
			}
		});
		
	}
	
	@Override
	public void onResponse(GoogleResponse paramGoogleResponse) {
		// TODO Auto-generated method stub
		
	}
}



