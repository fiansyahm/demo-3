package ourAssigment;

public class MyThread extends Thread{

	private String nama;
	private int n;
	private int result;

	
	public MyThread(String name,int n) {
		setNama(name);
		setN(n);
		setResult(0);
	}
	
	@Override
	public void run() {
		synchronized(this) {
			for(int i=1;i<n;i++) {
//				System.out.println(getNama()+" Running. Number: "+i);
				result+=1;
			}
		}
		
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
	
	
	
}
