package cita.calendario;

import java.time.LocalDate;
import java.util.ArrayList;

import dao.CitaDao;

public class SaberSiExisteUnaCita {
	public static boolean evaluarSiExiste(LocalDate localDate, int dia) {

		ArrayList<LocalDate> arrayListCitas = CitaDao.LeePorFechaEnAdelante(LocalDate.now().withDayOfMonth(1));

		for (LocalDate localDate2 : arrayListCitas) {
			if (localDate2.getMonth().equals(localDate.getMonth()) && localDate2.getDayOfMonth() == dia) {
				return true;
			}
		}
		return false;
	}

	public static boolean evaluarSiExiste(LocalDate localDate) {

		ArrayList<LocalDate> arrayListCitas = CitaDao.LeePorFechaEnAdelante(LocalDate.now().withDayOfMonth(1));

		for (LocalDate localDate2 : arrayListCitas) {
			if (localDate2.getMonth().equals(localDate.getMonth())
					&& localDate2.getDayOfMonth() == localDate.getDayOfMonth()) {
				return true;
			}
		}
		return false;
	}

	public static boolean evaluarSiExiste(int mes, int dia) {

		ArrayList<LocalDate> arrayListCitas = CitaDao.LeePorFechaEnAdelante(LocalDate.now().withDayOfMonth(1));

		for (LocalDate localDate2 : arrayListCitas) {
			if (localDate2.getMonthValue() == mes && localDate2.getDayOfMonth() == dia) {
				return true;
			}
		}
		return false;
	}

}
