package br.com.caelum.mamute.infrastructure.sanitized.text;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString(exclude = {"pure"})
public class MarkedText {

	@Getter
	private String pure;
	@Getter
	private String marked;

	public static MarkedText pureAndMarked(String pure, String marked) {
		return new MarkedText(pure, marked);
	}

	public static MarkedText notMarked(String pure) {
		return new MarkedText(pure, pure);
	}
}

