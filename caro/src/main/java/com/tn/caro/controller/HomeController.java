package com.tn.caro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tn.caro.bean.CaroTable;
import com.tn.caro.bean.Response;
import com.tn.caro.bean.Step;
import com.tn.caro.manager.TableManager;
import com.tn.caro.service.CaroService;


@RestController
public class HomeController {
		
	@Autowired
	CaroService caroService;
	
	@Autowired
	TableManager tableManager;
	
	@RequestMapping(value="/register-table", method=RequestMethod.GET)
	public Long registerTable(){
		return tableManager.createdNewTable();
	}
	
	@RequestMapping(value = "/add-user-step", method=RequestMethod.GET)
	public ResponseEntity<Response> addUserStep(@RequestParam(required=true) Long tableId, 
							@RequestParam(required=true) byte x, 
							@RequestParam(required=true) byte y){
		Response response = new Response();
		CaroTable caroTable = null;
		if((caroTable = tableManager.getTableById(tableId)) == null || caroTable.isOutOfDate()) {
			response.setStatus(Response.STATUS_ERR);
			response.setMessage("This table's session is invalid, Refresh page?");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		Step userStep = new Step(x, y, Step.CELL_VALUE_X);
		caroTable.checkToStep(userStep);
		response.setStep(userStep);
		response.setResult(caroService.checkResult(userStep, caroTable));
		if(response.getResult().getIsWin()) {
			tableManager.deleteTableById(tableId);
			caroService.saveLostTable(caroTable);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/request-robot-step", method=RequestMethod.GET)
	public ResponseEntity<Response> addStep(@RequestParam(required=true) Long tableId) {
		Response response = new Response();
		CaroTable caroTable = tableManager.getTableById(tableId);
		Step robotStep = caroService.findRobotStep(caroTable);
		robotStep.setValue(Step.CELL_VALUE_O);
		caroTable.checkToStep(robotStep);
		response.setStep(robotStep);
		response.setResult(caroService.checkResult(robotStep, caroTable));
		if(response.getResult().getIsWin()) {
			tableManager.deleteTableById(tableId);
		}
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/time_out", method=RequestMethod.GET)
	public ResponseEntity<Response> userTimeOut(@RequestParam(required=true) Long tableId) {
		tableManager.deleteTableById(tableId);
		Response response = new Response();
		response.setStatus(Response.STATUS_OK);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
