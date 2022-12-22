package agents.consumer;

import jade.core.behaviours.FSMBehaviour;

public class ConsumerFSM extends FSMBehaviour {

    public ConsumerFSM(double maxPrice, int hour) {


        //Отправить запрос на питание распределителю
        registerFirstState(new SendTaskForDistributor(maxPrice, hour), "firstState");

        //Ожидание результатов аукциона от распределителя
        registerState(new RecieveAPurchaseReport(), "middleState");


        //Ожидание нового часа
        registerLastState(new NextHour(maxPrice, hour), "lastState");
        System.out.println("sasaa");


        registerDefaultTransition("firstState", "middleState");
        registerDefaultTransition("middleState", "lastState");

    }
}
