package edu.br.fas;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;


import org.junit.Test;

import edu.br.fas.persistence.Dao;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

public class FooArchitectTest {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("edu.br.fas");



    @Test
    public void varificarDependenciaParaCamadaPersistencia(){
            ArchRule rule = classes()
            .that().resideInAPackage("..persistence..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..persistence..", "..service..");

            rule.check(importedClasses);
    }

    @Test
    public void varificarDependenciaDaCamadaPersistencia(){
            ArchRule rule = noClasses()
            .that().resideInAPackage("..persistence..")
            .should().dependOnClassesThat().resideInAnyPackage( "..service..");

            rule.check(importedClasses);
    }

    @Test
    public void varificarNomesClassesCamadaPersistencia(){
            ArchRule rule = classes()
            .that().haveSimpleNameEndingWith("Dao")
            .should().resideInAPackage( "..persistence..");

            rule.check(importedClasses);
    }

    @Test
    public void varificarNomesClassesCamadaInterfaceDao(){
            ArchRule rule = classes()
            .that().implement(Dao.class)
            .should().haveSimpleNameEndingWith( "Dao");

            rule.check(importedClasses);
    }

    @Test
    public void varificarDependenciasCiclicas(){
            ArchRule rule = slices()
            .matching("edu.br.fas.(*)..").should().beFreeOfCycles();

            rule.check(importedClasses);
    }

    @Test
    public void varificarViolcaoCamadas(){
            ArchRule rule = layeredArchitecture()
            .layer("Service").definedBy("..service..")
            .layer("Persistence").definedBy("..persistence..")
            .whereLayer("Persistence").mayOnlyBeAccessedByLayers("Service");

            rule.check(importedClasses);
    }



}