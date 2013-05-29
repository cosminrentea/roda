To generate the Java classes from XSD, using Eclipse's JAXB:

1. Right-click the XSD file (ddi122.xsd), select "Generate" -> "JAXB Classes" ; a wizard form appears

2. Package: enter "ro.roda.ddi"

3. Bindings: press "Add" -> select file "bindings.xjb" from the same folder

4. Press "Next"

5. Press "Next" again

6. Following option is needed and must be entered as text (at "Additional arguments") in the last window of the wizard:
"-target 2.1"

7. Confirm the generation of classes (when the warning is raised).