package com.example.apiRestTuten.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.apiRestTuten.responses.Hour;

@RestController
@RequestMapping("/utc")
public class ApiRestController {

	private Pattern patronTime = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");

	@CrossOrigin(origins = "http://localhost:3000")
	@PostMapping
	public Map<String, Hour> obtenerHorario(@RequestParam String hora, @RequestParam String utc) {

		Hour horarioAConvertir = new Hour(hora, utc);
		Map<String, Hour> map = new HashMap<String, Hour>();

		Matcher m = patronTime.matcher(hora);
		boolean validacionHora = m.matches();

		String tiempoCoordinadoString = horarioAConvertir.getTimezone();
		Integer tiempoCoordinado = Integer.parseInt(tiempoCoordinadoString);

		String[] horaminseg = horarioAConvertir.getTime().split(":");

		Integer soloHora = Integer.parseInt(horaminseg[0]);
		Integer soloMinuto = Integer.parseInt(horaminseg[1]);
		Integer soloSegundo = Integer.parseInt((horaminseg[2]));

		if (tiempoCoordinado < -12 || tiempoCoordinado > 12 || !validacionHora) {
			throw new RuntimeException("La hora universal coordinada no es válida");
		} else if (soloHora > 24 || soloHora < 0) {
			throw new RuntimeException("Formato de hora invalido");
		} else if (soloMinuto > 59 || soloMinuto < 0) {
			throw new RuntimeException("Formato de minuto invalido");
		} else if (soloSegundo > 59 || soloSegundo < 0) {
			throw new RuntimeException("Formato de segundo invalido");
		} else {
			Integer calculoUTC = (soloHora + tiempoCoordinado);

			if (calculoUTC < 0) {
				calculoUTC = calculoUTC * -1;
			}
			if (calculoUTC > 24) {
				calculoUTC -= 24;
			}

			String horaFinal = calculoUTC.toString();

			if (horaFinal.toString().length() == 1) {
				horaFinal = "0" + horaFinal;
			}
			String minutoOk = soloMinuto.toString();
			String segundoOk = soloSegundo.toString();

			if (soloMinuto < 9) {
				minutoOk = "0" + minutoOk;
			}
			if (soloSegundo < 9) {
				segundoOk = "0" + segundoOk;
			}
			String h_m_s = horaFinal + ":" + minutoOk + ":" + segundoOk;
			horarioAConvertir.setTime(h_m_s);
			map.put("response", horarioAConvertir);
			return map;
		}

	}

}
