package controllers;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import models.Model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import play.mvc.WebSocket;

public class ShopObserver implements Observer {
	
	public WebSocket.Out<JsonNode> shop;
	
	public ShopObserver(){
		Model.sharedInstance.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object artikelNummer) {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonArtikelNummer = null;
		try {
			jsonArtikelNummer = mapper.readTree("{\"Artikelnummer\":\""+artikelNummer+"\"}");
			System.out.println("JSON: "+jsonArtikelNummer);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		shop.write(Model.sharedInstance.zeigeAktuelleMenge(jsonArtikelNummer));
	}

}
