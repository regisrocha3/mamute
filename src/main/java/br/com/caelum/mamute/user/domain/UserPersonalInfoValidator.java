package br.com.caelum.mamute.user.domain;

public class UserPersonalInfoValidator {

    public static final int NAME_MIN_LENGTH = 4;
    public static final int NAME_MAX_LENGTH = 100;
    public static final int WEBSITE_MIN_LENGTH = 0;
    public static final int WEBSITE_MAX_LENGHT = 200;
    public static final int EMAIL_MIN_LENGTH = 6;
    public static final int EMAIL_MAX_LENGTH = 100;
    public static final int LOCATION_MAX_LENGTH = 100;
    public static final int ABOUT_MIN_LENGTH = 6;
    public static final int ABOUT_MAX_LENGTH = 500;
    public static final int MARKED_ABOUT_MAX_LENGTH = 600;
    public static final String NAME_LENGTH_MESSAGE = "user.errors.name.length";
    public static final String LOCATION_LENGTH_MESSAGE = "user.errors.location.length";
    public static final String WEBSITE_LENGTH_MESSAGE = "user.errors.website.length";
    public static final String EMAIL_LENGTH_MESSAGE = "user.errors.email.length";
    public static final String ABOUT_LENGTH_MESSAGE = "user.errors.about.length";
    public static final String EMAIL_NOT_VALID = "user.errors.email.invalid";
    public static final String NAME_REQUIRED = "user.errors.name.required";
}
