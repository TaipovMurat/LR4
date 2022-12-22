package agents.distributor;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.DFHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;
import java.util.List;

public class WaitingAnswerFromProducers extends Behaviour {
    private int contract;

    private List<AID> agreededProducers = new ArrayList<>();

    private int positiveAnswer = 0;
    private int answeredProducers = 0;
    private boolean getAnswers = false;

    public WaitingAnswerFromProducers(int contract) {
        this.contract = contract;
    }



    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.or(MessageTemplate.MatchPerformative(ACLMessage.AGREE),
                MessageTemplate.MatchPerformative(ACLMessage.REFUSE));

        ACLMessage receive = getAgent().receive(mt);
        if (receive != null) {
            if (receive.getPerformative() == ACLMessage.AGREE) {
                positiveAnswer++;
                agreededProducers.add(receive.getSender());
                System.out.println(agreededProducers.size() + "__________");
                String topic = getAgent().getLocalName() + " contracts";
                ContractsHelper.getAgreededProducers().put("Agreeded Producers" + getAgent().getLocalName(), agreededProducers);
                ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
                for (AID producer : agreededProducers){
                    msg.setContent(topic);
                    msg.setOntology(String.valueOf(contract));
                    msg.addReceiver(producer);
                }
                getAgent().send(msg);
            }
            answeredProducers++;

            List<AID> producers = DFHelper.searchForAgents(getAgent(), "Producer");
            System.out.println("________" + answeredProducers);
            if (answeredProducers == producers.size()){
                if (positiveAnswer > 0){
                    System.out.println("________" + positiveAnswer);
//                    String topic = getAgent().getLocalName() + " contracts";
//                    ContractsHelper.getAgreededProducers().put("Agreeded Producers" + getAgent().getLocalName(), agreededProducers);
//                    ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
//                    for (AID producer : agreededProducers){
//                        msg.setContent(topic);
//                        msg.setOntology(String.valueOf(contract));
//                        msg.addReceiver(producer);
//                    }
//                    getAgent().send(msg);
                }
            }
            getAnswers = true;
        }else{
            block();
        }
    }

    @Override
    public boolean done() {
        return getAnswers;
    }

    @Override
    public int onEnd(){
        if (positiveAnswer == 0){
            return 0;
        }else {
            return 1;
        }
    }
}
