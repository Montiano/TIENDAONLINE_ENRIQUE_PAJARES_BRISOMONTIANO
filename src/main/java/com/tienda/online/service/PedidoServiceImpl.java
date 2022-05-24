package com.tienda.online.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tienda.online.model.Pedido;
import com.tienda.online.model.Usuario;
import com.tienda.online.repository.PedidoRepository;

@Service
public class PedidoServiceImpl implements IPedidoService{

	// Con autowired Spring sabe que tiene que inyectar este objeto a esta clase de servicio
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	public Pedido save(Pedido pedido) {		
		return pedidoRepository.save(pedido);
	}

	@Override
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}

	
	public String generateNumFra() {
		String numeracionConcatenada = "FRA.".concat(LocalDateTime.now().toString());
		return numeracionConcatenada;
//		Long numero = 0L;		
//		String numeracionConcatenada = "";
//		
//		List<Pedido> pedidos = findAll();
//		List<Long> numeros = new ArrayList<Long>();
//		
//		pedidos.stream().forEach(p->numeros.add(Long.parseLong(p.getNumFactura())));
//		
//		if(pedidos.isEmpty()) {
//			numero = 1L;
//		}else {
//			numero = numeros.stream().max(Long::compare).get();
//			numero++;
//		}
//		
//		if(numero<10) {
//			numeracionConcatenada = "000000"+String.valueOf(numero);
//		}else if(numero<100){
//			numeracionConcatenada = "00000"+String.valueOf(numero);
//		}else if(numero<1000){
//			numeracionConcatenada = "0000"+String.valueOf(numero);
//		}else if(numero<10000){
//			numeracionConcatenada = "000"+String.valueOf(numero);
//		}else if(numero<100000){
//			numeracionConcatenada = "00"+String.valueOf(numero);
//		}else if(numero<1000000){
//			numeracionConcatenada = "0"+String.valueOf(numero);
//		}
//		
//		return numeracionConcatenada;
	}
	

	@Override
	public List<Pedido> findByUsuario(Usuario usuario) {
		return pedidoRepository.findByUsuario(usuario);
	}

	@Override
	public Optional<Pedido> findById(Long id) {
		return pedidoRepository.findById(id);
	}

	@Override
	public void update(Pedido pedido) {
		pedidoRepository.save(pedido);
		
	}

	@Override
	public void delete(Long id) {
		pedidoRepository.deleteById(id);
		
	}
}
