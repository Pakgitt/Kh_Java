package kh.mclass;

public class Producer extends Thread {
	private Buffer buffer;

	public Producer(Buffer buffer) {
		this.buffer = buffer;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			buffer.put(i);
			System.out.println("생산자 : " + i + "번 케익을 생생");
			try {
				Thread.sleep((int) (Math.random() * 100));
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}

}
