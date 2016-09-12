package GamePanel;

public class LoadingThread extends Thread implements Runnable{
	public MainPanel mp;
	LoadingPanel ldp;
	public LoadingThread(MainPanel mp) {
		super();
		this.mp=mp;
		mp.setVisible(false);
		ldp = new LoadingPanel(mp);
		ldp.setBounds(0, 50, 1600, 900);
		mp.f.add(ldp);
	}

	@Override
	public void run() {
		super.run();
		try {

			while (!Thread.currentThread().isInterrupted()) {

			ldp.loop();
			ldp.repaint();
			Thread.sleep(60);
			
			}
			
			} catch (InterruptedException e) {

			// 예상된 예외이므로 무시

			} finally {

			// 마무리 작업 진행

			System.out.println("Thread is dead…");

			}
	}

}
