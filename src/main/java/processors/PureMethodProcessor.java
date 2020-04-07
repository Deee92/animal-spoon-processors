package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtMethod;

/**
 * Pure methods
 * do not alter state of calling object
 * do not alter state of passed object
 * do not alter a global variable
 * can return something
 * can be public
 * can be private, called by a public method
 * are deterministic
 * are not empty - actually do something
 */

public class PureMethodProcessor extends AbstractProcessor<CtMethod<?>> {

    public void process(CtMethod<?> element) {
        // get public methods
        if (element.isPublic()) {
            System.out.println("PUBLIC METHOD!!!");
            System.out.println(element.getParameters());
            System.out.println(element.getSignature());
            System.out.println(element.getType());
            // System.out.println(element.getAllMetadata());
            // System.out.println(element.getDeclaringType());
        }

        // get private methods
        else if (element.isPrivate()) {
            System.out.println("PRIVATE METHOD!!!");
            System.out.println(element.getParameters());
        }
    }
}

