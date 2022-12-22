package agents.producer;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.TopicHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AuctionWaiting extends Behaviour {
    private boolean theEnd = false;
    private double minPrice;
    private double myPrice;
    private int contract;
    public AuctionWaiting(double minPrice, double myPrice, int contract) {
        this.minPrice = minPrice;
        this.myPrice = myPrice;
        this.contract = contract;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE),
                MessageTemplate.MatchOntology(String.valueOf(contract)));
        ACLMessage receive = getAgent().receive(mt);

        if (receive != null){
            System.out.println("Producer " + getAgent().getLocalName() + " get invitation in auction for contract№ " + contract);
            AID topic = TopicHelper.createTopic(getAgent(), receive.getContent());
            ContractsHelper.putTopic(getAgent().getLocalName(), topic);

            ACLMessage wishPrice = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
            wishPrice.setContent(String.valueOf(myPrice));
            wishPrice.addReceiver(topic);
            wishPrice.setOntology(String.valueOf(contract));
            getAgent().send(wishPrice);
            System.out.println("Producer " + getAgent().getLocalName() + " send his price for contract № " + contract);
            theEnd = true;
        }else
            block();
    }

    @Override
    public boolean done() {
        return theEnd;
    }
}
