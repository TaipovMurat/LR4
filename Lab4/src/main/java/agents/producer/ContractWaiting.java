package agents.producer;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.TimeHelper;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Map;

public class ContractWaiting extends ParallelBehaviour {
    public ContractWaiting(Agent agent, long wakeUp, double neededPower, int contract) {
        super(agent, WHEN_ALL);

        Behaviour producerReceive = new Behaviour() {
            private double myPower;
            private boolean theEnd = false;
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchOntology(String.valueOf(contract));
                ACLMessage receive = getAgent().receive(mt);

                if (receive != null){
                    if (receive.getPerformative() == ACLMessage.AGREE){
                        switch (getAgent().getLocalName()){
                            case "TPP":
                                myPower = ContractsHelper.getPowerOfTPP().get(TimeHelper.getCurrentHour());
                                break;
                            case "WPP":
                                myPower = ContractsHelper.getPowerOfWPP().get(TimeHelper.getCurrentHour());
                                break;
                            case "SPP":
                                myPower = ContractsHelper.getPowerOfSPP().get(TimeHelper.getCurrentHour());
                                break;
                        }
                        double balancePower = myPower - neededPower;

                        if (balancePower >= 0.0){
                            switch (getAgent().getLocalName()){
                                case "TPP":
                                    myPower = ContractsHelper.powerOfTPPPut(TimeHelper.getCurrentHour(), balancePower);
                                    break;
                                case "WPP":
                                    myPower = ContractsHelper.powerOfWPPPut(TimeHelper.getCurrentHour(), balancePower);
                                    break;
                                case "SPP":
                                    myPower = ContractsHelper.powerOfSPPPut(TimeHelper.getCurrentHour(), balancePower);
                                    break;
                            }
                            System.out.println("Producer " + getAgent().getLocalName() + " confirm contract № "
                                    + contract + ". Power remaining: " + balancePower);
                        }else {
                            System.out.println("Producer " + getAgent().getLocalName() +
                                    " haven't enough power for contract № " + contract);
                        }
                    }else if (receive.getPerformative() == ACLMessage.REFUSE){
                        System.out.println("Producer " + getAgent().getLocalName() + "get message, that his power is too expensive");
                    }
                    if ((receive.getPerformative() == ACLMessage.AGREE) || (receive.getPerformative() == ACLMessage.REFUSE)){
                        if (contract >= 100 && contract < 1000){
                            System.out.println("Producer " + getAgent().getLocalName() + " send permission for second contract №" + contract);

                            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                            msg.addReceiver(new AID(receive.getSender().getLocalName(), false));
                            msg.setProtocol("InformDivision");
                            msg.setOntology(String.valueOf(contract));
                            getAgent().send(msg);
                        }
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
        };


        Behaviour producerWake = new WakerBehaviour(getAgent(), wakeUp) {
            @Override
            protected void onWake() {
            }
        };

        addSubBehaviour(producerReceive);
        addSubBehaviour(producerWake);
    }
}
