package vanguard.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;

@SupportedAnnotationTypes("vanguard.annotations.VLANTModConstructor")
public class VLANTProcessor extends AbstractProcessor{

    protected ProcessingEnvironment penv;

    protected VLANTProcessor(){

    }

    @Override
    public synchronized void init(ProcessingEnvironment penv){
        super.init(penv);
        this.penv = penv;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment renv){
        if(!renv.processingOver()){
            for(TypeElement reference : set){
                Set<? extends Element> targets = renv.getElementsAnnotatedWith(reference);

                for(Element element : targets){
                    penv.getMessager().printMessage(Diagnostic.Kind.ERROR, "This constructor is not meant to be used directly, it is meant for subclasses " +
                                    "to use for copy constructors and modifications to superclass functionality.", element);

                    throw new RuntimeException("FUCKED");
                }
            }
        }
        return true;
    }
}
