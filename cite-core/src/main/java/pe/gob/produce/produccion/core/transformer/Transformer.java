package pe.gob.produce.produccion.core.transformer;

import java.util.List;

public interface Transformer<X,Y> {

	public Y transformer(final X input);
	public List<Y> transformer(final List<X> input);
}
