package basiclearner;

import com.google.common.collect.ImmutableSet;
import de.learnlib.api.SUL;

import java.io.IOException;
import java.util.Collection;
import java.net.InetAddress;

/**
 * Created by ramon on 13-12-16.
 */
public class ExampleExperiment {
    /**
     * Example of how to call a learner in a simple way with this class. Learns the ExampleSUL.
     * @param args
     * @throws IOException
     */
    public static void main(String [] args) throws IOException {
        // Load the actual SUL-class
        // For a SUL over a socket, use the SocketSUL-class
        // You can also program an own SUL-class if you extend SUL<String,String> (or SUL<S,T> in
        // general, with S and T the input and output types - but this class assumes strings)
    	// Replace ExampleSUL with SocketSUL
        InetAddress ipAddress = InetAddress.getLoopbackAddress();
        int port = 7892; 
        String resetCommand = "reset";

        SUL<String, String> sul = new SocketSUL(ipAddress, port, true, resetCommand);

        // Define the input alphabet
        Collection<String> inputAlphabet = ImmutableSet.of(
        		"IACK", "IREQ_0_0_0", "IREQ_0_0_1", "IREQ_0_1_0",
        		"IREQ_0_1_1", "IREQ_1_0_0", "IREQ_1_0_1", "IREQ_1_1_0", "IREQ_1_1_1",
        		"ISENDFRAME", "ITIMEOUT"
                );

        try {
        	BasicLearner.randomWalk_chanceOfResetting = 0; 
        	BasicLearner.randomWalk_numberOfSymbols = 75;   

            // runControlledExperiment for detailed statistics, runSimpleExperiment for just the result
            BasicLearner.runControlledExperiment(sul, BasicLearner.LearningMethod.RivestSchapire, BasicLearner.TestingMethod.RandomWalk, inputAlphabet);
        } finally {
            if (sul instanceof AutoCloseable) {
                try {
                    ((AutoCloseable) sul).close();
                } catch (Exception exception) {
                    // should not happen
                }
            }
        }
    }
}
