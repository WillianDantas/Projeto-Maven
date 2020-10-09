package edu.br.fas;


import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;


import org.junit.Test;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class FooArchitectTest {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("edu.br.fas");



    @Test
    public void varificarDependenciaParaCamadaPrsistencia(){
            ArchRule rule = classes()
            .that().resideInAPackage("..persistence..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");

            rule.check(importedClasses);
    }

    @Test
    public void varificarDependenciaDaCamadaPrsistencia(){
            ArchRule rule = noClasses()
            .that().resideInAPackage("..persistence..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage( "..service..");

            rule.check(importedClasses);
    }



}