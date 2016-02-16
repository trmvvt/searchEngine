package usc.csci572.hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QueryProgram {
	public static void main(String args[]) {
		System.out.println("CSCI572 HW2 Demo: Full results can be found out in the report. This program just displays top 5 reults in readable format");
		System.out.println("");
		System.out.println("Enter the Query number: (1/2/3/4)");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int q = Integer.parseInt(br.readLine());
			switch(q) {
			case 1: {
				System.out.println("Q1: Predict which geospatial areas will have which job types in the future");
				System.out.println();
				System.out.println("Our query: http://localhost:8983/solr/select?wt=json&indent=true&q=*:*&&fl=location2,jobtype&facet=true&facet.pivot=location2,jobtype ");
				System.out.println();
				System.out.println("Query Results: (Top 5)");
				System.out.println("Based on historical data we estimate the following job types to be open in specific geospatial areas in the near future");
				System.out.println("Location ---> Jobtype");
				System.out.println("Buenos Aires, Argentina --->  Tiempo Completo");
				System.out.println("buenos ---> tiempo");
				System.out.println("C髍doba, Argentina ---> Tiempo Completo");
				System.out.println("Rosario, Santa Fe, Argentina --->  Tiempo Completo");
				System.out.println("Norte, Berisso, Buenos Aires, Argentina --->  Tiempo Completo");
				break;
			}
			case 2: {
				System.out.println("Q2: Compare jobs in terms of quickly they抮e filled specifically in regards to region");
				System.out.println();
				System.out.println("Our query: http://localhost:8983/solr/select?q=*:*&facet=true&stats=true&stats.field=noOfDays&stats.facet=location2&facet.field=location2&facet.pivot=location2&wt=json&indent=true");
				System.out.println("Query Results: (Top 5)");
				System.out.println("Location ---> Job ---> no. of days");
				System.out.println("El Talar, Buenos Aires, Argentina ---> Asistente Administrativa ---> 5");
				System.out.println("Buenos Aires, Argentina --->  Programador de Producci贸n ---> 5");
				System.out.println("Confiter韆 del Molino, Capital Federal, Argentina - Buenos Aires, Ciudad Aut髇oma de Buenos Aires, Argentina ---> Adm en Salud para Guardia d铆as S谩bados de 8 a 20hs ---> 5");
				System.out.println("Buenos Aires, Argentina ---> Ref. 468 Conserje Recepcionista para importante Hotel Internacional ---> 5");
				System.out.println("C髍doba, Argentina ---> Especialista en fabricaci贸n de ladrillos ---> 5");
				break;
				
			}
			case 3: {
				System.out.println("Q3: Can you classify and zone cities based on the jobs data (E.G. commercial shopping region, industrial, residential, business offices, medical, etc)");
				System.out.println();
				System.out.println("Our query: http://localhost:8983/solr/select?wt=json&indent=true&q=*:*&fl=location2,title&facet=true&facet.pivot=title,location2&sort=jobTypeScore%20desc&sort=locScore%20desc");
				System.out.println("Query Results: (Top 5)");
				System.out.println("Location ---> Zone");
				System.out.println("El Talar, Buenos Aires, Argentina ---> Administrative jobs");
				System.out.println("Buenos Aires, Argentina ---> Programmer jobs");
				System.out.println("C髍doba, Argentina ---> Especialista en fabricaci贸n de ladrillos jobs");
				System.out.println("Rosario, Santa Fe, Argentina ---> Empresa L铆der en Comercializaci贸n de Productos siderometal煤rgicos jobs");
				System.out.println("Campa馻, Buenos Aires, Argentina ---> Operarios Campana Autopartista jobs");
				break;
			}
			case 4: {
				System.out.println("Q4: What are the trends as it relates to full time vs part time employment in South America?");
				System.out.println();
				System.out.println("Our query: http://localhost:8983/solr/select?q=isSA:true&sort=noOfDays%20asc&facet=true&facet.pivot=jobtype,title&group=true&group.field=jobtype&fl=title,jobtype,id&wt=json&indent=true");
				System.out.println("Query Results: (Top 5)");
				System.out.println("JObType ---> Trend");
				System.out.println("Operador CNC(Operator Jobs) --->  Tiempo Completo");
				System.out.println("Maquetador --->  Tiempo Completo");
				System.out.println("Estudiante de Contador(contractor jobs) ---> Medio Tiempo");
				System.out.println("Q-Cartero / Distribuidor de correspondencia(Didtribustion jobs) --->  Tiempo Completo, Temporal");
				System.out.println("Repartidor con Moto- Puerto Madero Turno Noche --->  Medio Tiempo, Por Horas");
				break;
			}
			default: {
				System.out.println("Please enter a valid query number");
			}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Number Format invalid");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("IO Exception");
			e.printStackTrace();
		}
	}

}
