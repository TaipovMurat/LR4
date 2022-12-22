package agents.distributor;

import agents.ahelpers.DFHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.List;

public class SendRequestToProducers extends OneShotBehaviour {
    private double neededPower;
    private String consumer;
    private int contract;
    public SendRequestToProducers(double neededPower, String consumer, int contract){
        this.neededPower = neededPower;
        this.consumer = consumer;
        this.contract = contract;
    }

    @Override
    public void action() {
        List<AID> producers = DFHelper.searchForAgents(getAgent(),"Producer");
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        for (AID producer : producers){
            msg.setContent(String.valueOf(neededPower));
            msg.addReceiver(producer);
            msg.setOntology(String.valueOf(contract));
            System.out.println("Contract num " + contract + " from " + getAgent().getLocalName() + " for buying " + neededPower);
        }
        getAgent().send(msg);
    }

}
