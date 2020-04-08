package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtExecutable;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.CtInheritanceScanner;
import spoon.reflect.visitor.Filter;

import java.util.List;
import java.util.Scanner;

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

    @Override
    public boolean isToBeProcessed(CtMethod<?> candidate) {

        // public or private
        if (!(candidate.isPublic() || candidate.isPrivate())) {
            return false;
        }
        if (candidate.isPrivate()) {
            // find all callers to the methods and they should be public
            List<CtInvocation> elements = candidate.getFactory().getModel().getElements(new Filter<CtInvocation>() {
                @Override
                public boolean matches(CtInvocation ctElement) {
                    ctElement.getExecutable().getSignature().equals(candidate.getSignature());
                    return false;
                }
            });
            for (CtInvocation ctInvocation : elements) {
                CtMethod parent = ctInvocation.getParent(CtMethod.class);
                if (parent != null) {
                    if (parent.isPublic()) {
                        return false;
                    }
                }
            }
        }
        // getElements
        // getParent
        // replace
        final boolean[] isPure = {true};
        CtInheritanceScanner ctInheritanceScanner = new CtInheritanceScanner() {
            @Override
            public <T> void visitCtVariableWrite(CtVariableWrite<T> e) {
                super.visitCtVariableWrite(e);
                isPure[0] = false;
            }

            @Override
            public <T> void visitCtArrayWrite(CtArrayWrite<T> arrayWrite) {
                super.visitCtArrayWrite(arrayWrite);
                isPure[0] = false;
            }

            @Override
            public <T> void scanCtAbstractInvocation(CtAbstractInvocation<T> a) {
                super.scanCtAbstractInvocation(a);
                isPure[0] = false;
            }
        };

        candidate.getElements(ctElement -> true).forEach(ctElement -> {
            ctInheritanceScanner.scan(ctElement);
        });

        return isPure[0];
    }

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

