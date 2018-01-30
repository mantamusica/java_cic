package es.cic.curso.curso17.ejercicio016;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class GestorTaquillaTest {
	
	public static final int NUMERO_SESIONES = 3;
	public static final int CAPACIDAD_SALA0 = 100;
	public static final int CAPACIDAD_SALA1 = 50;
	public static final int CAPACIDAD_SALA2 = 30;
	
	private GestorTaquilla sut;
	
	@Before
	public void setUp() {
		Set<Sala> conjuntoSalas = new HashSet<>();
		Sala s0 = new Sala(CAPACIDAD_SALA0);
		Sala s1 = new Sala(CAPACIDAD_SALA1);
		Sala s2 = new Sala(CAPACIDAD_SALA2);
		for (int i = 0; i < NUMERO_SESIONES; i++) {
			s0.agregaSesion();
			s1.agregaSesion();
			s2.agregaSesion();
		}
		conjuntoSalas.add(s0);
		conjuntoSalas.add(s1);
		conjuntoSalas.add(s2);
		sut = new GestorTaquilla(conjuntoSalas);
	}
	
	@Test
	public void testVenderEntrada() throws IOException {		
		sut.vendeEntrada(0, 1);
		assertEquals((CAPACIDAD_SALA0 - 1), sut.localidadesDisponibles(0, 1));
	}
	
	@Test
	public void testVenderEntradas() throws IOException {		
		sut.vendeEntradas(1, 0, CAPACIDAD_SALA1);
		assertEquals(0, sut.localidadesDisponibles(1, 0));
		
		try {
			sut.vendeEntrada(1, 0);
			fail("No debería poder realizar la venta");
		} catch (IndexOutOfBoundsException ioobe) {
		}
	}
	
	@Test
	public void testRecaudacionSala() throws IOException {		
		sut.vendeEntradas(2, 2, (CAPACIDAD_SALA2 / 2));
		assertEquals((CAPACIDAD_SALA2/2) * 5, sut.obtenRecaudacion(2), 0.000001);		
	}

	@Test
	public void testRecaudacionTotal() throws IOException {		
		sut.vendeEntradas(0, 0, (CAPACIDAD_SALA0 / 2));
		sut.vendeEntradas(1, 1, (CAPACIDAD_SALA1 / 2));
		double esperado = GestorTaquilla.PRECIO * (CAPACIDAD_SALA0 / 2) + GestorTaquilla.PRECIO * (CAPACIDAD_SALA1 / 2);
		assertEquals(esperado, sut.obtenRecaudacionTotal(), 0.000001);
	}

}
