package agents.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DivisionContract extends Behaviour {
    private final double neededPower;
    private final double maxPrice;
    private final String consumer;
    private final boolean div;
    private final int contract;
    private double firstPartOfPower;
    private double secondPartOfPower;
    private double firstPartOfMaxPrice;
    private double secondPartOfMaxPrice;
    private boolean theEnd = false;

    public DivisionContract(double neededPower, double maxPrice, String consumer, boolean div, int contract){
        this.neededPower = neededPower;
        this.maxPrice = maxPrice;
        this.consumer = consumer;
        this.div = div;
        this.contract = contract;
    }

    @Override
    public void onStart() {
        firstPartOfPower = neededPower / 2;
        secondPartOfPower = neededPower - firstPartOfPower;

        firstPartOfMaxPrice = maxPrice / 2;
        secondPartOfMaxPrice = maxPrice - firstPartOfMaxPrice;

        if (!div){
            System.out.println("_________Division contract №" + contract + "_________");
            System.out.println("Contract №" + 10+contract  + " for: " + firstPartOfPower +" kW");
            System.out.println("Contract №" + 100+contract  + " for: " + secondPartOfPower +" kW");
        }
        getAgent().addBehaviour(new DistributorFSM(firstPartOfPower, firstPartOfMaxPrice, consumer, 10+contract ));
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchProtocol("InformDivision");
        ACLMessage receive = getAgent().receive(mt);
        if (receive != null){
            System.out.println("Contract №" +100+ contract  + " got permission for start");
            getAgent().addBehaviour(new DistributorFSM(secondPartOfPower,secondPartOfMaxPrice, consumer, 100+contract));
            theEnd = true;
        }else {
            block();
        }

    }

    @Override
    public boolean done() {
        return theEnd;
    }
}
