package br.edu.utfpr.dv.siacoes.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestModel {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {TestModel
	}

	@Test
	public void test() {
		DepartmentLombok depart = new DepartmentLombok;
		
		 int idDepartment;
		 Campus campus;
		 String name;
		String fullName;
		 transient byte[] logo;
		 boolean active;
	 String site;
	 String initials;
	 
	 
	 assertEquals(3,idDepartment);
	 assertEquals("Cornelio Procopio",campus);
	 assertEquals("Joao", name);
	 assertEquals("Joao Silva",fullName);
	 assertEquals(2, logo);
	 assertEquals(true, active);
	 assertEquals("http://www.utfpr.edu.br/", site);
	 assertEquals("utfpr", initials);
	}

}
