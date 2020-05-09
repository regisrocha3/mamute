package br.com.caelum.mamute.infrastructure.sanitized.text;

public class SanitizedText {
    private String text;

    private SanitizedText(String text) {
        this.text = text;
    }

    public static SanitizedText fromTrustedText(String text) {
        return new SanitizedText(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return getText();
    }
}
