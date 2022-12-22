package agents.producer;

import agents.ahelpers.ContractsHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProducerAuction extends Behaviour {
    private boolean theEnd = false;
    private double minPrice;
    private double myPrice;
    private int contract;
    public ProducerAuction(double minPrice, double myPrice, int contract) {
        this.minPrice = minPrice;
        this.myPrice = myPrice;
        this.contract = contract;
    }

    @Override
    public void action() {
        AID topic = ContractsHelper.topicGet(getAgent().getLocalName());

        MessageTemplate mt = MessageTemplate.or(
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL),
                        MessageTemplate.MatchOntology(String.valueOf(contract))),
                MessageTemplate.and(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                        MessageTemplate.MatchOntology(String.valueOf(contract))));

        ACLMessage receive = getAgent().receive(mt);

        if (receive != null){
            if ((receive.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) &&
                    (!receive.getSender().getLocalName().equals(getAgent().getLocalName()))){
                double yourPrice = Double.parseDouble(receive.getContent());

                if (yourPrice < myPrice && yourPrice * 0.9 > minPrice){
                    myPrice = yourPrice * 0.9;
                     ACLMessage myNewPrice = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                     myNewPrice.setContent(String.valueOf(myPrice));
                     myNewPrice.setOntology(String.valueOf(contract));
                     myNewPrice.addReceiver(topic);
                     getAgent().send(myNewPrice);
                }else {
                    System.out.println("Producer " + getAgent().getLocalName() + " couldn't decrease his price in contract № "+ contract);
                }
            }else if (receive.getPerformative() == ACLMessage.INFORM){
                System.out.println("Producer " + getAgent().getLocalName() + " get noticed about auction ending. Contract № ");
                theEnd = true;
            }
        }else {
            block();
        }
    }

    @Override
    public boolean done() {
        return theEnd;
    }
}
