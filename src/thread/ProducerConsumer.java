package thread;

public class ProducerConsumer {
	
	public static void main(String[] args) {  
		Resource resource = new Resource();  
		new Thread(new ProducerThread(resource)).start();  
		new Thread(new ConsumerThread(resource)).start();  
		new Thread(new ProducerThread(resource)).start();  
		new Thread(new ConsumerThread(resource)).start();  
		new Thread(new ProducerThread(resource)).start();  
		new Thread(new ConsumerThread(resource)).start();  
	}   
}

class Resource {  
    private int number = 0;  
    public synchronized void increace() {  
        while (number != 0) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        number++;  
        System.out.println("生产"+number);  
        notify();  
    }  

    public synchronized void decreace() {  
        while (number == 0) {  
            try {  
                wait();  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
        }  
        number--;  
        System.out.println("消费"+number);  
        notify();  
    }  
}  

class ProducerThread implements Runnable {  
    private Resource resource;  
  
    public ProducerThread(Resource resource) {  
        this.resource = resource;  
    }  
  
    @Override  
    public void run() {  
        for (int i = 0; i < 10; i++) {  
            try {  
                Thread.sleep((long) (Math.random() * 1000));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            resource.increace();  
        }  
    }  
}  

class ConsumerThread implements Runnable {  
    private Resource resource;  
  
    public ConsumerThread(Resource resource) {  
        this.resource = resource;  
    }  
  
    @Override  
    public void run() {  
        for (int i = 0; i < 10; i++) {  
            try {  
                Thread.sleep((long) (Math.random() * 1000));  
            } catch (InterruptedException e) {  
                e.printStackTrace();  
            }  
            resource.decreace();  
        }  
    }  
}  
