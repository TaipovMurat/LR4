package agents.producer;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.TimeHelper;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class WaitingRequest extends Behaviour {
    private double myPower;
    private int contract;

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);

        ACLMessage receive = getAgent().receive(mt);
        if (receive != null){
            contract = Integer.parseInt(receive.getOntology());
            double neededPower = Double.parseDouble(receive.getContent());

            System.out.println("Producer" + getAgent().getLocalName() + " get request for sell "
                    + neededPower + " kW. Contract № " + contract);
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
            ACLMessage rep = receive.createReply();
            if (neededPower <= myPower){
                System.out.println(myPower);
                rep.addReceiver(new AID(receive.getSender().getLocalName(), false));
                rep.setPerformative(ACLMessage.AGREE);
                rep.setContent(String.valueOf(myPower));
                rep.setOntology(String.valueOf(contract));
                getAgent().send(rep);

                double minPrice = Math.abs(20.0 - myPower) * 1.99;
                double myPrice = 2 * minPrice;
                getAgent().addBehaviour(new ProducerFSM(minPrice, myPrice, neededPower, contract));
            }else {
                System.out.println(myPower);
                rep.setPerformative(ACLMessage.REFUSE);
                rep.setContent(String.valueOf(myPower));
                rep.setOntology(String.valueOf(contract));
                getAgent().send(rep);
            }
        }else {
            block();
        }
    }

    @Override
    public boolean done() {
        return false;
    }
}
