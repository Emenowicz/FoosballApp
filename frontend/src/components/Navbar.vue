<template>
    <v-layout>
        <v-navigation-drawer
                v-model="drawer"
                fixed
                app
        >
            <v-img v-if="loggedIn" :aspect-ratio="16/9" src="https://www.htmlcsscolor.com/preview/gallery/4CAF50.png">
                <v-layout column fill-height>
                    <v-spacer></v-spacer>
                    <v-layout pa-2 row align-end justify-space-between class="white--text">
                        <v-flex>
                            <div class="title">{{ userProfile.username }}</div>
                            <div class="body-1">{{ userProfile.email }}</div>
                            <v-layout justify-space-between row>
                                <div class="body-2 text-xs-center">WT: {{userProfile.tournamentsWon}}</div>
                                <div class="body-2">WM: {{userProfile.matchesWon}}</div>
                                <div class="body-2">WR: {{userProfile.roundsWon}}</div>
                            </v-layout>
                        </v-flex>
                        <v-flex>
                            <v-btn small flat dark icon @click="helpDialog=true">
                                <v-icon>help</v-icon>
                            </v-btn>
                            <v-btn small flat dark icon to="/myaccount">
                                <v-icon>person</v-icon>
                            </v-btn>
                            <v-btn small flat dark icon @click="logout">
                                <v-icon>power_settings_new</v-icon>
                            </v-btn>
                        </v-flex>
                    </v-layout>
                </v-layout>
            </v-img>
            <v-list dense>
                <v-list-tile ripple @click="goHome">
                    <v-list-tile-action>
                        <v-icon>home</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        <v-list-tile-title>Strona główna</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile ripple @click="goToSearchTournament">
                    <v-list-tile-action>
                        <v-icon>list</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        <v-list-tile-title>Lista turniejów</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile v-if="this.$store.getters.isAuthenticated" ripple @click="createTournament">
                    <v-list-tile-action>
                        <v-icon>add</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        <v-list-tile-title>Utwórz nowy turniej</v-list-tile-title>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-navigation-drawer>
        <v-toolbar color="green" dark fixed app>
            <v-toolbar-side-icon @click.stop="drawer = !drawer"></v-toolbar-side-icon>
            <v-toolbar-title>
                <router-link to="/" style="text-decoration: none; color:white">
                    Foosball League
                </router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn v-if="!loggedIn" flat @click="goToLogin">Zaloguj się</v-btn>
        </v-toolbar>
        <v-content>
            <router-view />
        </v-content>
        <v-dialog v-model="helpDialog" max-width="200">
            <v-card>
                <v-card-title class="headline">
                    Skróty
                </v-card-title>
                <v-card-text>
                    <div class="subheading">WT: Wygrane turnieje</div>
                    <div class="subheading">WM: Wygrane mecze</div>
                    <div class="subheading">WR: Wygrane rundy</div>
                </v-card-text>
                <v-card-actions>
                    <v-btn color="primary" flat @click="helpDialog=false">Zamknij</v-btn>
                </v-card-actions>
            </v-card>
        </v-dialog>
    </v-layout>
</template>

<script>
    import {AUTH_LOGOUT} from "../store/actions/authentication";

    export default {
        name: "Navbar",
        data: () => ({
            drawer: null,
            helpDialog: false
        }),
        computed: {

            loggedIn: function () {
                return this.$store.getters.isAuthenticated
            },
            userProfile: function () {
                return this.$store.getters.getProfile
            },
        },
        methods: {
            goHome: function () {
                this.$router.push('/')
            },
            goToLogin: function () {
                this.$router.push('/login')
            },
            logout: function () {
                this.$store.dispatch(AUTH_LOGOUT)
                    .then(() => this.$router.push('/login'))
            },
            createTournament: function () {
                this.$router.push('/new-tournament')
            },
            goToSearchTournament: function () {
                this.$router.push('/all-tournaments')
            }
        }

    }
</script>

<style scoped>

</style>