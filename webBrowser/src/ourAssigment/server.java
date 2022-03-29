package ourAssigment;
 
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
 
public class server {
	public static void main(String[] args) throws IOException {
 
 
		soal_all();
 
 
	}
 
	
	public static void soal_all() {
		int count=0;
		String pertama=null;
		String folderlisttmp=null;
		try {
 
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
			String message;
			while(true) {
				Socket client=server.accept();
				System.out.println("2");
 
				BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
				BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
				System.out.println("3");
				message=br.readLine();
				System.out.println("the message:"+message);
				String url=br.readLine();
				System.out.println("the url:"+url);
 
//				no5
				count++;
				if(count==1) {
					pertama=url.split(" ")[1];
	//				System.out.println("url: pertama"+pertama);
				}
//				else {
//					if(url.indexOf(pertama)<0)client.close();continue;
//				}
 
				if(message.isEmpty()==true)continue;
				if(message.indexOf(".ico")>=0)continue;
 
				if(message.indexOf(".pdf")>=0||message.indexOf(".jpg")>=0) {
//					no 1
					String filetmp=message.split(" ")[1];
					filetmp=filetmp.substring(1);
 
//					no 3
					if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
					else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];
 
 
					FileInputStream newfis;
 
					System.out.println("website lengkap:["+websiteRoot+filetmp+"]");
					newfis=new FileInputStream(websiteRoot+filetmp);
 
					String newfileContent=new String(newfis.readAllBytes());
//					System.out.println(newfileContent);
 
					File f = new File("D:\\serverku\\File\\"+filetmp);
		            BufferedWriter gambar = new BufferedWriter(new FileWriter(f));
 
		            bw.write(folderlisttmp);
//		            System.out.println(newfileContent);
		            bw.flush();
//		            client.close();
		            System.out.println("berhasil 3");
					continue;
				}
				else if(message.split(" ")[1].substring(1).indexOf(".")<0) {
 
//					no 2
					String baru = null,tmp=null;
					try {
 
						  String dirLocation=null;
						  String urlafter=url.split(" ")[1];
//						  no 3
						  if(urlafter.indexOf("progjarku.com")>=0)dirLocation=configArr[5];
						  else if(urlafter.indexOf("progjarkutercinta.com")>=0)dirLocation=configArr[7];
						  System.out.println("dirlocaction1: "+dirLocation);
						  if(!Files.exists(Paths.get(dirLocation)))continue;
					      List<File> myfile = Files.list(Paths.get(dirLocation))
					            .map(Path::toFile)
					            .collect(Collectors.toList());
 
					      for(int i=0;i<myfile.size();i++) {
					    	  tmp=myfile.get(i).toString();
//					    	  System.out.println(tmp);
					    	  tmp=tmp.replaceAll("\\\\", " ");
					    	  String[] listtmp=tmp.split(" ");
					    	  tmp=listtmp[listtmp.length-1];
					    	  System.out.println(tmp);
					    	  baru="<a href=\"/" + tmp + "\">"+tmp+"</a><br>"+baru;
					      }
 
					      if(myfile.size()<=0)continue;
 
 
					} catch (IOException e) {
					      // Error while reading the directory
					}
 
 
//					baru="<a href=\"/" + "index.html" + "\">cek</a><br>";
					String fileContent=new String(baru);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
//					System.out.println(inputan);
					folderlisttmp=inputan;
					bw.write(inputan);
					bw.flush();	
					System.out.println("4");
//					client.close();
//					continue;
 
				}
				else if(message.indexOf("html")<0) {
					System.out.println(message);
					System.out.println("kosong");
//					continue;
				}
				else {
 
					String urn=message.split(" ")[1];
 
	//				no 5
				    if(url.indexOf(".com")>=0) {
						if(count>=1) {
							if(url.indexOf(pertama)<0)continue;
 
						}
 
					}
 
	//				no 3
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
					}
//					System.out.println("4");
	//				System.out.println("From client: "+message);
					String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
					folderlisttmp=inputan;
					bw.write(inputan);
					bw.flush();	
//					client.close();
 
 
 
 
				}
				System.out.println("4");
//				client.close();
 
 
			}	
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
		}
	}

	
	
	
	
	
}
