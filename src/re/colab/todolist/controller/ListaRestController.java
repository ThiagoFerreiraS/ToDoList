package re.colab.todolist.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import re.colab.todolist.dao.ListaDao;
import re.colab.todolist.modelo.ItemLista;
import re.colab.todolist.modelo.Lista;


@RestController
public class ListaRestController {
	@Autowired
	private ListaDao listaDao;
	
	@Transactional
	@RequestMapping(value="/lista", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Lista> inserir(@RequestBody String strLista){
		try {
			JSONObject jsonOb = new JSONObject(strLista);
			Lista lista = new Lista();
			lista.setTitulo(jsonOb.getString("titulo"));
			
			List<ItemLista> itens = new ArrayList<>();			
			JSONArray arrayItens = jsonOb.getJSONArray("itens");
			
			for(int i = 0; i < arrayItens.length(); i++){
				ItemLista item = new ItemLista();				
				item.setDescricao(arrayItens.getString(i));
				item.setLista(lista);
				itens.add(item);
			}
			lista.setItens(itens);
			listaDao.inserir(lista);
			
			URI location = new URI("/todo/" + lista.getId());
			
			return ResponseEntity.created(location).body(lista);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Lista>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
