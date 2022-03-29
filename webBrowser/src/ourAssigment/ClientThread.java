package ourAssigment;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

public class ClientThread extends Thread{

	private Socket client;
	private String websiteRoot;
	private ServerSocket server;
	private String[] configArr;

	
	 public static void func(ServerSocket server,String[] configArr,String websiteRoot,Socket client) {
			
			String pertama=null;
			String folderlisttmp="fileberhasildidownload";
			int count=0;
			String message;
			int keepalive=0;
			try {

			BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			System.out.println("3");
			message=br.readLine();
			System.out.println("the message:"+message);
			String url=br.readLine();
			System.out.println("the url:"+url);

//			no5
			count++;
			if(count==1) {
				pertama=url.split(" ")[1];
//					System.out.println("url: pertama"+pertama);
			}
//			else {
//				if(url.indexOf(pertama)<0)client.close();continue;
//			}

			if(message.isEmpty()==true) {
				System.out.println("4");
				client.close();
				return;
			}
			if(message.indexOf(".ico")>=0) {
				System.out.println("4");
				client.close();
				return;
			}

			if(message.indexOf(".pdf")>=0||message.indexOf(".jpg")>=0) {
//				no 1
				String filetmp=message.split(" ")[1];
				filetmp=filetmp.substring(1);

//				no 3
				if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];


				FileInputStream newfis;

				System.out.println("website lengkap:["+websiteRoot+filetmp+"]");
				newfis=new FileInputStream(websiteRoot+filetmp);
				
				
				int width = 963;    //width of the image
			    int height = 640;   //height of the image
			    BufferedImage image = null;
			    File fi = null;

			    if(message.indexOf(".jpg")>=0) {
			    //read image
			    try{
			      fi = new File(websiteRoot+filetmp); //image file path
			      image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			      image = ImageIO.read(fi);
			      System.out.println("Reading complete.");
			    }catch(IOException e){
			      System.out.println("Error: "+e);
			    }

			    //write image
			    try{
			      fi = new File("D:\\serverku\\File\\"+filetmp);  //output file path
			      ImageIO.write(image, "jpg", fi);
			      System.out.println("Writing complete.");
			    }catch(IOException e){
			      System.out.println("Error: "+e);
			    }
			    bw.write(folderlisttmp);
			    bw.flush();
			    System.out.println("berhasil 3");
//			    continue;
			    }
			    else {
			    String newfileContent=new String(newfis.readAllBytes());
//				System.out.println(newfileContent);

				File f = new File("D:\\serverku\\File\\"+filetmp);
	            BufferedWriter gambar = new BufferedWriter(new FileWriter(f));

	            String header="HTTP/1.0 206 Partial Content\r\nContent-Type: html\r\nContent-length: "+"fileContent.length()"+"\r\nContent-Range: bytes "+"RANGE"+"\r\n\r\n"+"fileContent";
	            
	            bw.write(folderlisttmp);
//	            System.out.println(newfileContent);
	            bw.flush();
//	            client.close();
	            System.out.println("berhasil 3");
//				continue;
			    }
			    String cekalive=br.readLine();
				if(cekalive.indexOf("connection: keep-alive")>=0) keepalive=1;
			    
				
			}
			else if(message.split(" ")[1].substring(1).indexOf(".")<0) {

//				no 2
				String baru = null,tmp=null;
				try {

					  String dirLocation=null;
					  String urlafter=url.split(" ")[1];
//					  no 3
					  if(urlafter.indexOf("progjarku.com")>=0)dirLocation=configArr[5];
					  else if(urlafter.indexOf("progjarkutercinta.com")>=0)dirLocation=configArr[7];
					  System.out.println("dirlocaction1: "+dirLocation);
					  if(!Files.exists(Paths.get(dirLocation))) {
						  bw.flush();
				    	  System.out.println("4");
				    	  return;
					  }
				      List<File> myfile = Files.list(Paths.get(dirLocation))
				            .map(Path::toFile)
				            .collect(Collectors.toList());

				      for(int i=0;i<myfile.size();i++) {
				    	  tmp=myfile.get(i).toString();
//				    	  System.out.println(tmp);
				    	  tmp=tmp.replaceAll("\\\\", " ");
				    	  String[] listtmp=tmp.split(" ");
				    	  tmp=listtmp[listtmp.length-1];
				    	  System.out.println(tmp);
				    	  baru="<a href=\"/" + tmp + "\">"+tmp+"</a><br>"+baru;
				      }

				      if(myfile.size()<=0) {
				    	  bw.flush();
				    	  System.out.println("4");
				    	  return;
				      }


				} catch (IOException e) {
				      // Error while reading the directory
				}


//				baru="<a href=\"/" + "index.html" + "\">cek</a><br>";
				String fileContent=new String(baru);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
//				System.out.println(inputan);
				folderlisttmp=inputan;
				bw.write(inputan);
				bw.flush();	
				System.out.println("4");
				String cekalive=br.readLine();
				if(cekalive.indexOf("connection: keep-alive")>=0) keepalive=1;
//				client.close();
//				continue;

			}
			else if(message.indexOf("html")<0) {
				System.out.println(message);
				System.out.println("kosong");
//				continue;
			}
			else {

				String urn=message.split(" ")[1];

//					no 5
			    if(url.indexOf(".com")>=0) {
			    	System.out.println("stop!!");
					if(count>=1) {
						if(url.indexOf(pertama)<0)return;

					}

				}

//					no 3
				if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
				else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];

				urn=urn.substring(1);
				System.out.println("dirnya"+websiteRoot+urn);


				FileInputStream fis=new FileInputStream(websiteRoot+urn);
				String fileContent=new String(fis.readAllBytes());
				System.out.println(fileContent);


				while(!message.isEmpty()) {
					System.out.println(message);
					message=br.readLine();
					
					if(message.indexOf("connection: keep-alive")>=0) keepalive=1;
				}
				
				
//				System.out.println("4");
//					System.out.println("From client: "+message);
				String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
				folderlisttmp=inputan;
				bw.write(inputan);
				bw.flush();
				
//				client.close();

			}
			System.out.println("4");
			if(keepalive==1) {System.out.println("keep alive start");func(server,configArr,websiteRoot,client);}
			client.close();
			
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	
	
