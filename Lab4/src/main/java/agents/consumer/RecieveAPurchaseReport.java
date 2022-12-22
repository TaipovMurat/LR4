package agents.consumer;

import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class RecieveAPurchaseReport extends Behaviour {

    public RecieveAPurchaseReport(){

    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.INFORM),
                MessageTemplate.MatchPerformative(ACLMessage.FAILURE));

        ACLMessage receive = getAgent().receive(mt);
    }

    @Override
    public boolean done() {
        return false;
    }
}
