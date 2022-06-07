package core;

import infrastructure.Owner;
import system.AutoCrafter;
import system.OrderSolver;
import system.Printer;
import warehouse.Warehouse;

public final class Main extends Thread{
    private Thread t;
    private String threadName;
    private int delay;


    public void run() {
        System.out.println("Running " +  threadName );
        try {
            Owner owner = new Owner("Tomáš", "Černáček", "male", 21, "Stará Turá", "916 01", "Sesame St.");
            Warehouse warehouse = new Warehouse("Stará Turá", "916 01", "J. K. Gruska 15");
            if(threadName.equals("menu")){
                warehouse.mainMenu(warehouse, owner);
            }else{
                while(true) {
                    new AutoCrafter().craft();
                    new OrderSolver().solve();
                    new Printer().print(warehouse);
                    Thread.sleep(delay);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " +  threadName + " interrupted.");
        }
        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start () {
        System.out.println("Starting Thread: " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

    public Main(String threadName, int delay){
        this.threadName = threadName;
        this.delay = delay;
    }

    public static void main(String args[]) {
        Main t1 = new Main("menu", 0);
        t1.start();
        Main t2 = new Main("automation", 5000);
        t2.start();
    }
}