	public ClientThread(Socket client,String websiteRoot,ServerSocket server,String[] configArr) {
		this.client=client;
		this.websiteRoot=websiteRoot;
		this.server=server;
		this.configArr=configArr;
	}
	
	
	public void funcfirst() {
		try {
			BufferedReader br= new BufferedReader(new InputStreamReader(client.getInputStream()));
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
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		//			String websiteRoot="D:\\";
//			ServerSocket server=new ServerSocket(80);
//			System.out.println("1");
		while(true) {
//				Socket client=server.accept();
//				System.out.println("2");				
//			funcfirst();
				
			func(server,configArr,websiteRoot,client);
		}
	}

	public static void main(String[] args) throws IOException {
		try {
//			String websiteRoot="D:\\";
//			ServerSocket server=new ServerSocket(80);
//			System.out.println("1");
			
//			no 4
			String websiteRoot="D:\\";
			FileInputStream config=new FileInputStream("D:\\serverku\\config.txt");
			String configText=new String(config.readAllBytes());
			configText=configText.replaceAll("[\r\n]+", " ");
			String[] configArr=configText.split(" ");
			System.out.println(configArr[5]);
 
			InetAddress addr=InetAddress.getByName(configArr[1]);
//			InetAddress addr=(InetAddress) InetAddress.getLoopbackAddress();
			ServerSocket server=new ServerSocket(Integer.valueOf(configArr[3]),5,addr);
			
			System.out.println("1");
			
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
				ClientThread ct =new ClientThread(client,websiteRoot,server,configArr);
				ct.start();
			}
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}
	
	
	
	
}
