const API_ROOT = "http://localhost:8088/"

export default {
    API_ROOT: API_ROOT,
    LOGIN_ENDPOINT: API_ROOT + "oauth/token",
    CURRENT_USER_ENDPOINT: API_ROOT + "users/me",
    REGISTER_ENDPOINT: API_ROOT + "users/register",
    UPDATE_USER_DETAILS: API_ROOT + "users/updateDetails",
    UPDATE_USER_PASSWORD: API_ROOT + "users/updatePassword"
}