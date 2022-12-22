package agents.consumer;

import agents.ahelpers.TimeHelper;
import jade.core.behaviours.OneShotBehaviour;

public class NextHour extends OneShotBehaviour {
    private long timeForWaiting;

    private double price;
    private int hour;
    public NextHour(double price, int hour){
        this.price = price;
        this.hour = hour;
    }
    @Override
    public void action() {
        if (TimeHelper.getCurrentHour()<48000){
            timeForWaiting = (TimeHelper.getCurrentHour()-TimeHelper.getCurrentHour()%1000+1000)-TimeHelper.getCurrentHour();
            try {
                Thread.sleep(timeForWaiting);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            getAgent().addBehaviour(new ConsumerFSM(price, hour));
        }else{
            System.out.println("День закончен!");
        }
    }
}
