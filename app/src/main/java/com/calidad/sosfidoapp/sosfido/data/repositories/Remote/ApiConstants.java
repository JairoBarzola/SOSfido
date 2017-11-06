package com.calidad.sosfidoapp.sosfido.data.repositories.Remote;

/**
 * Created by jairbarzola on 24/09/17.
 */

public class ApiConstants {

    public static final String LOGIN = "login-api/";
    public static final String U_DESCRIPTION = "person-api/{person_id}/";
    public static final String LOGOUT = "logout-api/";
    public static final String REGISTER = "register-api/";
    public static final String SEND_REPORT = "animal-report-api/";
    public static final String SEND_PHOTO = "report-image-api/";
    public static final String SEND_PHOTO_ADOPTION ="adoption-image-api/";
    public static final String GET_REPORTS = "animal-report-api/";
    public static final String GET_REPORTS_ADOPTION = "adoption-proposal-api/";
    public static final String SEND_ADOPTION = "adoption-proposal-api/";
    public static final String UPLOAD_PHOTO = "person-image-api/";
    public static final String CHANGE_PHOTO= "person-image-api/{person_id}/";
    public static final String EDIT_U="person-api/{person_id}/";

    //API KEYS CONSTANS
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";


}
