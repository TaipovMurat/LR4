package agents.distributor;

import jade.core.behaviours.FSMBehaviour;

public class DistributorFSM extends FSMBehaviour {

    public DistributorFSM(double neededPower, double price, String consumer, int contract){

        registerFirstState(new SendRequestToProducers(neededPower, consumer, contract),
                "sendRequestToProducers");

        //Жду ответа об аукционе от производителей
        registerState(new WaitingAnswerFromProducers(contract), "waitingAnswer");

        //Получить предложение и таймер аукциона
        registerLastState(new StartAuction(getAgent(), 1000, price, consumer, neededPower, contract),
                "auction");

        //деление контракта
        registerLastState(new DivisionContract(neededPower, price, consumer, false, contract), "contractDivide");

//        //Ожидание подтверждения от потребителя
//        registerLastState(new SendReport(getAgent(), 500, contract),
//                "getAccess");


        registerDefaultTransition("sendRequestToProducers", "waitingAnswer");
        registerTransition("waitingAnswer", "contractDivide", 0);
        registerTransition("waitingAnswer", "auction", 1);
//        registerDefaultTransition("auction", "getAccess");

    }

}
