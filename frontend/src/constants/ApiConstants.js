const API_ROOT = "http://localhost:8088/"

export default {
    API_ROOT: API_ROOT,
    LOGIN_ENDPOINT: API_ROOT + "oauth/token",
    CURRENT_USER_ENDPOINT: API_ROOT + "users/me",
    REGISTER_ENDPOINT: API_ROOT + "users/register",
    UPDATE_USER_DETAILS: API_ROOT + "users/updateDetails",
    UPDATE_USER_PASSWORD: API_ROOT + "users/updatePassword",
    GET_ALL_TOURNAMENTS: API_ROOT + "tournaments/",
    CREATE_NEW_TOURNAMENT: API_ROOT + "tournaments/create",
    GET_DEFAULT_RULESETS: API_ROOT + "rulesets/default",
    GET_TOURNAMENT: API_ROOT + "tournaments/",
    /**
     * @return {string}
     */
    CREATE_TEAM: function (tournamentId) {
        return this.GET_TOURNAMENT + tournamentId + "/createTeam"
    }

}