package cita.calendario;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class CrearCalendario {
	
	
	public static void printCalendario() {
	
		System.out.println(" ");
		System.out.println("La disponibilidad de nuestro calendario es esta");
		System.out.println("Los que estan en XX no estan disponibles");
		System.out.println(" ");
		
		LocalDate localDateAhora = LocalDate.now();
		LocalDate ld = LocalDate.of(localDateAhora.getYear(), localDateAhora.getMonthValue(), 1);
		int vueltas = 1;
		while (vueltas != 3) {

			if (vueltas == 2) {
				ld = ld.withMonth(localDateAhora.getMonthValue() + 1);
			}
			int dias = 1;
			int dow = ld.getDayOfWeek().getValue();// obtener día de la semana del primer día del mes
			int max = ld.lengthOfMonth();// obtiene el maximo de días.

			System.out.println("                    " + ld.getMonth().toString());

			for (int i = 1; i < 8; i++) {// Imprime encabezado del enum DayOfWeek

				System.out.print("| " + DayOfWeek.of(i).toString().substring(0, 2) + " |");
			}

			boolean empezo = false;
			for (int i = 1; i < 7; i++) {// semanas por mes
				System.out.println();
				for (int j = 1; j < 8; j++) {// día por semana
					if (j == dow) {
						empezo = true;
					}
					if (!empezo || dias > max) {
						System.out.print("| XX |");
					} else if (empezo) {

						if (SaberSiExisteUnaCita.evaluarSiExiste(ld, dias)) {
							System.out.print("| XX |");
							dias++;
						}else if (vueltas==1 && dias < localDateAhora.getDayOfMonth() ) {
							System.out.print("| XX |");
							dias++;
						} else {
							System.out.print("| " + (dias < 10 ? ("0" + dias) : ("" + dias)) + " |");
							dias++;
						}
					}
				}
			}

			System.out.println("       ");
			vueltas++;
		}

	}

	
}
