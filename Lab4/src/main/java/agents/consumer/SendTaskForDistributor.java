package agents.consumer;


import agents.ahelpers.ContractsHelper;
import agents.ahelpers.TimeHelper;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;


public class SendTaskForDistributor extends OneShotBehaviour {

    private double price;
    private int hour;
    private double power;
    private ContractsHelper contractsHelper = new ContractsHelper();

    public SendTaskForDistributor(double price, int hour) {
        this.price = price;
        this.hour = hour;
    }

    @Override
    public void action() {
        System.out.println("Contract num " + hour / 2000 + " from " + getAgent().getLocalName() + ", present time is " + TimeHelper.getCurrentHour());

        switch (getAgent().getLocalName()) {
            case "ShoesFabric":
                power = contractsHelper.getShoesFabricContracts().get(hour);
                break;
            case "Residental":
                power = contractsHelper.getResidentalContracts().get(hour);
                break;
            case "Transport":
                power = contractsHelper.getTransportContracts().get(hour);
                break;
        }

        System.out.println(ContractsHelper.getDistributerNames().get(myAgent.getLocalName()));
        ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
        msg.addReceiver(new AID(ContractsHelper.getDistributerNames().get(myAgent.getLocalName()), false));
        msg.setContent(power + ";" + price + ";" + hour);
        getAgent().send(msg);
        System.out.println("Message: " + msg.getContent() +" from " + getAgent().getLocalName() + " to "
                + ContractsHelper.getDistributerNames().get(getAgent().getLocalName()) +
                " was sended");
    }
}
