package com.bmw.seed.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: han
 * @since: 2020-08-21 14:52
 **/
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	/**
	 * 瀵规柟娉曞弬鏁版牎楠屽紓甯稿鐞嗘柟娉�(浠呭浜庤〃鍗曟彁浜ゆ湁鏁堬紝瀵逛簬浠son鏍煎紡鎻愪氦灏嗕細澶辨晥) 濡傛灉鏄〃鍗曠被鍨嬬殑鎻愪氦锛屽垯spring浼氶噰鐢ㄨ〃鍗曟暟鎹殑澶勭悊绫昏繘琛屽鐞嗭紙杩涜鍙傛暟鏍￠獙閿欒鏃朵細鎶涘嚭BindException寮傚父锛�
	 */
	@ExceptionHandler(BindException.class)
	public ResponseEntity<Map<String, Object>> handlerBindException(BindException exception) {
		return handlerNotValidException(exception);
	}

	/**
	 * 瀵规柟娉曞弬鏁版牎楠屽紓甯稿鐞嗘柟娉�(鍓嶇鎻愪氦鐨勬柟寮忎负json鏍煎紡鍑虹幇寮傚父鏃朵細琚寮傚父绫诲鐞�)
	 * json鏍煎紡鎻愪氦鏃讹紝spring浼氶噰鐢╦son鏁版嵁鐨勬暟鎹浆鎹㈠櫒杩涜澶勭悊锛堣繘琛屽弬鏁版牎楠屾椂閿欒鏄姏鍑篗ethodArgumentNotValidException寮傚父锛�
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handlerArgumentNotValidException(MethodArgumentNotValidException
																						exception) {
		return handlerNotValidException(exception);
	}

	public ResponseEntity<Map<String, Object>> handlerNotValidException(Exception e) {
		log.debug("begin resolve argument exception");
		BindingResult result;
		if (e instanceof BindException) {
			BindException exception = (BindException) e;
			result = exception.getBindingResult();
		} else {
			MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
			result = exception.getBindingResult();
		}

		Map<String, Object> maps;
		if (result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			maps = new HashMap<>(fieldErrors.size());
			fieldErrors.forEach(error -> {
				maps.put(error.getField(), error.getDefaultMessage());
			});
		} else {
			maps = Collections.EMPTY_MAP;
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(maps);

	}
}