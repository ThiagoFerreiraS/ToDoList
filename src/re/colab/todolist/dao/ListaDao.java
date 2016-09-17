package re.colab.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import re.colab.todolist.modelo.Lista;

@Repository
public class ListaDao {
	@PersistenceContext
	private EntityManager manager;
	
	public void inserir(Lista lista){
		manager.persist(lista);
	}
}
