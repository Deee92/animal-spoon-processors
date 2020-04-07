package usage;

import processors.MethodProcessor;
import processors.PureMethodProcessor;
import spoon.MavenLauncher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.declaration.CtType;
import spoon.support.compiler.SpoonPom;

public class UseSpoon {
    public static void main(String[] args) {
        // APP_SOURCE / TEST_SOURCE / ALL_SOURCE
        MavenLauncher launcher = new MavenLauncher("/home/user/IdeaProjects/spoon-dog", MavenLauncher.SOURCE_TYPE.APP_SOURCE);

        SpoonPom spoonPom = launcher.getPomFile();
        // launcher.addProcessor("MethodProcessor");

        launcher.buildModel();
        CtModel model = launcher.getModel();

        // launcher.setSourceOutputDirectory("/home/user/IdeaProjects/spoon-dog/target/");

        // All packages of the model
        for (CtPackage p : model.getAllPackages()) {
            System.out.println("packages: " + p.getQualifiedName());
        }

        // All classes of the model
        for (CtType<?> s : model.getAllTypes()) {
            System.out.println("classes: " + s.getQualifiedName());
        }

        MethodProcessor methodProcessor = new MethodProcessor();
        model.processWith(methodProcessor);

        PureMethodProcessor pureMethodProcessor = new PureMethodProcessor();
        model.processWith(pureMethodProcessor);
    }
}
