package ourAssigment;
 
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
 
public class server {
	public static void transfer(final File f, Socket socket,FileInputStream fis) throws IOException {		
		String fileContent=new String(fis.readAllBytes());
		
		String header="HTTP/1.0 206 Partial Content\r\nContent-Type: application/jpg"+"\r\nContent-length: "+fileContent.length()+"\r\nContent-Range: bytes "+"0-"+(fileContent.length()-1)+"/"+fileContent.length()+"\r\n\r\n";
	    
		System.out.println("-----------------------");
		System.out.println(header);
		final PrintStream outStream = new PrintStream(socket.getOutputStream());
	    final BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(f));
	    final byte[] buffer = new byte[4096];
	    outStream.print(header);
	    for (int read = inStream.read(buffer); read >= 0; read = inStream.read(buffer))
	        outStream.write(buffer, 0, read);
	    inStream.close();
	    outStream.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		soal_all();
	}
 	

	 public static void func(Socket client,String[] configArr,String websiteRoot) {
		
		String pertama=null;
		String folderlisttmp="fileberhasildidownload";
		int count=0;
		String message;
		int keepalive=0;
		
		System.out.println("1x1x");
		try {
			
		
		System.out.println("2");

		BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		System.out.println("3");
		message=br.readLine();
		System.out.println("the message:"+message);
		String url=br.readLine();
		System.out.println("the url:"+url);

//		no5
		count++;
		if(count==1) {
			pertama=url.split(" ")[1];
//				System.out.println("url: pertama"+pertama);
		}
//		else {
//			if(url.indexOf(pertama)<0)client.close();continue;
//		}

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
//			no 1
			String filetmp=message.split(" ")[1];
			filetmp=filetmp.substring(1);

//			no 3
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
		    transfer(fi, client,newfis);
//		    bw.write(folderlisttmp);
		    bw.flush();
		    System.out.println("berhasil 3");
//		    continue;
		    }
		    else {
		    String newfileContent=new String(newfis.readAllBytes());
//			System.out.println(newfileContent);

			File f = new File("D:\\serverku\\File\\"+filetmp);
            BufferedWriter gambar = new BufferedWriter(new FileWriter(f));
            transfer(fi, client,newfis);
//            bw.write(folderlisttmp);
//            System.out.println(newfileContent);
            bw.flush();
//            client.close();
            System.out.println("berhasil 3");
//			continue;
		    }
		    
		    String cekalive=br.readLine();
			if(cekalive.indexOf("connection: keep-alive")>=0) keepalive=1;
		}
		else if(message.split(" ")[1].substring(1).indexOf(".")<0) {

//			no 2
			String baru = "<table><tr><th>Name</th><th>Size</th><th>Last Modified</th></tr>",tmp=null;
			try {

				  String dirLocation=null;
				  String urlafter=url.split(" ")[1];
//				  no 3
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

			      SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			      for(int i=0;i<myfile.size();i++) {
			    	  tmp=myfile.get(i).toString();
//			    	  System.out.println(tmp);
			    	  tmp=tmp.replaceAll("\\\\", " ");
			    	  String[] listtmp=tmp.split(" ");
			    	  tmp=listtmp[listtmp.length-1];
			    	  System.out.println(tmp);
//			    	  baru="<a href=\"/" + tmp + "\">"+tmp+"</a><br>"+baru;
			    	  baru=baru+"<tr><td>"+ "<a href=\"/" + tmp + "\">"+tmp+"</a>"+ "</td><td>"+myfile.get(i).length()+"</td><td>"+sdf.format(myfile.get(i).lastModified())+"</td></tr>";
			      }
			      baru=baru+"</table>";

			      if(myfile.size()<=0) {
			    	  bw.flush();
			    	  System.out.println("4");
			    	  return;
			      }


			} catch (IOException e) {
			      // Error while reading the directory
			}


//			baru="<a href=\"/" + "index.html" + "\">cek</a><br>";
			String fileContent=new String(baru);
			String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
//			System.out.println(inputan);
			folderlisttmp=inputan;
			bw.write(inputan);
			bw.flush();	
			System.out.println("4");
			String cekalive=br.readLine();
			if(cekalive.indexOf("connection: keep-alive")>=0) keepalive=1;
//			client.close();
//			continue;

		}
		else if(message.indexOf("html")<0) {
			System.out.println(message);
			System.out.println("kosong");
//			continue;
		}
		else {

			String urn=message.split(" ")[1];

//				no 5
		    if(url.indexOf(".com")>=0) {
		    	System.out.println("stop!!");
				if(count>=1) {
					if(url.indexOf(pertama)<0)return;

				}

			}

//				no 3
			if(url.indexOf("progjarku.com")>=0)websiteRoot=configArr[5];
			else if(url.indexOf("progjarkutercinta.com")>=0)websiteRoot=configArr[7];

			urn=urn.substring(1);
			System.out.println("dirnya last12: "+websiteRoot+urn);


			FileInputStream fis=new FileInputStream(websiteRoot+urn);
			String fileContent=new String(fis.readAllBytes());
			System.out.println(fileContent);


			System.out.println("-x-x-x-");
			while(!message.isEmpty()) {
				System.out.println(message);
				message=br.readLine();
				
				if(message.indexOf("connection: keep-alive")>=0) keepalive=1;
			}
			System.out.println("close client 1");
			
//			System.out.println("4");
//				System.out.println("From client: "+message);
			String inputan="HTTP/1.0 200 OK\r\nContent-Type: html\r\nContent-length: "+fileContent.length()+"\r\n\r\n"+fileContent;
//			if(keepalive==1)System.out.println("keep alive start");
			folderlisttmp=inputan;
			bw.write(inputan);
			bw.flush();
//			client.close();


		}
		if(keepalive==1) {System.out.println("keep alive start");func(client,configArr,websiteRoot);}
		System.out.println("4");
		client.close();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
//			e1.printStackTrace();
			System.out.println("ERROR");
		}
	}
	
	
	public static void soal_all() {
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
			Socket client;
			
			while(true) {
				client = server.accept();
				func(client,configArr,websiteRoot);
 
			}	
			
			
			//
		}
		catch(IOException ex) {
//			Logger.getLogger(server.getName()).log(Level.SEVERE,null,ex);
//			 soal_all();
		}
		
		
	}

	
	
	
}
