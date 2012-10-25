package rodatools;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.annotation.Annotation;

public class ClassProcessor {

	/**
	 * @param args
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */

	public static void main(String[] args) throws NotFoundException,
			IOException, CannotCompileException, ClassNotFoundException {

		String dirClasses = "./build/classes";
		// TODO create/delete a file as a marker for the processing of Java
		// classes ?!

		File file = new File(dirClasses + "/ddi122");

		String[] filenames = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File f, String s) {
				if (s.toUpperCase().endsWith(".CLASS"))
					return true;
				return false;
			}
		});

		for (String fullFilename : filenames) {
			// cut the ".class"
			String classname = fullFilename.split("\\.")[0];
			if (classname.equalsIgnoreCase("PACKAGE-INFO")) {
				continue;
			}

			CtClass ctClass = ClassPool.getDefault().get("ddi122." + classname);
			ClassFile classFile = ctClass.getClassFile();
			ConstPool constPool = classFile.getConstPool();

			AnnotationsAttribute aa;
			aa = new AnnotationsAttribute(constPool,
					AnnotationsAttribute.visibleTag);

			// @Id annotation
			Annotation idAnnotation = new Annotation("javax.persistence.Id",
					constPool);
			aa.addAnnotation(idAnnotation);

			// @GeneratedValue annotation
			Annotation gvAnnotation = new Annotation(
					"javax.persistence.GeneratedValue", constPool);
			aa.addAnnotation(gvAnnotation);

			// add to class an "id_" field having the previous annotations
			CtField idField = new CtField(ClassPool.getDefault().get(
					"java.lang.Long"), "id_", ctClass);
			idField.getFieldInfo().addAttribute(aa);
			ctClass.addField(idField);

			// add @Entity annotation to the class
			aa = new AnnotationsAttribute(constPool,
					AnnotationsAttribute.visibleTag);
			Annotation annotation = new Annotation("javax.persistence.Entity",
					constPool);
			aa.addAnnotation(annotation);
			classFile.getAttributes().add(aa);

			ctClass.writeFile(dirClasses);
		}

	}

}
