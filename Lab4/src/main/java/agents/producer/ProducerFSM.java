package agents.producer;

import jade.core.behaviours.FSMBehaviour;

public class ProducerFSM extends FSMBehaviour {
    private static final String WAITING_AUCTION = "waiting auction";
    private static final String AUCTION = "auction";
    private static final String WAITING_CONTRACT = "waiting contract";

    public ProducerFSM(double minPrice, double myPrice, double neededPower, int contract){
        
        registerFirstState(new AuctionWaiting(minPrice, myPrice, contract), WAITING_AUCTION);
        registerState(new ProducerAuction(minPrice, myPrice, contract), AUCTION);
        registerLastState(new ContractWaiting(getAgent(), 15000, neededPower, contract), WAITING_CONTRACT);
        
        
        registerDefaultTransition(WAITING_AUCTION, AUCTION);
        registerDefaultTransition(AUCTION, WAITING_CONTRACT);
    }
}
