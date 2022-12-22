package agents.distributor;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.DFHelper;
import agents.ahelpers.TimeHelper;
import agents.ahelpers.TopicHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StartAuction extends ParallelBehaviour {

    private int contract;

    private Map<String, Double> producers = new HashMap<>();

    public StartAuction(Agent agent, long wakeUpTime, double maxPrice, String consumer,
                        double neededPower, int contract){
        super(agent,WHEN_ANY);
        this.contract = contract;

        Behaviour receiveMessages = new Behaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.AGREE);
                ACLMessage receive = getAgent().receive(mt);
                if (receive != null){
                    double price = Double.parseDouble(receive.getContent());
                    producers.put(receive.getSender().getLocalName(), price);
                }else {
                    block();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        };

        Behaviour sendMessages = new WakerBehaviour(getAgent(), wakeUpTime) {
            private double minPrice = 200.0;
            private String bestProducer = null;

            @Override
            protected void onWake() {
                AID topicAID = TopicHelper.createTopic(getAgent(), getAgent().getLocalName() + TimeHelper.getCurrentHour());
                ACLMessage msgOver = new ACLMessage(ACLMessage.INFORM);
                msgOver.setContent("Time is over");
                msgOver.setOntology(String.valueOf(contract));
                msgOver.addReceiver(topicAID);
                getAgent().send(msgOver);

                List<AID> agreededProducers = ContractsHelper.getAgreededProducers().get("Agreeded Producers"+getAgent().getLocalName());
                for (AID prods : agreededProducers){
                    if (producers.get(prods.getLocalName()) < minPrice){
                        minPrice = producers.get(prods.getLocalName());
                        bestProducer = prods.getLocalName();
                        ContractsHelper.getNamesOfTheBestProducers().put("bestProducer " + getAgent().getLocalName(), bestProducer);
                    }
                }
                System.out.println("Contract № " + contract + " " + getAgent().getLocalName() +
                        " finded the best producer. It's " + bestProducer + " with price: "
                        + minPrice + " and power: " + neededPower);

                ACLMessage msgToProducer = new ACLMessage(ACLMessage.CONFIRM);
                ACLMessage msgToConsumer = new ACLMessage(ACLMessage.CONFIRM);

                if (minPrice <= maxPrice){
                    msgToProducer.setPerformative(ACLMessage.AGREE);
                    msgToProducer.setContent("Contract accepted");
                    msgToProducer.setOntology(String.valueOf(contract));
                    msgToProducer.addReceiver(new AID(bestProducer, false));

                    msgToConsumer.setPerformative(ACLMessage.AGREE);
                    msgToConsumer.setContent("Successfully transaction");
                    msgToConsumer.setOntology(String.valueOf(contract));
                    msgToConsumer.addReceiver(new AID(consumer, false));

                    getAgent().send(msgToProducer);
                    getAgent().send(msgToConsumer);
                }else {
                    msgToConsumer.setPerformative(ACLMessage.FAILURE);
                    msgToConsumer.setContent("minPrice: " + minPrice + " neededPower: "
                            + neededPower + " Best producer: " + bestProducer);
                    msgToConsumer.setOntology(String.valueOf(contract));
                    msgToConsumer.addReceiver(new AID(consumer, false));
                }
            }
        };

        addSubBehaviour(receiveMessages);
        addSubBehaviour(sendMessages);
    }
}
