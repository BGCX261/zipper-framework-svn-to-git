/**
 * 
 */
package br.srv.full.serviceLocator.annotations.processing;

import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.srv.full.serviceLocator.annotations.ServiceLocator;

/**
 * @author Carlos Delfino
 * 
 */
@SupportedAnnotationTypes(value = { "br.src.full.servcieLocator.annotations.*", "java.lang.Resource" })
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class ServiceLocatorAnnotations extends AbstractProcessor implements Processor {

	private final Log log = LogFactory.getLog(getClass());
	private String className;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.annotation.processing.AbstractProcessor#process(java.util.Set,
	 *      javax.annotation.processing.RoundEnvironment)
	 */
	@Override
	public boolean process(Set<? extends TypeElement> p_elements, RoundEnvironment p_env) {
		log.info(p_elements);
		log.info(p_env);
		
		// processingEnv is a predefined member in AbstractProcessor class
		// Messager allows the processor to output messages to the environment
		Messager messager = processingEnv.getMessager();

		// Create a hash table to hold the option switch to option bean mapping
		HashMap<String, String> values = new HashMap<String, String>();

		// Loop through the annotations that we are going to process
		// In this case there should only be one: Option
		for (TypeElement te : p_elements) {

			// Get the members that are annotated with Option
			for (Element e : p_env.getElementsAnnotatedWith(te))
				// Process the members. processAnnotation is our own method
				processAnnotation(e, values, messager);
		}

		// If there are any annotations, we will proceed to generate the
		// annotation
		// processor in generateOptionProcessor method
		if (values.size() > 0)
			try {
				// Generate the option process class
				generateOptionProcessor(processingEnv.getFiler(), values);
			} catch (Exception e) {
				messager.printMessage(Diagnostic.Kind.ERROR, e.getMessage());
			}

		return (true);
	}

	private void processAnnotation(Element element
		      , HashMap<String, String> values, Messager msg) {

		   //Get the Option annotation on the member
		   ServiceLocator opt = element.getAnnotation(ServiceLocator.class);

		   //Get the class name of the option bean
		   className = element.getEnclosingElement().toString();

		   //Check if the type in the member is a String. If not we igonre it
		   //We are currently only supporting String type
		   if (!element.asType().toString().equals(String.class.getName())) {
		      msg.printMessage(Diagnostic.Kind.WARNING
		            , element.asType() + " not supported. " + opt.value() + " not processed");
		      return;
		   }

		   //Save the option switch and the member's name in a hash set
		   //Eg. -filename (option switch) mapped to fileName (member)
		   values.put(opt.value(), element.getSimpleName().toString());
		}

	private void generateOptionProcessor(Filer filer
		      , HashMap<String, String> values) throws Exception {
		       
		   String generatedClassName = className + "Processor";
		       
		   JavaFileObject jfo= filer.createSourceFile(generatedClassName);

		   Writer writer = jfo.openWriter();
		   writer.write("/* Generated on " + new Date() + " */\n");
		       
		   writer.write("public class " + generatedClassName + " {\n");
		       
		   writer.write("\tpublic static " + className + " process(String[] args) {\n");
		       
		   writer.write("\t\t" + className + " options = new " + className + "();\n");
		   writer.write("\t\tint idx = 0;\n");
		       
		   writer.write("\t\twhile (idx < args.length) {\n");
		       
		   for (String key: values.keySet()) {
		      writer.write("\t\t\tif (args[idx].equals(\"" + key + "\")) {\n");
		      writer.write("\t\t\t\toptions." + values.get(key) + " = args[++idx];\n");
		      writer.write("\t\t\t\tidx++;\n");
		      writer.write("\t\t\t\tcontinue;\n");
		      writer.write("\t\t\t}\n");
		   }
		       
		   writer.write("\t\t\tSystem.err.println(\"Unknown option: \" + args[idx++]);\n");
		       
		   writer.write("\t\t}\n");
		       
		   writer.write("\t\treturn (options);\n");
		   writer.write("\t}\n");
		       
		   writer.write("}");
		       
		   writer.flush();
		   writer.close();
		}        

}
