package models;

/**
 * Clase encargada de componer una produccion
 * @author user
 *
 */
public class Production {

	private String producer, production;

	/**
	 * Constructor de la clase que crea la produccion junto con lo que produce 
	 * @param producer simbolo no terminal que genera producciones
	 * @param production produccion generada por el producer
	 */
	public Production(String producer, String production) {
		this.producer = producer;
		this.production = production;
	}

	/**
	 * metodo get del simbolo no terminal que genera producciones
	 * @return simbolo no terminal que genera producciones
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * metodo get de la produccion generada por el producer
	 * @return produccion generada por el producer
	 */
	public String getProduction() {
		return production;
	}
	
	/**
	 * Organiza los datos para mostrarlos
	 */
	@Override
	public String toString() {
		return producer + " => "+ production;
	}

}
