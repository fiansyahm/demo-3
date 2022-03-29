package ourAssigment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientThread extends Thread{
	
	private Socket client;
	private String websiteRoot;

	
	
	public ClientThread(Socket client,String websiteRoot) {
		this.client=client;
		this.websiteRoot=websiteRoot;
	}
	
	@Override
	public void run() {
		try {
//			String websiteRoot="D:\\";
//			ServerSocket server=new ServerSocket(80);
//			System.out.println("1");
			while(true) {
//				Socket client=server.accept();
//				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				String message=br.readLine();
				System.out.println(message);
				String urn=message.split(" ")[1];
				
				urn=urn.substring(1);
 
				String fileContent;
				String statuscode;
				FileInputStream fis;
				
				fis=new FileInputStream(websiteRoot+urn);
				fileContent=new String(fis.readAllBytes());
				statuscode="200 OK";
				System.out.println(fileContent);
 
 
				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
				}
				System.out.println("4");
				System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
 
				bw.write(inputan);
				bw.flush();	
				client.close();
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}

	public static void main(String[] args) throws IOException {
		try {
			String websiteRoot="D:\\";
			ServerSocket server=new ServerSocket(80);
			System.out.println("1");
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
				
				ClientThread ct =new ClientThread(client,websiteRoot);
				ct.start();
			}
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
}
