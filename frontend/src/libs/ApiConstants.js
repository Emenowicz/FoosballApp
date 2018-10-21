const API_ROOT = "http://localhost:8088/"

export default {
    API_ROOT: API_ROOT,
    LOGIN_ENDPOINT: API_ROOT + "oauth/token",
    CURRENT_USER_ENDPOINT: "users/me",
    REGISTER_ENDPOINT: API_ROOT + "users/register"
}