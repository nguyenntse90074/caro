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
	public ResponseEntity<Response> addStep(@RequestParam(required=true) Long tableId, 
							@RequestParam(required=true) int x, 
							@RequestParam(required=true) int y){
		Response response = new Response();
		Step newStep = new Step(x, y, Step.VALUE_X);
		CaroTable caroTable = tableManager.getTableById(tableId);
		caroTable.addNewStep(newStep);
		response.setNewStep(newStep);
		response.setResult(caroService.checkResult(newStep, caroTable));
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/request-robot-step", method=RequestMethod.GET)
	public ResponseEntity<Response> addStep(@RequestParam(required=true) Long tableId) {
		Response response = new Response();
		CaroTable caroTable = tableManager.getTableById(tableId);
		Step newStep = caroService.findNextStep(caroTable);
		response.setNewStep(newStep);
		response.setResult(caroService.checkResult(newStep, caroTable));
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
