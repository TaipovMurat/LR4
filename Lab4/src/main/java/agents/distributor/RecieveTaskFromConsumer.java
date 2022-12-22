package agents.distributor;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RecieveTaskFromConsumer extends Behaviour {

    private double price;
    private int numberOfContract;
    private double power;

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

        ACLMessage recieve = getAgent().receive(mt);

        if (recieve!=null){
            String consumer = recieve.getSender().getLocalName();
            String[] content = recieve.getContent().split(";");
            power = Double.parseDouble(content[0]);
            price = Double.parseDouble(content[1]);
            numberOfContract = Integer.parseInt(content[2]);

            System.out.println("Recieved a contract " + numberOfContract +" from "+ consumer +" to buy energy: " + power);

            getAgent().addBehaviour(new SendRequestToProducers(power,consumer,numberOfContract));

            getAgent().addBehaviour(new DistributorFSM(power, price, consumer, numberOfContract));
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
