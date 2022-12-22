package agents.ahelpers;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.util.ArrayList;
import java.util.List;

public class DFHelper {  //для публикации продюсера

    public synchronized static void registration(Agent agent, String serviceName) {
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        sd.setName(agent.getLocalName());
        dfd.addServices(sd);
        try{
            DFService.register(agent, dfd);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }

    public synchronized static List<AID> searchForAgents(Agent agent, String serviceName){
        List<AID> agents = new ArrayList<>();
        DFAgentDescription dfd = new DFAgentDescription();
        ServiceDescription sd  = new ServiceDescription();
        sd.setType(serviceName);
        dfd.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(agent, dfd);
            for (DFAgentDescription desc : result) {
                agents.add(desc.getName());
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        return agents;
    }
}
