package agents.distributor;


import jade.core.Agent;

public class DistributorAgent extends Agent {

    @Override
    protected void setup(){
        System.out.println(this.getLocalName() + " are borned!");

        addBehaviour(new RecieveTaskFromConsumer());
    }

}
