import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.util.LinkedList;
import java.awt.Color;
import javax.swing.plaf.DimensionUIResource;
//import java.awt.Graphics;
import java.awt.Graphics2D;

class Main{
	public static void main(String[] args){
		JFrame window2D = new JFrame("boids simulation");
		//window.setSize(800,800);

		DrawPanel panel = new DrawPanel();
		window2D.add(panel);
		window2D.pack();
		window2D.setLocationRelativeTo(null);
		window2D.setVisible(true);
		panel.startGameThread();
		System.out.println("eee");
		

	}
	
	//public static void gameLoop(){

	//}
}

class DrawPanel extends JPanel implements Runnable{
	private int FPS = 60;
	private Thread gameThread;
	Boid[] boids = new Boid[] {
		new Boid(200, 200, 0),
		new Boid(200, 250, -20),
		new Boid(230, 190, -10),
		new Boid(600, 250, -20),
		new Boid(190, 600, -10),
	};

	public void startGameThread(){
      gameThread = new Thread(this);
   	gameThread.start();
   }


	public DrawPanel(){
		this.setPreferredSize(new DimensionUIResource(800, 800));
		this.setBackground(Color.GRAY);
	}

	public void run(){
		double frameTime = 1000000000/FPS;//devision gives double even if both items are ints
		double nextFrame = System.nanoTime() + frameTime;
		while(gameThread != null){
			update();
			repaint();
			try{
				long remainingFrameTime = (long) nextFrame - System.nanoTime();
				remainingFrameTime = remainingFrameTime/1000000; //convert to milliseconds
				if(remainingFrameTime < 0){
					remainingFrameTime = 0;
				}
				nextFrame += frameTime;
				Thread.sleep(remainingFrameTime);
			} catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D)g;
		g2D.setColor(Color.BLUE);
		for(Boid b : boids){
		 	g2D.fillRect(b.getX(), b.getY(), 10, 10);	
		}
	}


	public void update(){
		for(Boid b : boids){
			//b.seperate(boids);
			//b.align(boids);
			//b.cohere(boids);
			b.calculateNewHeading(boids);
			b.marchForward(.5);
		}
	}
}


class Boid{
	private double x;
	private double y;
	private double heading = 0; // im gonna run everything back in degrees
	
	public Boid(int x, int y, double heading){
		this.x = x;
		this.y = y;
		this.heading = clampRotation(heading);
	}

	private Boid[] cull(Boid[] boids, int distance){
		double effectiveDistanceSqr = Math.pow(distance, 2);
		LinkedList<Boid> closeBoids = new LinkedList<Boid>();
		for(Boid b : boids){
			if(Math.pow((b.getX() - x), 2) + Math.pow((b.getY() - y), 2) < effectiveDistanceSqr && 
					b != this){
				closeBoids.add(b);
			}
		}
		Boid[] culledBoids = new Boid[closeBoids.size()];
		for(int i = 0; i < closeBoids.size(); i++){
			culledBoids[i] = closeBoids.get(i);
		}
		return culledBoids;
	}

	private Point centerOfMass(Boid[] boids, int distanceFromCenter){
		boids = cull(boids, distanceFromCenter);
		double totalX = 0;
		double totalY = 0;
		int numberOfBoids = 0;
		for(Boid b : boids){
			totalX += b.getX();
			totalY += b.getY();
			numberOfBoids++;
		}
		if(numberOfBoids == 0){
			return new Point(x, y);
		}
		return new Point(totalX/numberOfBoids, totalY/numberOfBoids);
	}

	private double clampRotation(double degrees){
		if(degrees > 360){
			degrees -= 360;
		} else if(degrees < 0){
			degrees += 360;
		}
		return degrees;

	}

	public double seperate(Boid[] boids){
		Point centerOfMass = centerOfMass(boids, 20);
		double deflection = Math.atan2((y - centerOfMass.getY()), (x - centerOfMass.getX()));
		deflection = Math.toDegrees(deflection);
		return clampRotation(deflection);
	}

	public double align(Boid[] boids){
		double tmpHeading = 0;
		for(Boid b : boids){
			if(b != this){
				tmpHeading += b.getHeading();
			}
		}
		return clampRotation(tmpHeading/(boids.length-1));
	}

	public double cohere(Boid[] boids){
		Point centerOfMass = centerOfMass(boids, 600);
		double deflection = Math.atan2((centerOfMass.getY() - y), (centerOfMass.getX() - x));
		deflection = Math.toDegrees(deflection);
		return clampRotation(deflection);
	}

	public double cohereToMiddle(){
		double deflection = Math.atan2((400 - y), (400- x));
		deflection = Math.toDegrees(deflection);
		return clampRotation(deflection);
	}

	public int getX(){
		return (int)x;
	}
	
	public int getY(){
		return (int)y;
	}

	public void calculateNewHeading(Boid[] boids){
		double tmpHeadingOne = seperate(boids) * 4;
		double tmpHeadingTwo = align(boids) * 5;
		double tmpHeadingThree = cohere(boids) * 20;
		double tmpHeadingFour = cohereToMiddle() * 0; 
		double target = (tmpHeadingOne + tmpHeadingTwo + tmpHeadingThree + tmpHeadingFour)/4;
		double diff = target - heading;
		diff = ((diff + 540) % 360) - 180;
		heading = clampRotation(heading + diff * .1);
	}

	public void marchForward(double distance){
		x+=distance*Math.cos(Math.toRadians(heading));
		y+=distance*Math.sin(Math.toRadians(heading));
	}

	public double getHeading(){
		return heading;
	}

}

class Point{
	private double x;
	private double y;

	Point(double x, double y){
		this.x = x;
		this.y = y;
	}

	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}
}
