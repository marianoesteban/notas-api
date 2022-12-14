package marianoesteban.notas.rest;

public class Envelope<T> {

	private T data;

	public Envelope(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Envelope [data=" + data + "]";
	}

}
