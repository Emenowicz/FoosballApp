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
                    <v-layout pa-2 row align-end class="lightbox white--text">
                        <v-flex shrink>
                            <div class="title">{{ userProfile.username }}</div>
                            <div class="body-1">{{ userProfile.email }}</div>
                        </v-flex>
                        <v-spacer/>
                        <v-btn to="/myaccount" large flat dark icon>
                            <v-icon>person</v-icon>
                        </v-btn>
                        <v-btn @click="logout" large flat dark icon>
                            <v-icon>power_off</v-icon>
                        </v-btn>
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
            <v-toolbar-title>Foosball League</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn v-if="!loggedIn" flat @click="goToLogin">Zaloguj się</v-btn>
        </v-toolbar>
        <v-content>
            <v-container fluid fill-height>
                <v-layout
                        justify-center
                        align-center
                >
                    <router-view/>
                </v-layout>
            </v-container>
        </v-content>
    </v-layout>


</template>

<script>
    import {AUTH_LOGOUT} from "../store/actions/authentication";
    import VListTile from "vuetify/lib/components/VList/VListTile";
    import VListTileAction from "vuetify/lib/components/VList/VListTileAction";

    export default {
        name: "Navbar",
        data: () => ({
            drawer: null
        }),
        computed: {

            loggedIn: function () {
                return this.$store.getters.isAuthenticated
            },
            userProfile: function () {
                return this.$store.getters.getProfile
            },
        },
        components: {VListTileAction, VListTile},
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
            }
        }

    }
</script>

<style scoped>

</style>