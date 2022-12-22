package agents.producer;

import agents.ahelpers.ContractsHelper;
import agents.ahelpers.DFHelper;
import jade.core.Agent;

import java.util.Random;

public class ProducerAgent extends Agent {
    private double A = 17.4;
    private double B1 = 8.6;
    private double B2 = 9.3;
    private double C0 = -81.355;
    private double C1 = 20.922;
    private double C2 = -1.358;
    private double C3 = 0.025;
    private Random random = new Random();

    @Override
    protected void setup() {
        System.out.println(this.getLocalName() + " are borned to die)))");
        DFHelper.registration(this, "Producer");
        switch (this.getLocalName()) {

            //Thermal Power Plant (TPP)
            case "TPP":
                for (int i = 0; i < 24; i++) {
                    ContractsHelper.getPowerOfTPP().put(i*2000, A);
                }
//                System.out.println("TPP " + ContractsHelper.getPowerOfTPP().toString());
                break;

            //Wind Power Plant (WPP)
            case "WPP":
                for (int i = 0; i < 24; i++) {
                    ContractsHelper.getPowerOfWPP().put(i*2000, Math.abs(random.nextGaussian() * B2 + B1)); //Like and subscribe to StackOverflow
                }
//                System.out.println("WPP " + ContractsHelper.getPowerOfWPP().toString());
                break;

            //Solar Power Plant (SPP)
            case "SPP":
                for (int i = 0; i < 24; i++) {
                    if (i > 5 && i < 19) {
                        ContractsHelper.getPowerOfSPP().put(i*2000, C0 * 1 + C1 * i + C2 * i * i + C3 * i * i * i);
                    } else {
                        ContractsHelper.getPowerOfSPP().put(i*2000, 0.0);
                    }
                }
//                System.out.println("SPP " + ContractsHelper.getPowerOfSPP().toString());
                break;
        }

        addBehaviour(new WaitingRequest());
    }
}
