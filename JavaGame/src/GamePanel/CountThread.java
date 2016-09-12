package GamePanel;

public class CountThread extends Thread implements Runnable{

	public CountPanel cntP;
	
	public CountThread(MainPanel mp) {
		super();
		cntP = new CountPanel(mp);
		
	}

	@Override
	public void run() {
		super.run();
		try {

			while (!Thread.currentThread().isInterrupted()) {

			cntP.loop();
			cntP.repaint();
			Thread.sleep(1000);
			
			}

			} catch (InterruptedException e) {

			// ����� �����̹Ƿ� ����

			} finally {

			// ������ �۾� ����

			System.out.println("Thread is dead��");

			}
	}
}
