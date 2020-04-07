package processors;

import spoon.processing.AbstractProcessor;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;

public class BenProcessor extends AbstractProcessor<CtClass<?>> {

    @Override
    public void process(CtClass element) {
        // element.getMethods().forEach(System.out::println);
        System.out.println("Original number of methods: " + element.getMethods().size());

        // Remove a method
        CtMethod methodToRemove = (CtMethod) element.getMethodsByName("woof").get(0);
        element.removeMethod(methodToRemove);

        // Check number of methods
        System.out.println("New number of methods: " + element.getMethods().size());
    }
}