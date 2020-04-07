package processors;

import org.apache.log4j.Level;
import spoon.processing.AbstractProcessor;
import spoon.reflect.code.CtCatch;

// Warns for empty catch blocks
public class CatchProcessor extends AbstractProcessor<CtCatch> {
    public void process(CtCatch element) {
        // we get all statements and if there isn't statement, it means the block catch is empty!
        if (element.getBody().getStatements().size() == 0) {
            getFactory().getEnvironment().report(this, Level.WARN, element, "!! EMPTY CATCH BLOCK !!");
        }
    }
}
