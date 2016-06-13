package pe.gob.produce.produccion.model;

public enum Mes {
	Enero(1), Febrero(2), Marzo(3), Abril(4), Mayo(5), Junio(6), 
	Julio(7), Agosto(8), Setiembre(9), Octubre(10), Noviembre(11), Diciembre(12);
	private int valor;
	
	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	Mes(int val){
		this.valor = val;
	}
}
