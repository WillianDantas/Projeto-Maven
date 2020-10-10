package edu.br.fas;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.library.dependencies.Slice;

import org.junit.Test;

import edu.br.fas.persistence.Dao;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

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
            .matching("br.edu.(*)..").should().beFreeOfCycles();

            rule.check(importedClasses);
    }



}