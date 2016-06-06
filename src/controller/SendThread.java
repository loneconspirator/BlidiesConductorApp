package controller;

public class SendThread extends Thread {
	private Blindies blindies;
	private boolean go = false;
	
	public SendThread(Blindies b) {
		blindies = b;
	}

	public void setGo(boolean go) {
		this.go = go;
	}
	
	public void run() {
		while (true) {
			try {
				sleep(10);
				if (go) blindies.send();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
