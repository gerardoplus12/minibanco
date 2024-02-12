package com.app.bnc.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Scope;

@Scope("Singleton")
public class UtilDate {
	public static String getDateStringToday(String separador) {
		return DateTimeFormatter.ofPattern("yyyy"+separador+"MM"+separador+"dd").format(LocalDateTime.now());
	}
	
	public static LocalDate getLocalDateToday() {
		return LocalDate.now();
	}
}